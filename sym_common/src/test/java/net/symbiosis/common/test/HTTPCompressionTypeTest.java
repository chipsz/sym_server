package net.symbiosis.common.test;

import org.testng.annotations.Test;

import static net.symbiosis.common.enumeration.HTTPCompressionType.isCompressedEncodingType;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/******************************************************************************
 * *
 * Created:     28 / 10 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 ******************************************************************************/

@Test
public class HTTPCompressionTypeTest {

    @Test
    public void testIsCompressedEncodingType() {
        assertTrue(isCompressedEncodingType("gzip"));
        assertTrue(isCompressedEncodingType("zlib"));
        assertTrue(isCompressedEncodingType("x-compress"));
        assertTrue(isCompressedEncodingType("x-zip"));
        assertTrue(isCompressedEncodingType("x-zip, test"));
        assertTrue(isCompressedEncodingType("none, test, x-zip"));
        assertFalse(isCompressedEncodingType("none"));
        assertFalse(isCompressedEncodingType(null));
    }

}
