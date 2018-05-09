package net.beyondtelecom.gopay.bt_common.security;

import static java.lang.Integer.parseInt;
import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.getProperty;

/**
 * Created by photon on 2015/12/31.
 */
public class BeyondTelecomSecurityEncryption {

	public static final String SHA256 = "SHA-256";

	public static final String DEFAULT_SECURITY_HASH = SHA256;

	public static final byte[] DEFAULT_ENCRYPTION_SALT = null;

	public static final int DEFAULT_SALT_LENGTH = 64;

	public static final int MAX_PASSWORD_TRIES = parseInt(getProperty("MaxPasswordTries"));
}
