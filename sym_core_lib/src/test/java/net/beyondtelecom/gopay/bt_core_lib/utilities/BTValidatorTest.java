package net.beyondtelecom.bt_core_lib.utilities;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/20/15
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class BTValidatorTest {

    @Test
    public void testIsNullOrEmptyTest()
    {
        System.out.println("RUNNING TEST: CommonUtilities.isNullOrEmpty");
        Assert.assertTrue(BTValidator.isNullOrEmpty(null));
        Assert.assertTrue(BTValidator.isNullOrEmpty(""));
        Assert.assertFalse(BTValidator.isNullOrEmpty("0394580"));
        Assert.assertFalse(BTValidator.isNullOrEmpty("test string"));
        Assert.assertFalse(BTValidator.isNullOrEmpty(String.valueOf(false)));
    }

    @Test
    public void testIsNumeric()
    {
        System.out.println("RUNNING TEST: CommonUtilities.IsNumeric");
        Assert.assertFalse(BTValidator.isNumeric(null));
        Assert.assertFalse(BTValidator.isNumeric("test string"));
        Assert.assertTrue(BTValidator.isNumeric("0"));
        Assert.assertTrue(BTValidator.isNumeric("12.0"));
        Assert.assertTrue(BTValidator.isNumeric("6663.11"));
        Assert.assertTrue(BTValidator.isNumeric("-6663.11"));
        Assert.assertTrue(BTValidator.isNumeric(-99999999999999999999999999999999.99999999999999999999999999999999));
        Assert.assertTrue(BTValidator.isNumeric("-99999999999999999999999999999999.99999999999999999999999999999999"));
        Assert.assertTrue(BTValidator.isNumeric("-0"));
        Assert.assertTrue(BTValidator.isNumeric(new BigDecimal(0)));
        Assert.assertTrue(BTValidator.isNumeric(new BigDecimal(0.4)));
        Assert.assertTrue(BTValidator.isNumeric((float) 0.4));
        Assert.assertTrue(BTValidator.isNumeric(1L));
        Assert.assertFalse(BTValidator.isNumeric(new Object()));
        Assert.assertFalse(BTValidator.isNumeric(""));
    }

    @Test
    public void testIsValidEmailTest()
    {
        System.out.println("RUNNING TEST: CommonUtilities.isValidEmail");
        Assert.assertTrue(BTValidator.isValidEmail("t@t.te"));
        Assert.assertTrue(BTValidator.isValidEmail("t@d12.te"));
        Assert.assertTrue(BTValidator.isValidEmail("1t@d12.te"));
        Assert.assertTrue(BTValidator.isValidEmail("test@test.com"));
        Assert.assertTrue(BTValidator.isValidEmail("test@test.co.za"));
        Assert.assertTrue(BTValidator.isValidEmail("test.123-yer@test.co.za"));
        Assert.assertTrue(BTValidator.isValidEmail("TEST.123-yer@test.CO.ZA"));
        Assert.assertTrue(BTValidator.isValidEmail("test123@test.co.za"));
        Assert.assertFalse(BTValidator.isValidEmail("t@t.t"));
        Assert.assertFalse(BTValidator.isValidEmail("test@123@test.co.za"));
        Assert.assertFalse(BTValidator.isValidEmail("test#123@test.co.za"));
        Assert.assertFalse(BTValidator.isValidEmail("test*123@test.co.za"));
        Assert.assertFalse(BTValidator.isValidEmail(null));
        Assert.assertFalse(BTValidator.isValidEmail(""));
        Assert.assertFalse(BTValidator.isValidEmail("t@t"));
    }

    @Test
    public void testIsValidPassword()
    {
        System.out.println("RUNNING TEST: CommonUtilities.isValidPassword");
        Assert.assertFalse(BTValidator.isValidPassword("12345"));
        Assert.assertTrue(BTValidator.isValidPassword("l2n5k4jl56n2(*&^*(^"));
        Assert.assertFalse(BTValidator.isValidPassword("12345abcde12345abcde12345abcde12345abcde12345abcde1"));
        Assert.assertFalse(BTValidator.isValidPassword(null));
    }

    @Test
    public void testIsValidMsisdn() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidTenDigitMsisdn");

        Assert.assertTrue(BTValidator.isValidTenDigitMsisdn("0123456789"));
        Assert.assertTrue(BTValidator.isValidMsisdn("27123456789", "27"));
        Assert.assertTrue(BTValidator.isValidMsisdn("00263123456789", "263"));
        Assert.assertTrue(BTValidator.isValidMsisdn("263123456789", "263"));
        Assert.assertTrue(BTValidator.isValidMsisdn("+27123456789", "27"));

        Assert.assertFalse(BTValidator.isValidTenDigitMsisdn("1234567890"));
        Assert.assertFalse(BTValidator.isValidTenDigitMsisdn("12345"));
        Assert.assertFalse(BTValidator.isValidTenDigitMsisdn("abcdefg"));
        Assert.assertFalse(BTValidator.isValidMsisdn("00263123456789", "27"));
        Assert.assertFalse(BTValidator.isValidMsisdn("263123456789", "264"));
        Assert.assertFalse(BTValidator.isValidMsisdn("+27123456789", "28"));
        Assert.assertFalse(BTValidator.isValidMsisdn("28123456789", "27"));
        Assert.assertFalse(BTValidator.isValidMsisdn(null, "27"));
    }

    @Test
    public void testIsValidName() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidFirstName");
        Assert.assertFalse(BTValidator.isValidName("a"));
        Assert.assertTrue(BTValidator.isValidName("Jason"));
        Assert.assertFalse(BTValidator.isValidName("Jason1"));
        Assert.assertFalse(BTValidator.isValidName("12345abcde12345abcde12345abcde12345abcde12345abcde1"));
        Assert.assertFalse(BTValidator.isValidName("1234567890"));
        Assert.assertFalse(BTValidator.isValidName(null));
    }

    @Test
    public void testIsValidUsername() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidUsername");
        Assert.assertFalse(BTValidator.isValidUsername("a"));
        Assert.assertTrue(BTValidator.isValidUsername("j_boss"));
        Assert.assertFalse(BTValidator.isValidUsername("j boss"));
        Assert.assertTrue(BTValidator.isValidUsername("jBoss1"));
        Assert.assertFalse(BTValidator.isValidUsername("12345abcde12345abcde12345abcde12345abcde12345abcde1"));
        Assert.assertTrue(BTValidator.isValidUsername("abcdefghij"));
        Assert.assertTrue(BTValidator.isValidUsername("test_1"));
        Assert.assertFalse(BTValidator.isValidUsername("test_@"));
        Assert.assertTrue(BTValidator.isValidUsername("_josh.boss"));
        Assert.assertTrue(BTValidator.isValidUsername("j-boss"));
        Assert.assertFalse(BTValidator.isValidUsername(null));
    }
}
