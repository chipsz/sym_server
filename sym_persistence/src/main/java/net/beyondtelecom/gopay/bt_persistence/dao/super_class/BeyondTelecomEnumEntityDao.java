package net.beyondtelecom.gopay.bt_persistence.dao.super_class;

import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */
@Repository
@Transactional
public interface BeyondTelecomEnumEntityDao<E extends bt_entity, I extends Serializable>
        extends BeyondTelecomDaoInterface<E, I> {
    List<E> findEnabled();
    E findEnabledByName(String name);
    E findByName(String name);
}
