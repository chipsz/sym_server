package net.symbiosis.persistence.dao.super_class;

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import java.io.Serializable;
import java.util.List;

public interface SymbisoisDaoInterface<E extends sym_entity, I extends Serializable> {
    E saveOrUpdate(sym_entity<E> e);

    E save(sym_entity<E> e);

    void delete(sym_entity<E> e);

    E refresh(E e);

    Class<E> getEntityClass();

    E findById(I id);

    List<E> findAll();

    List<E> findWhere(List<Pair<String, ?>> criterion, int maxResults);

    SymResponseObject<E> findUniqueWhere(List<Pair<String, ?>> criterion);

    <E> SymResponseObject<E> enforceUnique(List<E> list);
}
