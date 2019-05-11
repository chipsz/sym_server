package net.symbiosis.core_lib.utilities;

import net.symbiosis.core_lib.response.SymResponseObject;
import org.testng.annotations.Test;

import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.core_lib.utilities.CommonUtilities.*;
import static net.symbiosis.core_lib.utilities.IOUtils.getOSDefaultTempDir;
import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/20/15
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class CommonUtilitiesTest {

    @Test
    public void testJoin() {
        System.out.println("RUNNING TEST: join");
        assertEquals("hello123", join("hello", 123));
        assertEquals("helloDATA", join("hello", "", "DATA"));
        assertEquals("Test123Works", join(null, "Test", null, 123, "Works"));
        assertEquals("12102015", join(null, 12, 10, null, 2015));
        assertEquals("12/10/2015", join(null, 12, "/", 10, "/", null, 2015));
        assertNull(join(null, null, null));
    }

    @Test
    public void testJoinWithDelimiter() {
        System.out.println("RUNNING TEST: joinWithDelimiter");
        assertEquals("hello123", joinWithDelimiter(null, "hello", 123));
        assertEquals("hello-123", joinWithDelimiter('-', "hello", 123));
        assertEquals("hello/123", joinWithDelimiter("/", "hello", 123));
        assertEquals("helloDATA", joinWithDelimiter(null, "hello", "", "DATA"));
        assertEquals("Test-123-Works", joinWithDelimiter('-', null, "Test", null, 123, "Works"));
        assertEquals("12/10/2015", joinWithDelimiter("/", null, 12, 10, null, 2015));
        assertNull(joinWithDelimiter(null, null, null));
    }

    @Test
    public void testCapitalizeFirst() {
        System.out.println("RUNNING TEST: capitalizeFirst");
        assertTrue(capitalizeFirst("hello").equals("Hello"));
        assertTrue(capitalizeFirst("1").equals("1"));
        assertTrue(capitalizeFirst("TestString").equals("TestString"));
        assertTrue(capitalizeFirst("").equals(""));
        assertTrue(capitalizeFirst("hello1").equals("Hello1"));
        assertTrue(capitalizeFirst("1hello").equals("1hello"));
        assertNull(capitalizeFirst(null));
    }

    @Test
    public void testDecapitalizeFirst() {
        System.out.println("RUNNING TEST: decapitalizeFirst");
        assertTrue(decapitalizeFirst("Hello").equals("hello"));
        assertTrue(decapitalizeFirst("1").equals("1"));
        assertTrue(decapitalizeFirst("TestString").equals("testString"));
        assertTrue(decapitalizeFirst("").equals(""));
        assertTrue(decapitalizeFirst("Hello1").equals("hello1"));
        assertTrue(decapitalizeFirst("1hello").equals("1hello"));
        assertNull(decapitalizeFirst(null));
    }

    @Test
    public void testCapitalizeAll() {
        System.out.println("RUNNING TEST: capitalizeAll");
        assertTrue(capitalizeAll("Hello").equals("HELLO"));
        assertTrue(capitalizeAll("1").equals("1"));
        assertTrue(capitalizeAll("Test String").equals("TEST STRING"));
        assertTrue(capitalizeAll("").equals(""));
        assertTrue(capitalizeAll("Hello1").equals("HELLO1"));
        assertTrue(capitalizeAll("1hello =").equals("1HELLO ="));
        assertNull(capitalizeAll(null));
    }

    @Test
    public void testDecapitalizeAll() {
        System.out.println("RUNNING TEST: decapitalizeAll");
        assertTrue(decapitalizeAll("Hello").equals("hello"));
        assertTrue(decapitalizeAll("1").equals("1"));
        assertTrue(decapitalizeAll("Test String").equals("test string"));
        assertTrue(decapitalizeAll("").equals(""));
        assertTrue(decapitalizeAll("Hello1").equals("hello1"));
        assertTrue(decapitalizeAll("1heLLO =").equals("1hello ="));
        assertNull(decapitalizeAll(null));
    }

    @Test
    public void testGetTempFolderLocation() {
        System.out.println("RUNNING TEST: getTempFolderLocation");
        assertNotNull(getOSDefaultTempDir());
    }

    @Test
    public void testToCamelCase() {
        System.out.println("RUNNING TEST: toCamelCase");
        assertNull(toCamelCase(null));
        assertEquals(toCamelCase(""), "");
        assertEquals(toCamelCase("12"), "12");
        assertEquals(toCamelCase("1 t"), "1 T");
        assertEquals(toCamelCase("t 1"), "T 1");
        assertEquals(toCamelCase("T 1"), "T 1");
        assertEquals(toCamelCase("teSt DAta"), "Test Data");
        assertEquals(toCamelCase("MORE TEST DATA"), "More Test Data");
        assertEquals(toCamelCase("more test data"), "More Test Data");
    }

    @Test
    public void testAlignStringToLength() {
        System.out.println("RUNNING TEST: alignStringToLength");
        assertEquals(alignStringToLength("test", 7), "test   ");
        assertEquals(alignStringToLength(null, 7), "       ");
        assertEquals(alignStringToLength("", 7), "       ");
        assertEquals(alignStringToLength(" test ", 7), " test  ");
    }

    @Test
    public void testFormatDoubleToMoney() {
        System.out.println("RUNNING TEST: formatDoubleToMoney");
        assertEquals(formatDoubleToMoney(0, "$"), "$0.00");
        assertEquals(formatDoubleToMoney(100, "R"), "R100.00");
        assertEquals(formatDoubleToMoney(1000, "R"), "R1,000.00");
        assertEquals(formatDoubleToMoney(1000.55, "R"), "R1,000.55");
        assertEquals(formatDoubleToMoney(1000.554, "R"), "R1,000.55");
        assertEquals(formatDoubleToMoney(1000.556, "R"), "R1,000.56");
        assertEquals(formatDoubleToMoney(-1000.556, "R"), "-R1,000.56");
        assertEquals(formatDoubleToMoney(123456789.456, "R"), "R123,456,789.46");
        assertEquals(formatDoubleToMoney(123456789.456, "$"), "$123,456,789.46");
        assertEquals(formatDoubleToMoney(-123456789.456, "ZAR"), "-ZAR123,456,789.46");
    }

    @Test
    public void testFormat10DigitPhoneNumber() {
        System.out.println("RUNNING TEST: format10DigitPhoneNumber");
        assertEquals(format10DigitPhoneNumber("+263781234567", null), "+263781234567");
        assertEquals(format10DigitPhoneNumber("0123456789", null), "0123456789");
        assertEquals(format10DigitPhoneNumber("junkMsisdn", "263"), "junkMsisdn");
        assertEquals(format10DigitPhoneNumber("00263111111111", "263"), "0111111111");
        assertEquals(format10DigitPhoneNumber("+27121212121", "27"), "0121212121");
        assertEquals(format10DigitPhoneNumber("+11123456789", "263"), "+11123456789");
        assertEquals(format10DigitPhoneNumber("00263123456789", "263"), "0123456789");
        assertEquals(format10DigitPhoneNumber("00263123456789", "61"), "00263123456789");
    }

    @Test
    public void testFormatFullMsisdn() {
        System.out.println("RUNNING TEST: formatFullMsisdn");
        assertNull(formatFullMsisdn(null, "263"));
        assertEquals(formatFullMsisdn("0123456789", null), "0123456789");
        assertEquals(formatFullMsisdn("junkMsisdn", "263"), "junkMsisdn");
        assertEquals(formatFullMsisdn("00263111111111", "263"), "263111111111");
        assertEquals(formatFullMsisdn("+27121212121", "27"), "27121212121");
        assertEquals(formatFullMsisdn("+11123456789", "263"), "11123456789");
        assertEquals(formatFullMsisdn("0123456789", "263"), "263123456789");
    }

    @Test
    public void testRound() {
        System.out.println("RUNNING TEST: round");
        assertEquals(round(0), 0);
        assertEquals(round(0.123), 0);
        assertEquals(round(0.4), 0);
        assertEquals(round(-0.4), 0);
        assertEquals(round(-0.4), 0);
        assertEquals(round(1), 1);
        assertEquals(round(1.4), 1);
        assertEquals(round(1.6), 2);
        assertEquals(round(1.666), 2);
        assertEquals(round(-50.4), -50);
        assertEquals(round(-50.6), -51);
    }

    @Test
    public void testGetValueByStringPattern() {
        System.out.println("RUNNING TEST: getValueByStringPattern");
        String inputString = "77384296047778,100017220219,100,1,20171231,Symbiosiss,,,";
        String inputPattern = "(\\d+),(\\d+),(\\d+),(.*)";
        SymResponseObject<String> response = getValueByStringPattern(inputString, inputPattern, 3);
        assertTrue(response.getResponseCode().equals(SUCCESS));
        assertEquals(response.getResponseObject(), "100");
        response = getValueByStringPattern(inputString, inputPattern, 1);
        assertEquals(response.getResponseObject(), "77384296047778");
    }
}
