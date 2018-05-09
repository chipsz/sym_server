package net.beyondtelecom.gopay.bt_persistence;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_group_role;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static junit.framework.Assert.*;
import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.Gen;
import static net.beyondtelecom.gopay.bt_persistence.TestHelper.createSavedSymbisisUser;
import static net.beyondtelecom.gopay.bt_persistence.TestHelper.createSavedTestAuthUser;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */

@Test
public class EntityTest {

	@BeforeClass
	public void setUp() throws Exception {
		new ClassPathXmlApplicationContext("test-bt_persistence-spring-context.xml");
	}

//	private static void enumerateAuthUsers(bt_user gopayUser) {
//
//		int numAuthUsers = gopayUser.getAuth_users() == null ? 0 : gopayUser.getAuth_users().size();
//
//		System.out.println("Number of auth users linked to gopayUser" + gopayUser.getId() + " is " + numAuthUsers);
//
//		for (bt_auth_user testUser : gopayUser.getAuth_users()) {
//			System.out.println("---------------");
//			System.out.println("Found linked testBeyondTelecomUser" + gopayUser.getId() + " entity: " + testUser.getId());
//			System.out.println("Group name: " + testUser.getGroup().getName());
//			System.out.println("Channel name: " + testUser.getChannel().getName());
//			System.out.println("Registration date: " + testUser.getRegistration_date());
//			System.out.println("---------------");
//		}
//
//	}
//
//	@Test
//	public void lazyOneToManyTest() {
//
//		System.out.println("RUNNING TEST: lazyOneToManyTest");
//
//		bt_user symOTMUser1 = createTestBeyondTelecomUser();
//		bt_auth_user authOTMUser1 = createTestAuthUser(symOTMUser1);
//
//		//persist only system user
//		getUserDao().saveOrUpdate(symOTMUser1);
//		assertNotNull(authOTMUser1.getUser().getAuth_users());
//		assertTrue(authOTMUser1.getUser().getAuth_users().size() >= 0);
//		enumerateAuthUsers(authOTMUser1.getUser());
//
//
//		bt_user symOTMUser2 = createTestBeyondTelecomUser();
//		bt_auth_user authOTMUser2 = createTestAuthUser(symOTMUser2);
//
//		//persist only auth user
//		getAuthUserDao().saveOrUpdate(authOTMUser2);
//		assertNotNull(authOTMUser2.getUser().getAuth_users());
//		assertTrue(authOTMUser2.getUser().getAuth_users().size() >= 0);
//		enumerateAuthUsers(authOTMUser2.getUser());
//
//		//reload directly from DB
//		bt_user gopayUser1 = getUserDao().findById(1L);
//		assertNotNull(gopayUser1.getAuth_users());
//		assertTrue(gopayUser1.getAuth_users().size() >= 0);
//		enumerateAuthUsers(gopayUser1);
//	}

	@Test
	public void persistenceEnumTest() {

		System.out.println("RUNNING TEST: persistenceEnumTest");

		bt_channel testChannel1 = new bt_channel(Gen(), true, true).save();
		bt_channel testChannel2 = new bt_channel(Gen(), true, false).save();
		bt_event_type testEventType = new bt_event_type(Gen(), false);
		bt_auth_group testGroup = new bt_auth_group(Gen(), true);
		bt_language testLanguage = new bt_language(Gen(), true);
		bt_role testRole = new bt_role(Gen(), true);
		bt_auth_group_role testGroupRole = new bt_auth_group_role(Gen(), true, testGroup, testRole);

		System.out.println("Saving data to database");
		assertNotNull(testChannel1.save());
		assertNotNull(testChannel2.save());
		assertNotNull(testEventType.save());
		assertNotNull(testGroup.save());
		assertNotNull(testLanguage.save());
		assertNotNull(testRole.save());
		assertNotNull(testGroupRole.save());

		System.out.println("Testing data integrity");

		/* complex enum used save() */
		assertNotNull(testChannel1.getId());
		assertTrue(testChannel1.getIs_enabled());
		assertFalse(testChannel2.is_pin_based());

		/* enum entity used saveOrUpdate() */
		assertNotNull(testEventType.getId());
		assertFalse(testEventType.getIs_enabled());
	}

	@Test
	public void authUserTest() {
	}

	@Test
	public void gopayUserTest() {
	}

	@Test
	public void deleteEntityTest() {

		System.out.println("RUNNING TEST: deleteEntityTest");

		bt_auth_user testAuthUser = createSavedTestAuthUser(createSavedSymbisisUser());

		bt_session gopayAuthLog1 = new bt_session(testAuthUser,
			"dev_1234", "auth_token1", testAuthUser.getRegistration_date(), new Date()).save();

		assertNotNull(gopayAuthLog1.getId());

		bt_session gopayAuthLog2 = new bt_session(testAuthUser,
			"dev_5678", "auth_token2", testAuthUser.getRegistration_date(), new Date()).save();

		assertNotNull(gopayAuthLog2.getId());

		bt_session gopayAuthLog3 = new bt_session(testAuthUser,
			"dev_1122", "auth_token3", new Date(), new Date()).save();

		assertNotNull(gopayAuthLog3.getId());

		gopayAuthLog2.delete();

		assertNotNull(getEntityManagerRepo().findById(bt_session.class, gopayAuthLog1.getId()));
		assertNull(getEntityManagerRepo().findById(bt_session.class, gopayAuthLog2.getId()));
		assertNotNull(getEntityManagerRepo().findById(bt_session.class, gopayAuthLog3.getId()));

		gopayAuthLog1.delete();
		gopayAuthLog3.delete();

		assertNull(getEntityManagerRepo().findById(bt_session.class, gopayAuthLog1.getId()));
		assertNull(getEntityManagerRepo().findById(bt_session.class, gopayAuthLog2.getId()));
		assertNull(getEntityManagerRepo().findById(bt_session.class, gopayAuthLog3.getId()));
	}
}
