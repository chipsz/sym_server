package net.symbiosis.authentication.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/20/15
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */

@Test
public class SymbiosisAuthenticatorTest {

    @BeforeClass
    public void setUp() {
        new ClassPathXmlApplicationContext("test-sym_authentication-spring-context.xml");
    }

//	@Test
//	public void hashPasswordTest() throws Exception {
//		System.out.println("Testing salted hashing");
//		String salt = generateSalt();
//		String hashedPassword = hashPassword("testpass", salt);
//		System.out.println("Salted hashed password = " + hashedPassword);
//		assertNotNull(hashedPassword);
//		hashedPassword = hashPassword("1234", salt);
//		System.out.println("Salted hashed password = " + hashedPassword);
//		assertNotNull(hashedPassword);
//	}
//
//	@Test
//	public void aesEncryptTest() throws Exception {
//		System.out.println("Testing 128 bit AES encryption");
//
//		String aes128BitKey = "";
//		for (int c = 0; c < 1; c++) { aes128BitKey += generateIV(); }
//
//		String plainTextData = "Hello, this is a test";
//		String initializationVector = generateIV();
//
//		System.out.println("aes128BitKey \"" + aes128BitKey + "\"");
//		System.out.println("initializationVector \"" + initializationVector + "\"");
//		String encryptedData = encryptAES(aes128BitKey, initializationVector, plainTextData);
//		System.out.println("Encrypted \"" + plainTextData + "\" to " + encryptedData);
//		assertNotNull(encryptedData);
//		String decryptedData = decryptAES(aes128BitKey, initializationVector, encryptedData);
//		System.out.println("Decrypted data is \"" + decryptedData + "\"");
//		assertTrue(decryptedData.equals(plainTextData));
//	}
//
//	@Test
//	public void authenticateUserTest() {
//		System.out.println("Testing authenticateUser");
//
//		String plainTestPassword1 = Gen();
//		String plainTestPin1 = GenPin();
//		String passwordSalt1 = generateSalt();
//		String plainTestPassword2 = Gen();
//        String plainTestPin2 = GenPin();
//		String passwordSalt2 = generateSalt();
//
//		//channels = WEB, POS
//		sym_user symUser1 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//            hashPassword(plainTestPassword1, passwordSalt1), hashPassword(plainTestPin1, passwordSalt1),
//            0, 0, passwordSalt1, genEmail(), genMsisdn(), genMsisdn(),
//            fromEnum(ACC_PASSWORD_EXPIRED), countryFromString("ZIMBABWE"), languageFromString("ENGLISH")).save();
//
//		//channels = USSD
//		sym_user symUser2 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//            hashPassword(plainTestPassword2, passwordSalt2), hashPassword(plainTestPin2, passwordSalt2),
//            0, 0, passwordSalt2, genEmail(), genMsisdn(), genMsisdn(), fromEnum(ACC_ACTIVE),
//            countryFromString("ZIMBABWE"), languageFromString("ENGLISH")).save();
//
//		//joe
//		sym_auth_user testAuthUser1 = new sym_auth_user(symUser1, fromEnum(WEB),
//                fromEnum(WEB_ADMIN),null,null,new Date(),new Date(),new Date()).save();
//		sym_auth_user testAuthUser2 = new sym_auth_user(symUser1, fromEnum(POS_MACHINE),
//                fromEnum(WEB_AGENT),null,null,new Date(),new Date(),new Date()).save();
//
//		//mj
//		sym_auth_user testAuthUser3 = new sym_auth_user(symUser2,fromEnum(USSD),
//                fromEnum(WEB_CLERK),null,null,new Date(),new Date(),new Date()).save();
//
//		assertEquals(authenticateUser(fromEnum(WEB), symUser1.getUsername(), plainTestPassword1, true).getResponseCode(), ACC_PASSWORD_EXPIRED);
//		assertEquals(authenticateUser(fromEnum(POS_MACHINE), symUser1.getUsername(), plainTestPassword1, true).getResponseCode(), ACC_PASSWORD_EXPIRED);
//		assertEquals(authenticateUser(fromEnum(DESKTOP), symUser1.getUsername(), plainTestPassword1, true).getResponseCode(), AUTH_NON_EXISTENT);
//
//		System.out.println("Setting user account status to " + fromEnum(ACC_ACTIVE).getName());
//
//		symUser1.setUser_status(fromEnum(ACC_ACTIVE));
////		getUserDao().save(symUser1);
//
//		System.out.println("User status is now " + symUser1.getUser_status().getName());
//
//		assertTrue(symUser1.getUser_status().getName().equals(ACC_ACTIVE.name()));
//
//		assertEquals(authenticateUser(fromEnum(WEB), symUser1.getUsername(), plainTestPassword1, true).getResponseCode(), SUCCESS);
//		assertEquals(authenticateUser(fromEnum(POS_MACHINE), symUser1.getUsername(), plainTestPin1, true).getResponseCode(), SUCCESS);
//		assertEquals(authenticateUser(fromEnum(DESKTOP), symUser1.getUsername(), plainTestPassword1, true).getResponseCode(), AUTH_NON_EXISTENT);
//
//		assertEquals(authenticateUser(fromEnum(DESKTOP), symUser2.getUsername(), plainTestPassword2, true).getResponseCode(), AUTH_NON_EXISTENT);
//		assertEquals(authenticateUser(fromEnum(USSD), symUser2.getUsername(), "1234", true).getResponseCode(), AUTH_INCORRECT_PASSWORD);
//		assertTrue(symUser2.getPin_tries() == 1);
//		assertEquals(authenticateUser(fromEnum(USSD), symUser2.getUsername(), plainTestPin2, true).getResponseCode(), SUCCESS);
//		assertTrue(symUser2.getPin_tries() == 0);
//		assertEquals(authenticateUser(fromEnum(USSD), symUser2.getEmail(), plainTestPin2, true).getResponseCode(), SUCCESS);
//		assertEquals(authenticateUser(fromEnum(USSD), symUser2.getMsisdn(), plainTestPin2, true).getResponseCode(), SUCCESS);
//
//		for (int c = 0; c < SymbiosisSecurityEncryption.MAX_PASSWORD_TRIES; c++) {
//			assertEquals(authenticateUser(fromEnum(USSD), symUser2.getUsername(), "1234", true).getResponseCode(), AUTH_INCORRECT_PASSWORD);
//		}
//		assertEquals(authenticateUser(fromEnum(USSD), symUser2.getUsername(), "1234", true).getResponseCode(), ACC_PASSWORD_TRIES_EXCEEDED);
//	}
//
//	@Test
//	public void findByUsernameAndChannelTest() throws Exception {
//		System.out.println("Testing findByUsernameAndChannel");
//
//		//channels = fromEnum(WEB), fromEnum(POS)
//		sym_user symUser1 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//                Gen(), Gen(),0,0, Gen(), genEmail(), genMsisdn(), genMsisdn(), fromEnum(ACC_PASSWORD_EXPIRED),
//                countryFromString("ZIMBABWE"), languageFromString("ENGLISH")).save();
//
//		//channels = fromEnum(USSD)
//		sym_user symUser2 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//                Gen(), Gen(), 0, 0, Gen(), genEmail(), genMsisdn(), genMsisdn(), fromEnum(ACC_ACTIVE),
//                countryFromString("ZIMBABWE"), languageFromString("ENGLISH")).save();
//
//		//joe
//		sym_auth_user testAuthUser1 = new sym_auth_user(symUser1, fromEnum(WEB),
//                fromEnum(WEB_ADMIN),null,null,new Date(),new Date(),new Date()).save();
//		sym_auth_user testAuthUser2 = new sym_auth_user(symUser1, fromEnum(POS_MACHINE),
//                fromEnum(WEB_AGENT),null,null,new Date(),new Date(),new Date()).save();
//
//		//mj
//		sym_auth_user testAuthUser3 = new sym_auth_user(symUser2,fromEnum(USSD),
//                fromEnum(WEB_CLERK),null,null,new Date(),new Date(),new Date()).save();
//
//
//		/* right user, wrong channel */
//		assertNull(getAuthUserDao().findByUsernameAndChannel(testAuthUser1.getUser().getUsername(), fromEnum(SMART_PHONE)));
//
//		/* no user, right channel */
//		assertNull(getAuthUserDao().findByUsernameAndChannel(null, fromEnum(USSD)));
//		/* right user, no channel */
//		assertNull(getAuthUserDao().findByUsernameAndChannel(testAuthUser2.getUser().getUsername(), null));
//		/* no user, no channel */
//		assertNull(getAuthUserDao().findByUsernameAndChannel(null, null));
//		/* wrong user, wrong channel */
//		assertNull(getAuthUserDao().findByUsernameAndChannel("wronguser", fromEnum(DESKTOP)));
//		/* right user, right channel, but users are not linked */
//		assertNull(getAuthUserDao().findByUsernameAndChannel(testAuthUser3.getUser().getUsername(), fromEnum(WEB)));
//
//		/* right user, linked to 2 channels */
//		sym_auth_user result = getAuthUserDao().findByUsernameAndChannel(testAuthUser2.getUser().getUsername(), fromEnum(POS_MACHINE));
//		assertNotNull(result);
//		assertEquals(result.getUser().getUsername(), testAuthUser2.getUser().getUsername());
//		assertEquals(result.getChannel().getId(), fromEnum(POS_MACHINE).getId());
//		System.out.println("Found auth_user with username " + result.getUser().getUsername() + " on channel " + result.getChannel());
//
//		result = getAuthUserDao().findByUsernameAndChannel(testAuthUser2.getUser().getUsername(), fromEnum(WEB), false);
//		assertNotNull(result);
//		assertEquals(result.getUser().getUsername(), testAuthUser1.getUser().getUsername());
//		assertEquals(result.getChannel().getId(), fromEnum(WEB).getId());
//		System.out.println("Found auth_user with username " + result.getUser().getUsername() + " on channel " + result.getChannel());
//
//		/* right user, linked to 2 channels, but password is expired, and explicit checking enabled */
//		result = getAuthUserDao().findByUsernameAndChannel(testAuthUser2.getUser().getUsername(), fromEnum(WEB), true);
//        assertNull(result);
//
//		/* right user, right channel, right account status, and explicit checking enabled */
//		result = getAuthUserDao().findByUsernameAndChannel(testAuthUser3.getUser().getUsername(), fromEnum(USSD), true);
//		assertNotNull(result);
//		assertEquals(result.getUser().getUsername(), testAuthUser3.getUser().getUsername());
//		assertEquals(result.getChannel().getId(), fromEnum(USSD).getId());
//		System.out.println("Found auth_user with username " + result.getUser().getUsername() + " on channel " + result.getChannel());
//	}
//
//	@Test
//	public void getUserByEmailTest() throws Exception {
//
//		System.out.println("Testing getUserByEmail");
//
//		//channels = fromEnum(WEB), fromEnum(POS)
//		sym_user symUser1 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//                Gen(), Gen(), 0, 0, Gen(), genEmail(), genMsisdn(), genMsisdn(), fromEnum(ACC_SUSPENDED),
//                countryFromString("ZIMBABWE"), languageFromString("ENGLISH")).save();
//
//		//channels = fromEnum(USSD)
//		sym_user symUser2 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//                Gen(), Gen(), 0, 0, Gen(), genEmail(), genMsisdn(), genMsisdn(), fromEnum(ACC_ACTIVE),
//                countryFromString("GHANA"), languageFromString("ENGLISH")).save();
//
//		//joe
//		sym_auth_user testAuthUser1 = new sym_auth_user(symUser1, fromEnum(WEB),
//                fromEnum(WEB_ADMIN),null,null,new Date(),new Date(),new Date()).save();
//		sym_auth_user testAuthUser2 = new sym_auth_user(symUser1, fromEnum(POS_MACHINE),
//                fromEnum(WEB_AGENT),null,null,new Date(),new Date(),new Date()).save();
//
//		//mj
//		sym_auth_user testAuthUser3 = new sym_auth_user(symUser2,fromEnum(USSD),
//                fromEnum(WEB_CLERK),null,null,new Date(),new Date(),new Date()).save();
//
//		/* passing in email as username with optimistic search disabled */
//		SymResponseObject<sym_auth_user> userResponse = getUserByUsername(symUser1.getEmail(), fromEnum(WEB));
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_USERNAME));
//
//		userResponse = getUserByUsername(symUser1.getEmail(), fromEnum(WEB), false);
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_USERNAME));
//
//		/* passing in email as username with optimistic search enabled, but password is expired */
//		userResponse = getUserByUsername(symUser1.getEmail(), fromEnum(WEB), true);
//		assertNotNull(userResponse.getResponseObject());
//		assertTrue(userResponse.getResponseObject().getUser().getEmail().equals(symUser1.getEmail()));
//		assertTrue(userResponse.getResponseObject().getChannel().getId().equals(fromEnum(WEB).getId()));
//
//		/* passing in email explicitly with wrong data */
//		userResponse = getUserByEmail(symUser2.getEmail(), fromEnum(WEB));
//		assertTrue(userResponse.getResponseCode().equals(AUTH_NON_EXISTENT));
//
//		/* passing in email explicitly with wrong data */
//		userResponse = getUserByEmail(null, fromEnum(WEB));
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_REQUEST));
//
//		/* passing in email explicitly with wrong data */
//		userResponse = getUserByEmail(null, null);
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_REQUEST));
//
//		/* passing in email explicitly with wrong data */
//		userResponse = getUserByEmail(symUser2.getEmail(), null);
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_REQUEST));
//
//		/* passing in email explicitly with wrong data */
//		userResponse = getUserByEmail("1234", fromEnum(SMART_PHONE));
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_EMAIL));
//
//		/* passing in email explicitly with correct data */
//		userResponse = getUserByEmail(symUser2.getEmail(), fromEnum(USSD));
//		assertTrue(userResponse.getResponseCode().equals(SUCCESS));
//		assertTrue(userResponse.getResponseObject().getUser().getEmail().equals(symUser2.getEmail()));
//		assertTrue(userResponse.getResponseObject().getChannel().getId().equals(fromEnum(USSD).getId()));
//	}
//
//	@Test
//	public void getUserByMsisdnTest() throws Exception {
//
//		System.out.println("Testing getUserByMsisdn");
//
//		//channels = fromEnum(WEB), fromEnum(POS)
//		sym_user symUser1 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//                Gen(), Gen(), 0, 0, Gen(), genEmail(), genMsisdn(), genMsisdn(), fromEnum(ACC_INACTIVE),
//                countryFromString("ZIMBABWE"), languageFromString("ENGLISH")).save();
//
//		//channels = fromEnum(USSD)
//		sym_user symUser2 = new sym_user(genUsername(), genUsername(), new Date(), genUsername(),
//                Gen(), Gen(), 0, 0, Gen(), genEmail(), genMsisdn(), genMsisdn(), fromEnum(ACC_ACTIVE),
//                countryFromString("SOUTH AFRICA"), languageFromString("ENGLISH")).save();
//
//		//joe
//		sym_auth_user testAuthUser1 = new sym_auth_user(symUser1, fromEnum(WEB),
//                fromEnum(WEB_ADMIN),null,null,new Date(),new Date(),new Date()).save();
//		sym_auth_user testAuthUser2 = new sym_auth_user(symUser1, fromEnum(POS_MACHINE),
//                fromEnum(WEB_AGENT),null,null,new Date(),new Date(),new Date()).save();
//
//		//mj
//		sym_auth_user testAuthUser3 = new sym_auth_user(symUser2,fromEnum(USSD),
//                fromEnum(WEB_CLERK),null,null,new Date(),new Date(),new Date()).save();
//
//		/* passing in msisdn as username with optimistic search disabled */
//		SymResponseObject<sym_auth_user> userResponse = getUserByUsername(symUser1.getMsisdn(), fromEnum(WEB));
//		assertTrue(userResponse.getResponseCode().equals(AUTH_NON_EXISTENT));
//
//		userResponse = getUserByUsername(symUser1.getMsisdn(), fromEnum(WEB), false);
//		assertTrue(userResponse.getResponseCode().equals(AUTH_NON_EXISTENT));
//
//		/* passing in msisdn as username with optimistic search enabled */
//		userResponse = getUserByUsername(symUser1.getMsisdn(), fromEnum(WEB), true);
//		assertTrue(userResponse.getResponseCode().equals(SUCCESS));
//		assertTrue(userResponse.getResponseObject().getUser().getMsisdn().equals(symUser1.getMsisdn()));
//		assertTrue(userResponse.getResponseObject().getChannel().getId().equals(fromEnum(WEB).getId()));
//
//		/* passing in msisdn explicitly with wrong data */
//		userResponse = getUserByMsisdn(null, fromEnum(WEB));
//		System.out.println("Got auth response " + userResponse.getResponseCode());
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_REQUEST));
//
//		/* passing in msisdn explicitly with wrong data */
//		userResponse = getUserByMsisdn(null, null);
//		System.out.println("Got auth response " + userResponse.getResponseCode());
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_REQUEST));
//
//		/* passing in msisdn explicitly with wrong data */
//		userResponse = getUserByMsisdn("", fromEnum(SMART_PHONE));
//		System.out.println("Got auth response " + userResponse.getResponseCode());
//		assertTrue(userResponse.getResponseCode().equals(INPUT_INVALID_MSISDN));
//
//		/* passing in msisdn explicitly with wrong data */
//		userResponse = getUserByMsisdn(symUser2.getMsisdn(), fromEnum(WEB));
//		System.out.println("Got auth response " + userResponse.getResponseCode());
//		assertTrue(userResponse.getResponseCode().equals(AUTH_NON_EXISTENT));
//
//		/* passing in msisdn explicitly with correct data */
//		userResponse = getUserByMsisdn(symUser2.getMsisdn(), fromEnum(USSD));
//		assertTrue(userResponse.getResponseCode().equals(SUCCESS));
//		assertTrue(userResponse.getResponseObject().getUser().getMsisdn().equals(symUser2.getMsisdn()));
//		assertTrue(userResponse.getResponseObject().getChannel().getId().equals(fromEnum(USSD).getId()));
//	}
}
