package net.symbiosis.authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * *************************************************************************
 */

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_group_role;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static net.symbiosis.core_lib.enumeration.SymResponseCode.ACC_ACTIVE;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

public class SpringWebAuthenticationProvider extends WebAuthenticationProvider {

    protected static final Logger logger = Logger.getLogger(SpringWebAuthenticationProvider.class.getSimpleName());

    public SpringWebAuthenticationProvider(sym_request_response_log requestResponseLog, String username,
                                           String password, String deviceId) {
        super(requestResponseLog, username, password, deviceId);
    }

    public User getSpringUser() {

        if (symAuthUser == null) {
            return null;
        }

        return new User(
                symAuthUser.getUser().getUsername(),
                symAuthUser.getUser().getPassword(),
                symAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account enabled
                symAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account non expired
                symAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //credentials non expired
                symAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account non locked
                getAuthorities(symAuthUser.getAuth_group().getName())
        );
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(String userGroup) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();

        logger.info("Getting authorities for access group " + userGroup);

        List<sym_auth_group_role> userGroupRoles = getEntityManagerRepo().findWhere(
                sym_auth_group_role.class, new Pair<String, Object>("auth_group.name", userGroup));

        for (sym_auth_group_role authGroupRole : userGroupRoles) {
            logger.info("Adding role " + authGroupRole.getName());
            authList.add(new SimpleGrantedAuthority(authGroupRole.getName()));
        }

        return authList;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(sym_auth_group auth_group) {
        return getAuthorities(auth_group.getName());
    }
}
