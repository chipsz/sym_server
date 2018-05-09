package net.symbiosis.common.test;

import net.symbiosis.common.configuration.Configuration;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class ConfigurationTests {

    @Test
    public void testGetNewLogger() {
        //check that logger is named correctly
        assertEquals(Configuration.getNewLogger(this.getClass().getSimpleName()).getName(), "ConfigurationTests");
    }

    @Test
    public void testGetSymbiosisProperty() {
        //test property reading
        assertEquals(Configuration.getSymbiosisProperty("testMaster"), "Tsungai");
    }

    @Test
    public void testGetProperty() {
        //test property reading
        assertEquals(Configuration.getProperty("test", "testConfig"), "TestConfig");
        assertEquals(Configuration.getProperty("test", "invalidConfig", "defaultValue"), "defaultValue");
    }

//    @Test
//    public void testGetCurrencySymbol() {
//        assertEquals(Configuration.getCurrencySymbol(), "Gh¢");
//    }

    @Test
    public void testGetCountryCodePrefix() {
        assertEquals(Configuration.getCountryCodePrefix(), "263");
    }

    @Test
    public void testGetConfigurations() {
        String[] multiConfigs = Configuration.getProperties("test", "testMultiConfig");
        assertEquals(multiConfigs.length, 3);
        assertEquals(multiConfigs[0], "Config1");
        assertEquals(multiConfigs[1], "Config2");
        assertEquals(multiConfigs[2], "Config3");
    }
}
