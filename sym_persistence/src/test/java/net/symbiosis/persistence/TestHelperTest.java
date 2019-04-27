package net.symbiosis.persistence;

import org.testng.annotations.Test;

import static net.symbiosis.core_lib.utilities.ReferenceGenerator.GenMills;
import static net.symbiosis.persistence.TestHelper.genEmail;
import static net.symbiosis.persistence.TestHelper.genMsisdn;
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
