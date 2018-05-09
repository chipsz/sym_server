package net.beyondtelecom.gopay.bt_authentication.authentication;

import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_group_role;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;
import static net.beyondtelecom.gopay.bt_authentication.authentication.BTAuthenticator.getUserByUsername;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getUserGroupRoleDao;

/**
* Created with IntelliJ IDEA.
* User: tkaviya
* Date: 8/6/13
* Time: 7:06 PM
*/
@Service
public class BTUserDetailsService implements UserDetailsService {

	Logger logger = Logger.getLogger(BTUserDetailsService.class.getSimpleName());

	protected HashMap<String, List<SimpleGrantedAuthority>> grantedAuthoritiesCache = new HashMap<>();

	protected bt_channel gopayChannel;

	public void setBeyondTelecomChannel(bt_channel gopayChannel) {
		this.gopayChannel = gopayChannel;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		BTResponseObject<bt_auth_user> userResponse = getUserByUsername(username, gopayChannel);

		bt_user user = userResponse.getResponseObject().getUser();

		boolean accountNonExpired = true, accountNonLocked = true, credentialsNonExpired = true, enabled = false;

		bt_response_code userStatus = user.getUser_status();

		if (userStatus.equals(fromEnum(ACC_ACTIVE))) {
			enabled = true;
		} else if (userStatus.equals(fromEnum(ACC_INACTIVE)) || userStatus.equals(fromEnum(ACC_SUSPENDED))) {
			accountNonLocked = false;
		} else if (userStatus.equals(fromEnum(ACC_CLOSED))) {
			accountNonExpired = false;
		} else if (userStatus.equals(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED)) || userStatus.equals(fromEnum(ACC_PASSWORD_EXPIRED))) {
			credentialsNonExpired = false;
		}

		return new User(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
			getAuthorities(null));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String userGroup)
	{
		List<SimpleGrantedAuthority> authList = new ArrayList<>();

		if (!grantedAuthoritiesCache.containsKey(userGroup))
		{
			logger.fine("Getting authorities for access group " + userGroup);

			List<bt_auth_group_role> userGroupRoles =
					getUserGroupRoleDao().findByGroup(findByName(bt_auth_group.class, userGroup));

			for (bt_auth_group_role userGroupRole : userGroupRoles)
			{
				logger.fine("Caching role " + userGroupRole.getName());
				authList.add(new SimpleGrantedAuthority(userGroupRole.getId().toString()));
			}

			//cache the authorities to avoid future db hits.
			grantedAuthoritiesCache.put(userGroup, authList);
		}
		return grantedAuthoritiesCache.get(userGroup);
	}

}
