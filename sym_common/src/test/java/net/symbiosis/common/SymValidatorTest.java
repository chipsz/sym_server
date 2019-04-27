package net.symbiosis.common;

import net.symbiosis.common.utilities.SymValidator;
import net.symbiosis.core_lib.utilities.CommonUtilities;
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
public class SymValidatorTest {

    @Test
    public void testIsNullOrEmptyTest() {
        System.out.println("RUNNING TEST: CommonUtilities.isNullOrEmpty");
        Assert.assertTrue(CommonUtilities.isNullOrEmpty(null));
        Assert.assertTrue(CommonUtilities.isNullOrEmpty(""));
        Assert.assertFalse(CommonUtilities.isNullOrEmpty("0394580"));
        Assert.assertFalse(CommonUtilities.isNullOrEmpty("test string"));
        Assert.assertFalse(CommonUtilities.isNullOrEmpty(String.valueOf(false)));
    }

    @Test
    public void testIsNumeric() {
        System.out.println("RUNNING TEST: CommonUtilities.IsNumeric");
        Assert.assertFalse(SymValidator.isNumeric(null));
        Assert.assertFalse(SymValidator.isNumeric("test string"));
        Assert.assertTrue(SymValidator.isNumeric("0"));
        Assert.assertTrue(SymValidator.isNumeric("12.0"));
        Assert.assertTrue(SymValidator.isNumeric("6663.11"));
        Assert.assertTrue(SymValidator.isNumeric("-6663.11"));
        Assert.assertTrue(SymValidator.isNumeric(-99999999999999999999999999999999.99999999999999999999999999999999));
        Assert.assertTrue(SymValidator.isNumeric("-99999999999999999999999999999999.99999999999999999999999999999999"));
        Assert.assertTrue(SymValidator.isNumeric("-0"));
        Assert.assertTrue(SymValidator.isNumeric(new BigDecimal(0)));
        Assert.assertTrue(SymValidator.isNumeric(new BigDecimal(0.4)));
        Assert.assertTrue(SymValidator.isNumeric((float) 0.4));
        Assert.assertTrue(SymValidator.isNumeric(1L));
        Assert.assertFalse(SymValidator.isNumeric(new Object()));
        Assert.assertFalse(SymValidator.isNumeric(""));
    }

    @Test
    public void testIsValidEmailTest() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidEmail");
        Assert.assertTrue(SymValidator.isValidEmail("t@t.te"));
        Assert.assertTrue(SymValidator.isValidEmail("t@d12.te"));
        Assert.assertTrue(SymValidator.isValidEmail("1t@d12.te"));
        Assert.assertTrue(SymValidator.isValidEmail("test@test.com"));
        Assert.assertTrue(SymValidator.isValidEmail("test@test.co.za"));
        Assert.assertTrue(SymValidator.isValidEmail("test.123-yer@test.co.za"));
        Assert.assertTrue(SymValidator.isValidEmail("TEST.123-yer@test.CO.ZA"));
        Assert.assertTrue(SymValidator.isValidEmail("test123@test.co.za"));
        Assert.assertFalse(SymValidator.isValidEmail("t@t.t"));
        Assert.assertFalse(SymValidator.isValidEmail("test@123@test.co.za"));
        Assert.assertFalse(SymValidator.isValidEmail("test#123@test.co.za"));
        Assert.assertFalse(SymValidator.isValidEmail("test*123@test.co.za"));
        Assert.assertFalse(SymValidator.isValidEmail(null));
        Assert.assertFalse(SymValidator.isValidEmail(""));
        Assert.assertFalse(SymValidator.isValidEmail("t@t"));
    }

    @Test
    public void testIsValidPassword() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidPassword");
        Assert.assertFalse(SymValidator.isValidPassword("12345"));
        Assert.assertTrue(SymValidator.isValidPassword("l2n5k4jl56n2(*&^*(^"));
        Assert.assertFalse(SymValidator.isValidPassword("12345abcde12345abcde12345abcde12345abcde12345abcde1"));
        Assert.assertFalse(SymValidator.isValidPassword(null));
    }

    @Test
    public void testIsValidMsisdn() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidTenDigitMsisdn");

        Assert.assertTrue(SymValidator.isValidTenDigitMsisdn("0123456789"));
        Assert.assertTrue(SymValidator.isValidMsisdn("27123456789", "27"));
        Assert.assertTrue(SymValidator.isValidMsisdn("00263123456789", "263"));
        Assert.assertTrue(SymValidator.isValidMsisdn("263123456789", "263"));
        Assert.assertTrue(SymValidator.isValidMsisdn("+27123456789", "27"));

        Assert.assertFalse(SymValidator.isValidTenDigitMsisdn("1234567890"));
        Assert.assertFalse(SymValidator.isValidTenDigitMsisdn("12345"));
        Assert.assertFalse(SymValidator.isValidTenDigitMsisdn("abcdefg"));
        Assert.assertFalse(SymValidator.isValidMsisdn("00263123456789", "27"));
        Assert.assertFalse(SymValidator.isValidMsisdn("263123456789", "264"));
        Assert.assertFalse(SymValidator.isValidMsisdn("+27123456789", "28"));
        Assert.assertFalse(SymValidator.isValidMsisdn("28123456789", "27"));
        Assert.assertFalse(SymValidator.isValidMsisdn(null, "27"));
    }

    @Test
    public void testIsValidName() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidFirstName");
        Assert.assertFalse(SymValidator.isValidName("a"));
        Assert.assertTrue(SymValidator.isValidName("Jason"));
        Assert.assertFalse(SymValidator.isValidName("Jason1"));
        Assert.assertFalse(SymValidator.isValidName("12345abcde12345abcde12345abcde12345abcde12345abcde1"));
        Assert.assertFalse(SymValidator.isValidName("1234567890"));
        Assert.assertFalse(SymValidator.isValidName(null));
    }

    @Test
    public void testIsValidUsername() {
        System.out.println("RUNNING TEST: CommonUtilities.isValidUsername");
        Assert.assertFalse(SymValidator.isValidUsername("a"));
        Assert.assertTrue(SymValidator.isValidUsername("j_boss"));
        Assert.assertFalse(SymValidator.isValidUsername("j boss"));
        Assert.assertTrue(SymValidator.isValidUsername("jBoss1"));
        Assert.assertFalse(SymValidator.isValidUsername("12345abcde12345abcde12345abcde12345abcde12345abcde1"));
        Assert.assertTrue(SymValidator.isValidUsername("abcdefghij"));
        Assert.assertTrue(SymValidator.isValidUsername("test_1"));
        Assert.assertFalse(SymValidator.isValidUsername("test_@"));
        Assert.assertTrue(SymValidator.isValidUsername("_josh.boss"));
        Assert.assertTrue(SymValidator.isValidUsername("j-boss"));
        Assert.assertFalse(SymValidator.isValidUsername(null));
    }
}
