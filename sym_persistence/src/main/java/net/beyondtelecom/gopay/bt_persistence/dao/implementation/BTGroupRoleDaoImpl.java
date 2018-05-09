package net.beyondtelecom.gopay.bt_persistence.dao.implementation;

import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.dao.complex_type.BTGroupRoleDao;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_group_role;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ***************************************************************************
 * *
 * Created:     20 / 09 / 2015                                             *
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
public class BTGroupRoleDaoImpl extends BTEnumEntityDaoImpl<bt_auth_group_role, Long>
        implements BTGroupRoleDao {
    protected BTGroupRoleDaoImpl() { super(bt_auth_group_role.class); }

    @Override
    public List<bt_auth_group_role> findByGroup(bt_auth_group gopayGroup) {
        return findWhere(new Pair<String, Object>("group_id", gopayGroup.getId()));
    }
}
