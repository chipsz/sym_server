package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;

/***************************************************************************
 * Created:     16 / 12 / 2016                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class BTMap extends DataContract<BTMap> implements Serializable {

	protected Map responseData;

    public BTMap() {}

    public BTMap(BTResponseCode btResponseCode) {
	    this.btResponse = new BTResponse(btResponseCode);
    }

	public BTMap(BTResponseCode btResponseCode, Map responseData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.responseData = responseData;
	}

	public Map getResponseData() {
		return responseData;
	}

	public void setResponseData(Map responseData) {
		this.responseData = responseData;
	}
}
