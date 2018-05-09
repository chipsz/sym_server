//package net.beyondtelecom.gopay.bt_persistence.helper;
//
//import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.gopay_session;
//import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.gopay_request_response_log;
//import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.gopay_auth_user;
//import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.gopay_user;
//import org.testng.annotations.Test;
//
//import java.util.Date;
//
//import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEventTypeDao;
//import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getResponseCodeDao;
//import static net.beyondtelecom.gopay.bt_persistence.helper.BeyondTelecomConfigHelper.*;
//import static net.beyondtelecom.gopay.bt_persistence.helper.BeyondTelecomEntityHelper.createAndSaveAuthUser;
//import static net.beyondtelecom.gopay.bt_persistence.helper.BeyondTelecomEntityHelper.createAndSaveBeyondTelecomUser;
//import static net.beyondtelecom.gopay.bt_persistence.helper.BeyondTelecomLogHelper.logSystemEvent;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertNull;
//
///***************************************************************************
// * *
// * Created:     19 / 10 / 2016                                             *
// * Author:      Tsungai Kaviya                                             *
// * Contact:     tsungai.kaviya@gmail.com                                   *
// * *
// ***************************************************************************/
//
//public class BeyondTelecomLogHelperTest {
//
//	@Test
//	public void testLogSystemEvent() {
//
//		System.out.println("RUNNING TEST: testLogSystemEvent");
//
//		gopay_user symLogUser = createAndSaveBeyondTelecomUser("log", "master", new Date(), "lmaster", "logs", 0, "fail",
//				"logs@system.com","0111122222", fromEnum(ACC_SUSPENDED), countryFromString("SOUTH_AFRICA"), languageFromString("ENGLISH"));
//
//		gopay_auth_user testAuthUser =
//				createAndSaveAuthUser(symLogUser,E_POS_TILL,fromEnum(WEB)_ADMIN,"0","token",new Date(),new Date(),new Date());
//
//
//		gopay_request_response_log gopayEventLog1 = new gopay_request_response_log(testAuthUser,
//				getEventTypeDao().findEnabled().get(0),
//				getResponseCodeDao().findAll().get(0), 1L, null, "test event log 1");
//
//		logSystemEvent(gopayEventLog1);
//
//		gopay_session gopayEventLog2 = new gopay_session(testAuthUser.getUser(),
//				null, getEventTypeDao().findEnabled().get(0),
//				getResponseCodeDao().findAll().get(0), 1L, null, "test event log 3");
//
//		logSystemEvent(gopayEventLog2);
//
//		gopay_session gopayEventLog3 = new gopay_session(testAuthUser,
//				getEventTypeDao().findEnabled().get(0),
//				getResponseCodeDao().findAll().get(0), 1L, null, "test event log 3");
//
//		gopayEventLog3.setUser(null);
//		gopayEventLog3.setChannel(null);
//
//		logSystemEvent(gopayEventLog3);
//
//		try { Thread.sleep(3000L); } catch (InterruptedException e) { e.printStackTrace(); }
//
//		assertNotNull(gopayEventLog1.getId());
//		assertNotNull(gopayEventLog1.getTimestamp());
//
//		assertNotNull(gopayEventLog2.getId());
//		assertNotNull(gopayEventLog2.getChannel());
//		assertNotNull(gopayEventLog2.getTimestamp());
//
//		assertNull(gopayEventLog2);
//		assertNotNull(gopayEventLog3);
//
//	}
//}
