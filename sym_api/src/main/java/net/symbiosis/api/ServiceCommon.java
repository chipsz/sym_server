package net.symbiosis.api;

import static net.symbiosis.core_lib.utilities.CommonUtilities.isNullOrEmpty;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public class ServiceCommon {
    public static <T> T getRealParamValue(T param) {
        return isNullOrEmpty(String.valueOf(param)) ? null : param;
    }
}
