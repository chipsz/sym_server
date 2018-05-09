package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWalletGroup;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTWalletGroupList extends DataContract<BTWalletGroupList> implements Serializable {

	protected ArrayList<BTWalletGroup> walletGroupData;

	public BTWalletGroupList() {}

	public BTWalletGroupList(BTResponseCode btResponseCode) {
		this.btResponse = new BTResponse(btResponseCode);
	}

	public BTWalletGroupList(BTResponseCode btResponseCode, ArrayList<BTWalletGroup> walletGroupData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.walletGroupData = walletGroupData;
	}

	public BTWalletGroupList(BTResponseCode btResponseCode, BTWalletGroup walletGroup) {
		this.btResponse = new BTResponse(btResponseCode);
		this.walletGroupData = new ArrayList<>();
		this.walletGroupData.add(walletGroup);
	}

	public ArrayList<BTWalletGroup> getVoucherGroupData() {
		return walletGroupData;
	}

	public void setVoucherGroupData(ArrayList<BTWalletGroup> walletGroupData) {
		this.walletGroupData = walletGroupData;
	}

}
