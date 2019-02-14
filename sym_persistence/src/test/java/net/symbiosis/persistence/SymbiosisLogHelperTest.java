//import org.testng.annotations.Test;
//
//import java.util.Date;
//
//import static DaoManager.getEventTypeDao;
//import static DaoManager.getResponseCodeDao;
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
//public class SymbiosisLogHelperTest {
//
//	@Test
//	public void testLogSystemEvent() {
//
//		System.out.println("RUNNING TEST: testLogSystemEvent");
//
//		sym_user symLogUser = createAndSaveSymbiosisUser("log", "master", new Date(), "lmaster", "logs", 0, "fail",
//				"logs@system.com","0111122222", fromEnum(ACC_SUSPENDED), countryFromString("SOUTH_AFRICA"), languageFromString("ENGLISH"));
//
//		sym_auth_user testAuthUser =
//				createAndSaveAuthUser(symLogUser,E_POS_TILL,fromEnum(WEB)_ADMIN,"0","token",new Date(),new Date(),new Date());
//
//
//		sym_request_response_log symEventLog1 = new sym_request_response_log(testAuthUser,
//				getEventTypeDao().findEnabled().get(0),
//				getResponseCodeDao().findAll().get(0), 1L, null, "test event log 1");
//
//		logSystemEvent(symEventLog1);
//
//		sym_session symEventLog2 = new sym_session(testAuthUser.getUser(),
//				null, getEventTypeDao().findEnabled().get(0),
//				getResponseCodeDao().findAll().get(0), 1L, null, "test event log 3");
//
//		logSystemEvent(symEventLog2);
//
//		sym_session symEventLog3 = new sym_session(testAuthUser,
//				getEventTypeDao().findEnabled().get(0),
//				getResponseCodeDao().findAll().get(0), 1L, null, "test event log 3");
//
//		symEventLog3.setUser(null);
//		symEventLog3.setChannel(null);
//
//		logSystemEvent(symEventLog3);
//
//		try { Thread.sleep(3000L); } catch (InterruptedException e) { e.printStackTrace(); }
//
//		assertNotNull(symEventLog1.getId());
//		assertNotNull(symEventLog1.getTimestamp());
//
//		assertNotNull(symEventLog2.getId());
//		assertNotNull(symEventLog2.getChannel());
//		assertNotNull(symEventLog2.getTimestamp());
//
//		assertNull(symEventLog2);
//		assertNotNull(symEventLog3);
//
//	}
//}
