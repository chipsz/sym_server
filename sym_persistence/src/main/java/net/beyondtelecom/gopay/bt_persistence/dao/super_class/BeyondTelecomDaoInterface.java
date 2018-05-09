package net.beyondtelecom.gopay.bt_persistence.dao.super_class;

import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

import java.io.Serializable;
import java.util.List;

public interface BeyondTelecomDaoInterface<E extends bt_entity, I extends Serializable>
{
    E saveOrUpdate(bt_entity<E> e);

    E save(bt_entity<E> e);

    void delete(bt_entity<E> e);

    E refresh(E e);

    Class<E> getEntityClass();

    E findById(I id);

	List<E> findAll();

    List<E> findWhere(List<Pair<String, ?>> criterion, int maxResults);

    BTResponseObject<E> findUniqueWhere(List<Pair<String, ?>> criterion);

    <E> BTResponseObject<E> enforceUnique(List<E> list);
}
