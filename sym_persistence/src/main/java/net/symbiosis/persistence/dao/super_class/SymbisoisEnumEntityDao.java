package net.symbiosis.persistence.dao.super_class;

import net.symbiosis.persistence.entity.super_class.sym_entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */
@Repository
@Transactional
public interface SymbisoisEnumEntityDao<E extends sym_entity, I extends Serializable>
        extends SymbisoisDaoInterface<E, I> {
    List<E> findEnabled();

    E findEnabledByName(String name);

    E findByName(String name);
}
