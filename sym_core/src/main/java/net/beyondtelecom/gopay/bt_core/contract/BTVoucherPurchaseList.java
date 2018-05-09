package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTVoucherPurchase;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTVoucherPurchaseList extends DataContract<BTVoucherPurchaseList> implements Serializable {

	protected ArrayList<BTVoucherPurchase> voucherPurchaseData;

    public BTVoucherPurchaseList() {}

    public BTVoucherPurchaseList(BTResponseCode btResponseCode) {
	    this.btResponse = new BTResponse(btResponseCode);
    }

	public BTVoucherPurchaseList(BTResponseCode btResponseCode, ArrayList<BTVoucherPurchase> voucherPurchaseData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.voucherPurchaseData = voucherPurchaseData;
	}

	public BTVoucherPurchaseList(BTResponseCode btResponseCode, BTVoucherPurchase voucherProvider) {
		this.btResponse = new BTResponse(btResponseCode);
		this.voucherPurchaseData = new ArrayList<>();
		this.voucherPurchaseData.add(voucherProvider);
	}

	public ArrayList<BTVoucherPurchase> getVoucherPurchaseData() {
		return voucherPurchaseData;
	}

	public void setVoucherPurchaseData(ArrayList<BTVoucherPurchase> voucherPurchaseData) {
		this.voucherPurchaseData = voucherPurchaseData;
	}
}
