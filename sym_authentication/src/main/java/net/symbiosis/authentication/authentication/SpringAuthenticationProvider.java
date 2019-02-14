package net.symbiosis.authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
 */

import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymEventType.LOGIN;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

public class SpringAuthenticationProvider implements AuthenticationProvider {

    Logger logger = Logger.getLogger(SpringAuthenticationProvider.class.getSimpleName());

    private final sym_channel channel = fromEnum(WEB);

    @Autowired
    private SymUserDetailsService userService;

    SpringAuthenticationProvider() {
        userService.setSymChannel(channel);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        sym_request_response_log request_response_log = new sym_request_response_log(
                channel, fromEnum(LOGIN), authentication.getDetails().toString()).save();

        logger.info("Authenticating user " + authentication.getPrincipal() + " on to channel WEB");

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        WebAuthenticationProvider webAuthenticationProvider = new WebAuthenticationProvider(
                request_response_log, username, password, null
        );

        logger.info("Initialized WebAuthenticationProvider chain provider.");


        logger.info("Running authentication chain.");
        SymResponseObject<sym_auth_user> symUserResponse = webAuthenticationProvider.authenticateUser();
        request_response_log.setSystem_user(symUserResponse.getResponseObject().getUser());

        logger.info("Authentication response: " + symUserResponse.getMessage());
        if (symUserResponse.getResponseCode() == SUCCESS) {

            sym_user user = symUserResponse.getResponseObject().getUser();

            Collection<? extends GrantedAuthority> authorities =
                    SpringWebAuthenticationProvider.getAuthorities(symUserResponse.getResponseObject().getAuth_group());

            logger.info("Returning authenticated used with granted authorities");

            request_response_log.setOutgoing_response_time(new Date()).save();

            return new UsernamePasswordAuthenticationToken(user, password, authorities);
        } else {
            SymResponseCode result = SymChainAuthenticationProvider.getMappedResponseCode(symUserResponse.getResponseCode());
            logger.info("Returning mapped auth response: " + result);

            request_response_log.setOutgoing_response_time(new Date()).save();
            throw new BadCredentialsException(result.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(Authentication.class);
    }
}
