package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTVoucherProvider;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTVoucherProviderList extends DataContract<BTVoucherProviderList> implements Serializable {

	protected ArrayList<BTVoucherProvider> voucherProviderData;

    public BTVoucherProviderList() {}

    public BTVoucherProviderList(BTResponseCode btResponseCode) {
	    this.btResponse = new BTResponse(btResponseCode);
    }

	public BTVoucherProviderList(BTResponseCode btResponseCode, ArrayList<BTVoucherProvider> voucherProviderData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.voucherProviderData = voucherProviderData;
	}

	public BTVoucherProviderList(BTResponseCode btResponseCode, BTVoucherProvider voucherProvider) {
		this.btResponse = new BTResponse(btResponseCode);
		this.voucherProviderData = new ArrayList<>();
		this.voucherProviderData.add(voucherProvider);
	}

	public ArrayList<BTVoucherProvider> getVoucherProviderData() {
		return voucherProviderData;
	}

	public void setVoucherProviderData(ArrayList<BTVoucherProvider> voucherProviderData) {
		this.voucherProviderData = voucherProviderData;
	}
}
