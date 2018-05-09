package net.beyondtelecom.gopay.bt_persistence.dao.implementation;

import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.dao.super_class.AbstractDao;
import net.beyondtelecom.gopay.bt_persistence.dao.super_class.BeyondTelecomEnumEntityDao;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/**
 * ***************************************************************************
 * *
 * Created:     19 / 09 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
 * *
 * This program is free software; you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License as published by    *
 * the Free Software Foundation; either version 2 of the License, or       *
 * (at your option) any later version.                                     *
 * *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the         *
 * GNU General Public License for more details.                            *
 * *
 * ****************************************************************************
 */

@Repository
@Transactional
public abstract class BTEnumEntityDaoImpl<E extends bt_entity, I extends Serializable>
        extends AbstractDao<E, I> implements BeyondTelecomEnumEntityDao<E, I> {

    protected BTEnumEntityDaoImpl(Class<E> entityClass) { super(entityClass); }

    @Override
    public List<E> findEnabled() {
	    System.out.println(getEntityClass().getSimpleName() + " findEnabled = true" );
	    return getEntityManagerRepo().findWhere(getEntityClass(), new Pair<>("is_enabled", true));
    }

    @Override
    public E findEnabledByName(String name) {
	    System.out.println(getEntityClass().getSimpleName() + " findEnabledByName " + name);
	    return getEntityManagerRepo().findWhere(getEntityClass(), Arrays.asList(new Pair<>("is_enabled", true), new Pair<>("name", name))).get(0);
    }

    @Override
    public E findByName(String name) {
	    System.out.println(getEntityClass().getSimpleName() + " findByName " + name);
	    return getEntityManagerRepo().findWhere(getEntityClass(), new Pair<>("name", name)).get(0);
    }
}
