package net.beyondtelecom.gopay.bt_persistence;

import org.testng.annotations.Test;

import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.GenMills;
import static net.beyondtelecom.gopay.bt_persistence.TestHelper.genEmail;
import static net.beyondtelecom.gopay.bt_persistence.TestHelper.genMsisdn;
import static org.testng.Assert.assertNotNull;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Test
public class TestHelperTest {

	@Test
	public void testGenEmail() {
		assertNotNull(genEmail());
	}

	@Test
	public void testGenMsisdn() {
		for (int c = 0; c < 20; c++) {
			String temp = "" + GenMills();
			System.out.println();
		}
		assertNotNull(genMsisdn());
	}

	@Test
	public void testGenUsername() {
		assertNotNull(genEmail());
	}
}
