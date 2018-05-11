package net.symbiosis.persistence.dao;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.super_class.sym_entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Collections.singletonList;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/11/13
 * Time: 8:30 PM
 */

@Repository
@Transactional
@PersistenceContext
public class EntityManagerRepo {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    private final static Integer UNLIMITED_RESULTS = -1;

    private EntityManager getEntityManager() {
        if (entityManager == null) {
            LOGGER.info("Creating entity manager from entityManagerFactory");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    @Transactional
    public <E extends sym_entity> E refresh(E e) {
        try {
            getEntityManager().refresh(e);
            LOGGER.info("Refreshed " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not refresh " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + ex.getMessage());
            entityManager = null;
        }
        return e;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public <E extends sym_entity> E saveOrUpdate(sym_entity<E> e) {
        LOGGER.info("Updating entity: " + e.toString());
        try {
            getEntityManager().getTransaction().begin();
            e = getEntityManager().merge(e);
            getEntityManager().getTransaction().commit();
            LOGGER.info("Updated " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not merge " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + ex.getMessage());
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
            entityManager = null;
            throw new RuntimeException(ex.getMessage());
        }
        return (E) e;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public <E extends sym_entity> E save(sym_entity<E> e) {
        LOGGER.info("Saving new entity to database: " + e.toString());
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(e);
            getEntityManager().getTransaction().commit();
            LOGGER.info("Persisted " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not persist " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + ex.getMessage());
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
            entityManager = null;
            throw new RuntimeException(ex);
        }
        return (E) e;
    }

    @Transactional
    public <E extends sym_entity> void delete(sym_entity<E> e) {
        LOGGER.info("Deleting entity " + e.toString());
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(e);
            getEntityManager().getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not delete " + e + " : " + ex.getMessage());
            entityManager = null;
        }
    }

    @Transactional
    public <E extends sym_entity> Long countWhere(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                                  boolean useLikeQuery, boolean useOrQuery) {
        StringBuilder conditions = new StringBuilder();

        for (Pair<String, ?> criteria : criterion) {
            if (conditions.length() > 0) {
                if (useOrQuery) {
                    conditions.append(" OR ");
                } else {
                    conditions.append(" AND ");
                }
            }
            if (useLikeQuery) {
                conditions.append(criteria.getLeft()).append(" LIKE '%").append(criteria.getRight()).append("%' ");
            } else {
                conditions.append(criteria.getLeft()).append("= '").append(criteria.getRight()).append("' ");
            }
        }

        LOGGER.info(entityClass.getSimpleName() + " countWhere " + conditions);
        String queryString = "SELECT count(e) FROM " + entityClass.getSimpleName() + " e WHERE " + conditions;

        try {
            Query query = getEntityManager().createQuery(queryString);
            return (Long) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute countWhere query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    public <E extends sym_entity> Long countWhere(Class<E> entityClass, List<Pair<String, ?>> criteria) {
        return countWhere(entityClass, criteria, false, false);
    }

    @Transactional
    public <E extends sym_entity> Long countAll(Class<E> entityClass) {
        LOGGER.info("countAll " + entityClass.getSimpleName());
        String queryString = "SELECT count(e) FROM " + entityClass.getSimpleName() + " e";
        try {
            return (Long) getEntityManager().createQuery(queryString).getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute countAll query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    @Transactional
    public <E extends sym_entity, I extends Serializable> E findById(Class<E> entityClass, I id) {
        LOGGER.info(entityClass.getSimpleName() + " findById " + id);
        try {
            E result = getEntityManager().find(entityClass, id);
            if (result != null) {
                result.refresh();
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute findById query: " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public <E extends sym_entity> List<E> findAll(Class<E> entityClass, boolean reverseOrder) {
        LOGGER.info("findAll " + entityClass.getSimpleName());
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e" + (reverseOrder ? " ORDER BY e.id DESC" : "");
        try {
            List<E> results = getEntityManager().createQuery(queryString).getResultList();
            for (E result : results) {
                result.refresh();
            }
            return results;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute findAll query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    public <E extends sym_entity> List<E> findAll(Class<E> entityClass) {
        return findAll(entityClass, false);
    }

    public <E extends sym_entity> List<E> findWhere(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                                    int maxResults, boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {

        StringBuilder conditions = new StringBuilder();

        for (Pair<String, ?> criteria : criterion) {
            if (conditions.length() > 0) {
                if (useOrQuery) {
                    conditions.append(" OR ");
                } else {
                    conditions.append(" AND ");
                }
            }
            String prefix = "", suffix = "";
            if (criteria.getRight() instanceof String && !caseSensitive) {
                prefix = "UPPER(";
                suffix = ")";
            }
            if (useLikeQuery) {
                conditions.append(prefix).append(criteria.getLeft()).append(suffix)
                          .append(" LIKE ").append(prefix)
                          .append("'%").append(criteria.getRight()).append("%'").append(suffix).append(" ");
            } else {
                conditions.append(prefix).append(criteria.getLeft()).append(suffix)
                          .append("= ").append(prefix)
                          .append("'").append(criteria.getRight()).append("'").append(suffix).append(" ");
            }
        }

        LOGGER.info(entityClass.getSimpleName() + " findWhere " + conditions);
        LOGGER.info(entityClass.getSimpleName() + " findWhere max results = " + maxResults);

        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE " +
                conditions + (reverseOrder ? " ORDER BY e.id DESC" : "");
        try {
            Query query = getEntityManager().createQuery(queryString);
            if (maxResults > 0) {
                query.setMaxResults(maxResults).getResultList();
            }
            List<E> results = query.getResultList();
            for (E result : results) {
                result.refresh();
            }
            return results;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute findWhere query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    public <E extends sym_entity> List<E> findWhere(Class<E> entityClass, List<Pair<String, ?>> criteria) {
        return findWhere(entityClass, criteria, -1, false, false, false, false);
    }

    @SuppressWarnings("unchecked")
    public <E extends sym_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria) {
        List conditions = new ArrayList<Pair<String, Object>>();
        conditions.add(criteria);
        return findWhere(entityClass, conditions, UNLIMITED_RESULTS,
                false, false, false, false);
    }

    public <E extends sym_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria, int maxResults) {
        return findWhere(entityClass, singletonList(criteria), maxResults,
                false, false, false, false);
    }

    public <E extends sym_entity> List<E> findWhere(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                                    boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {
        return findWhere(entityClass, criterion, -1, caseSensitive, useLikeQuery, useOrQuery, reverseOrder);
    }

    @SuppressWarnings("unchecked")
    public <E extends sym_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria,
                                                    boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {
        List conditions = new ArrayList<Pair<String, Object>>();
        conditions.add(criteria);
        return findWhere(entityClass, conditions, UNLIMITED_RESULTS, caseSensitive, useLikeQuery, useOrQuery, reverseOrder);
    }

    public <E extends sym_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria, int maxResults,
                                                    boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {
        return findWhere(entityClass, singletonList(criteria), maxResults, caseSensitive, useLikeQuery, useOrQuery, reverseOrder);
    }

    public <E extends sym_entity> SymResponseObject<E> findUniqueWhere(
            Class<E> entityClass, List<Pair<String, ?>> criteria) {
        return enforceUnique(findWhere(entityClass, criteria, UNLIMITED_RESULTS,
                false, false, false, false));
    }

    public <E extends sym_entity> SymResponseObject<E> findUniqueWhere(
            Class<E> entityClass, Pair<String, ?> criterion) {
        return enforceUnique(findWhere(entityClass, criterion, UNLIMITED_RESULTS));
    }

    private <E> SymResponseObject<E> enforceUnique(List<E> list) {
        if (list.size() == 1) {
            return new SymResponseObject<>(SymResponseCode.SUCCESS, list.get(0));
        } else if (list.size() > 1) {
            String alert = "Found non unique entries for " + list.get(0).getClass().getSimpleName() + "\r\n\r\n";
            for (E item : list) {
                alert += item.toString() + "\n";
            }
            new Exception(alert).printStackTrace();
            sendEmailAlert("SYMBIOSIS_CONTROL_CENTER", "Found non unique entries for " + list.get(0).getClass().getSimpleName(), alert);
            return new SymResponseObject<>(SymResponseCode.GENERAL_ERROR);
        }
        return new SymResponseObject<>(SymResponseCode.DATA_NOT_FOUND);
    }
}
