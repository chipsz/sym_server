package net.beyondtelecom.gopay.bt_persistence;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;

import java.util.Date;

import static net.beyondtelecom.bt_core_lib.enumeration.BTAuthGroup.WEB_AGENT;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.SMART_PHONE;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.ACC_ACTIVE;
import static net.beyondtelecom.bt_core_lib.utilities.CommonUtilities.decapitalizeAll;
import static net.beyondtelecom.gopay.bt_common.security.Security.generateSecureRandomBytes;
import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.Gen;
import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.GenMills;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.*;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class TestHelper {

	public static String genEmail() { return Gen() + "@" + Gen() + ".com"; }

	public static String genMsisdn() {
		String temp = String.valueOf(GenMills());
		return "0" + temp.substring(temp.length() - 9);
	}

	public static String genUsername() { return decapitalizeAll(Gen()); }

	public static bt_user createSavedSymbisisUser() { return createSavedSymbisisUser(false); }

	public static bt_user createSavedSymbisisUser(boolean useUpdate) {
		bt_user testUser = createTestBeyondTelecomUser();
		if (useUpdate) {
			return testUser.save();
		} else {
			return testUser.save();
		}
	}

	public static bt_user createTestBeyondTelecomUser() {
		return new bt_user(Gen(), Gen(), new Date(), Gen(), Gen(), "1234", 0, 0,
                new String(generateSecureRandomBytes()), genEmail(), genMsisdn(), null, fromEnum(ACC_ACTIVE),
                countryFromString("GHANA"), languageFromString("ENGLISH")).save();
	}

	public static bt_auth_user createSavedTestAuthUser(bt_user user) {
		return createSavedTestAuthUser(user, false);
	}

	public static bt_auth_user createSavedTestAuthUser(bt_user user, boolean useUpdate) {
		bt_auth_user testAuthUser = createTestAuthUser(user);
		return testAuthUser.save();
	}

	public static bt_auth_user createTestAuthUser(bt_user user) {
		return new bt_auth_user(user, fromEnum(SMART_PHONE), fromEnum(WEB_AGENT), Gen(),
			new String(generateSecureRandomBytes()).substring(0,20), new Date(), new Date(), new Date());
	}

}
