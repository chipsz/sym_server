package net.beyondtelecom.gopay.bt_common.test;

import org.testng.annotations.Test;

import static net.beyondtelecom.gopay.bt_common.enumeration.HTTPCompressionType.isCompressedEncodingType;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/******************************************************************************
 * *
 * Created:     28 / 10 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * Copyright:   beyondtelecom Entertainment                                     *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
 * *
 * This program is free software; you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License as published by    *
 * the Free Software Foundation; either version 2 of the License, or       *
 * (at your option) any later version.                                     *
 * *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the         *
 * GNU General Public License for more details.                            *
 * *
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
