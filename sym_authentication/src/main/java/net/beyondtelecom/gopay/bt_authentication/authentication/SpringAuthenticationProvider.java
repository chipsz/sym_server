package net.beyondtelecom.gopay.bt_authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
*/

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
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

import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.WEB;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.LOGIN;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.SUCCESS;
import static net.beyondtelecom.gopay.bt_authentication.authentication.BTChainAuthenticationProvider.getMappedResponseCode;
import static net.beyondtelecom.gopay.bt_authentication.authentication.SpringWebAuthenticationProvider.getAuthorities;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;

public class SpringAuthenticationProvider implements AuthenticationProvider {

	Logger logger = Logger.getLogger(SpringAuthenticationProvider.class.getSimpleName());

	private final bt_channel gopayChannel = fromEnum(WEB);

	@Autowired
	private BTUserDetailsService userService;

	SpringAuthenticationProvider() {
		userService.setBeyondTelecomChannel(gopayChannel);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		bt_request_response_log request_response_log = new bt_request_response_log(
            gopayChannel, fromEnum(LOGIN), authentication.getDetails().toString()).save();

		logger.info("Authenticating user " + authentication.getPrincipal() + " on to channel WEB");

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		WebAuthenticationProvider webAuthenticationProvider = new WebAuthenticationProvider(
				request_response_log, username, password, null
		);

		logger.info("Initialized WebAuthenticationProvider chain provider.");


		logger.info("Running authentication chain.");
		BTResponseObject<bt_auth_user> gopayUserResponse = webAuthenticationProvider.authenticateUser();
		request_response_log.setSystem_user(gopayUserResponse.getResponseObject().getUser());

		logger.info("Authentication response: " + gopayUserResponse.getMessage());
		if (gopayUserResponse.getResponseCode() == SUCCESS) {

			bt_user user = gopayUserResponse.getResponseObject().getUser();

			Collection<? extends GrantedAuthority> authorities =
                getAuthorities(gopayUserResponse.getResponseObject().getAuth_group());

			logger.info("Returning authenticated used with granted authorities");

			request_response_log.setOutgoing_response_time(new Date()).save();

			return new UsernamePasswordAuthenticationToken(user, password, authorities);
		} else {
			BTResponseCode result = getMappedResponseCode(gopayUserResponse.getResponseCode());
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
