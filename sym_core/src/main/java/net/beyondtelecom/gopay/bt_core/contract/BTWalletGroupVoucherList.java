package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWalletGroupVoucher;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTWalletGroupVoucherList extends DataContract<BTWalletGroupVoucherList> implements Serializable {

	protected ArrayList<BTWalletGroupVoucher> walletGroupVoucherData;

	public BTWalletGroupVoucherList() {}

	public BTWalletGroupVoucherList(BTResponseCode btResponseCode) {
		this.btResponse = new BTResponse(btResponseCode);
	}

	public BTWalletGroupVoucherList(BTResponseCode btResponseCode, ArrayList<BTWalletGroupVoucher> walletGroupVoucherData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.walletGroupVoucherData = walletGroupVoucherData;
	}

	public BTWalletGroupVoucherList(BTResponseCode btResponseCode, BTWalletGroupVoucher walletGroupVoucher) {
		this.btResponse = new BTResponse(btResponseCode);
		this.walletGroupVoucherData = new ArrayList<>();
		this.walletGroupVoucherData.add(walletGroupVoucher);
	}

	public ArrayList<BTWalletGroupVoucher> getWalletGroupVoucherData() {
		return walletGroupVoucherData;
	}

	public void setWalletGroupVoucherData(ArrayList<BTWalletGroupVoucher> walletGroupVoucherData) {
		this.walletGroupVoucherData = walletGroupVoucherData;
	}

}
