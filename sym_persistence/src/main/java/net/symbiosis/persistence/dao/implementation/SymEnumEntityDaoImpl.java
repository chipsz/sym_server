package net.symbiosis.persistence.dao.implementation;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.dao.super_class.AbstractDao;
import net.symbiosis.persistence.dao.super_class.SymbisoisEnumEntityDao;
import net.symbiosis.persistence.entity.super_class.sym_entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

/**
 * ***************************************************************************
 * *
 * Created:     19 / 09 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * ****************************************************************************
 */

@Repository
@Transactional
public abstract class SymEnumEntityDaoImpl<E extends sym_entity, I extends Serializable>
        extends AbstractDao<E, I> implements SymbisoisEnumEntityDao<E, I> {

    protected SymEnumEntityDaoImpl(Class<E> entityClass) {
        super(entityClass);
    }

    @Override
    public List<E> findEnabled() {
        System.out.println(getEntityClass().getSimpleName() + " findEnabled = true");
        return getEntityManagerRepo().findWhere(getEntityClass(), new Pair<>("is_enabled", 1));
    }

    @Override
    public E findEnabledByName(String name) {
        System.out.println(getEntityClass().getSimpleName() + " findEnabledByName " + name);
        return getEntityManagerRepo().findWhere(getEntityClass(), Arrays.asList(new Pair<>("is_enabled", 1), new Pair<>("name", name))).get(0);
    }

    @Override
    public E findByName(String name) {
        System.out.println(getEntityClass().getSimpleName() + " findByName " + name);
        return getEntityManagerRepo().findWhere(getEntityClass(), new Pair<>("name", name)).get(0);
    }
}
