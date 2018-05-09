package net.symbiosis.persistence;

import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.sym_auth_group_role;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.enumeration.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static junit.framework.Assert.*;
import static net.symbiosis.common.utilities.ReferenceGenerator.Gen;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */

@Test
public class EntityTest {

    @BeforeClass
    public void setUp() {
        new ClassPathXmlApplicationContext("test-sym_persistence-spring-context.xml");
    }

//	private static void enumerateAuthUsers(sym_user symUser) {
//
//		int numAuthUsers = symUser.getAuth_users() == null ? 0 : symUser.getAuth_users().size();
//
//		System.out.println("Number of auth users linked to symUser" + symUser.getId() + " is " + numAuthUsers);
//
//		for (sym_auth_user testUser : symUser.getAuth_users()) {
//			System.out.println("---------------");
//			System.out.println("Found linked testSymbiosisUser" + symUser.getId() + " entity: " + testUser.getId());
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
//		sym_user symOTMUser1 = createTestSymbiosisUser();
//		sym_auth_user authOTMUser1 = createTestAuthUser(symOTMUser1);
//
//		//persist only system user
//		getUserDao().saveOrUpdate(symOTMUser1);
//		assertNotNull(authOTMUser1.getUser().getAuth_users());
//		assertTrue(authOTMUser1.getUser().getAuth_users().size() >= 0);
//		enumerateAuthUsers(authOTMUser1.getUser());
//
//
//		sym_user symOTMUser2 = createTestSymbiosisUser();
//		sym_auth_user authOTMUser2 = createTestAuthUser(symOTMUser2);
//
//		//persist only auth user
//		getAuthUserDao().saveOrUpdate(authOTMUser2);
//		assertNotNull(authOTMUser2.getUser().getAuth_users());
//		assertTrue(authOTMUser2.getUser().getAuth_users().size() >= 0);
//		enumerateAuthUsers(authOTMUser2.getUser());
//
//		//reload directly from DB
//		sym_user symUser1 = getUserDao().findById(1L);
//		assertNotNull(symUser1.getAuth_users());
//		assertTrue(symUser1.getAuth_users().size() >= 0);
//		enumerateAuthUsers(symUser1);
//	}

    @Test
    public void persistenceEnumTest() {

        System.out.println("RUNNING TEST: persistenceEnumTest");

        sym_channel testChannel1 = new sym_channel(Gen(), true, true).save();
        sym_channel testChannel2 = new sym_channel(Gen(), true, false).save();
        sym_event_type testEventType = new sym_event_type(Gen(), false);
        sym_auth_group testGroup = new sym_auth_group(Gen(), true);
        sym_language testLanguage = new sym_language(Gen(), true);
        sym_role testRole = new sym_role(Gen(), true);
        sym_auth_group_role testGroupRole = new sym_auth_group_role(Gen(), true, testGroup, testRole);

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
    public void symUserTest() {
    }

    @Test
    public void deleteEntityTest() {

        System.out.println("RUNNING TEST: deleteEntityTest");

        sym_auth_user testAuthUser = TestHelper.createSavedTestAuthUser(TestHelper.createSavedSymbisisUser());

        sym_session symAuthLog1 = new sym_session(testAuthUser,
                "dev_1234", "auth_token1", testAuthUser.getRegistration_date(), new Date()).save();

        assertNotNull(symAuthLog1.getId());

        sym_session symAuthLog2 = new sym_session(testAuthUser,
                "dev_5678", "auth_token2", testAuthUser.getRegistration_date(), new Date()).save();

        assertNotNull(symAuthLog2.getId());

        sym_session symAuthLog3 = new sym_session(testAuthUser,
                "dev_1122", "auth_token3", new Date(), new Date()).save();

        assertNotNull(symAuthLog3.getId());

        symAuthLog2.delete();

        assertNotNull(getEntityManagerRepo().findById(sym_session.class, symAuthLog1.getId()));
        assertNull(getEntityManagerRepo().findById(sym_session.class, symAuthLog2.getId()));
        assertNotNull(getEntityManagerRepo().findById(sym_session.class, symAuthLog3.getId()));

        symAuthLog1.delete();
        symAuthLog3.delete();

        assertNull(getEntityManagerRepo().findById(sym_session.class, symAuthLog1.getId()));
        assertNull(getEntityManagerRepo().findById(sym_session.class, symAuthLog2.getId()));
        assertNull(getEntityManagerRepo().findById(sym_session.class, symAuthLog3.getId()));
    }
}
