package net.beyondtelecom.gopay.bt_authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * Copyright:   beyondtelecom Entertainment                                     *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
 * This program is free software; you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License as published by    *
 * the Free Software Foundation; either version 2 of the License, or       *
 * (at your option) any later version.                                     *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the         *
 * GNU General Public License for more details.                            *
 * *************************************************************************
*/

import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_group_role;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.ACC_ACTIVE;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

public class SpringWebAuthenticationProvider extends WebAuthenticationProvider {

    protected static final Logger logger = Logger.getLogger(SpringWebAuthenticationProvider.class.getSimpleName());

    public SpringWebAuthenticationProvider(bt_request_response_log requestResponseLog, String username,
                                           String password, String deviceId) {
		super(requestResponseLog, username, password, deviceId);
	}

	public User getSpringUser() {

        if (btAuthUser == null) { return null; }

        return new User(
            btAuthUser.getUser().getUsername(),
            btAuthUser.getUser().getPassword(),
            btAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account enabled
            btAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account non expired
            btAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //credentials non expired
            btAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account non locked
            getAuthorities(btAuthUser.getAuth_group().getName())
        );
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(String userGroup)
    {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();

        logger.info("Getting authorities for access group " + userGroup);

        List<bt_auth_group_role> userGroupRoles = getEntityManagerRepo().findWhere(
            bt_auth_group_role.class, new Pair<String, Object>("auth_group.name", userGroup));

        for (bt_auth_group_role authGroupRole : userGroupRoles) {
            logger.info("Adding role " + authGroupRole.getName());
            authList.add(new SimpleGrantedAuthority(authGroupRole.getName()));
        }

        return authList;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(bt_auth_group auth_group) {
        return getAuthorities(auth_group.getName());
    }
}
