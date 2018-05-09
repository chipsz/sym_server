package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTServiceProvider;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTServiceProviderList extends DataContract<BTServiceProviderList> implements Serializable {

	protected ArrayList<BTServiceProvider> serviceProviderData;

    public BTServiceProviderList() {}

    public BTServiceProviderList(BTResponseCode btResponseCode) {
	    this.btResponse = new BTResponse(btResponseCode);
    }

	public BTServiceProviderList(BTResponseCode btResponseCode, ArrayList<BTServiceProvider> serviceProviderData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.serviceProviderData = serviceProviderData;
	}

	public BTServiceProviderList(BTResponseCode btResponseCode, BTServiceProvider serviceProvider) {
		this.btResponse = new BTResponse(btResponseCode);
		this.serviceProviderData = new ArrayList<>();
		this.serviceProviderData.add(serviceProvider);
	}

	public ArrayList<BTServiceProvider> getServiceProviderData() {
		return serviceProviderData;
	}

	public void setServiceProviderData(ArrayList<BTServiceProvider> serviceProviderData) {
		this.serviceProviderData = serviceProviderData;
	}
}
