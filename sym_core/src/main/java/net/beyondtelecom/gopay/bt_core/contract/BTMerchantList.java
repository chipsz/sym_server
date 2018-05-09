package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWallet;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTMerchantList extends DataContract<BTMerchantList> implements Serializable {

	protected ArrayList<BTWallet> merchantData;

	public BTMerchantList() {}

	public BTMerchantList(BTResponseCode btResponseCode) {
		this.btResponse = new BTResponse(btResponseCode);
	}

	public BTMerchantList(BTResponseCode btResponseCode, BTWallet merchant) {
		this.btResponse = new BTResponse(btResponseCode);
		this.merchantData = new ArrayList<>();
		this.merchantData.add(merchant);
	}

	public BTMerchantList(BTResponseCode btResponseCode, ArrayList<BTWallet> merchantData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.merchantData = merchantData;
	}

	public ArrayList<BTWallet> getMerchantData() { return merchantData; }

	public void setMerchantData(ArrayList<BTWallet> merchantData) { this.merchantData = merchantData; }

}
