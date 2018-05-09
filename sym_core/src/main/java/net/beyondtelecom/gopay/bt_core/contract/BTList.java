package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTList extends DataContract<BTList> implements Serializable {

	protected ArrayList responseData;

    public BTList() {}

    public BTList(BTResponseCode btResponseCode) {
	    this.btResponse = new BTResponse(btResponseCode);
    }

	public BTList(BTResponseCode btResponseCode, ArrayList responseData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.responseData = responseData;
	}

	public ArrayList getResponseData() {
		return responseData;
	}

	public void setResponseData(ArrayList responseData) {
		this.responseData = responseData;
	}
}
