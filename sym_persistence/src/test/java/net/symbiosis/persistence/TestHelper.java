package net.symbiosis.persistence;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;

import java.util.Date;

import static net.symbiosis.core_lib.enumeration.SymAuthGroup.WEB_AGENT;
import static net.symbiosis.core_lib.enumeration.SymChannel.SMART_PHONE;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.ACC_ACTIVE;
import static net.symbiosis.core_lib.security.Security.generateSecureRandomBytes;
import static net.symbiosis.core_lib.utilities.CommonUtilities.decapitalizeAll;
import static net.symbiosis.core_lib.utilities.ReferenceGenerator.Gen;
import static net.symbiosis.core_lib.utilities.ReferenceGenerator.GenMills;
import static net.symbiosis.persistence.helper.SymEnumHelper.*;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class TestHelper {

    public static String genEmail() {
        return Gen() + "@" + Gen() + ".com";
    }

    public static String genMsisdn() {
        String temp = String.valueOf(GenMills());
        return "0" + temp.substring(temp.length() - 9);
    }

    public static String genUsername() {
        return decapitalizeAll(Gen());
    }

    public static sym_user createSavedSymbisisUser() {
        return createSavedSymbisisUser(false);
    }

    public static sym_user createSavedSymbisisUser(boolean useUpdate) {
        sym_user testUser = createTestSymbiosisUser();
        if (useUpdate) {
            return testUser.save();
        } else {
            return testUser.save();
        }
    }

    public static sym_user createTestSymbiosisUser() {
        return new sym_user(Gen(), Gen(), new Date(), Gen(), Gen(), "1234", 0, 0,
                new String(generateSecureRandomBytes()), genEmail(), genMsisdn(), null, fromEnum(ACC_ACTIVE),
                countryFromString("GHANA"), languageFromString("ENGLISH")).save();
    }

    public static sym_auth_user createSavedTestAuthUser(sym_user user) {
        return createSavedTestAuthUser(user, false);
    }

    public static sym_auth_user createSavedTestAuthUser(sym_user user, boolean useUpdate) {
        sym_auth_user testAuthUser = createTestAuthUser(user);
        return testAuthUser.save();
    }

    public static sym_auth_user createTestAuthUser(sym_user user) {
        return new sym_auth_user(user, fromEnum(SMART_PHONE), fromEnum(WEB_AGENT), Gen(),
                new String(generateSecureRandomBytes()).substring(0, 20), new Date(), new Date(), new Date());
    }

}
