package net.beyondtelecom.gopay.bt_common.enumeration;

import java.util.Arrays;

import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isNullOrEmpty;

/******************************************************************************
 * *
 * Created:     27 / 10 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
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


public enum HTTPCompressionType {

    GZIP            { public String value() { return "gzip";        } },
    ZLIB            { public String value() { return "zlib";        } },
    X_COMPRESS      { public String value() { return "x-compress";  } },
    X_ZIP           { public String value() { return "x-zip";       } };

    public static boolean isCompressedEncodingType(String contentStr) {
        return !isNullOrEmpty(contentStr) && Arrays.stream(HTTPCompressionType.values())
            .filter((t) ->  contentStr.toLowerCase().contains(t.name().toLowerCase()) ||
                            contentStr.toLowerCase().contains(t.value().toLowerCase()))
            .count() > 0;
    }

    public abstract String value();
}
