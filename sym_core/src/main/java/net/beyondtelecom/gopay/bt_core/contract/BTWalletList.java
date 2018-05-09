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
public class BTWalletList extends DataContract<BTWalletList> implements Serializable {

	protected ArrayList<BTWallet> walletData;

	public BTWalletList() {}

	public BTWalletList(BTResponseCode btResponseCode) {
		this.btResponse = new BTResponse(btResponseCode);
	}

	public BTWalletList(BTResponseCode btResponseCode, BTWallet wallet) {
		this.btResponse = new BTResponse(btResponseCode);
		this.walletData = new ArrayList<>();
		this.walletData.add(wallet);
	}

	public BTWalletList(BTResponseCode btResponseCode, ArrayList<BTWallet> walletData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.walletData = walletData;
	}

	public ArrayList<BTWallet> getWalletData() { return walletData; }

	public void setWalletData(ArrayList<BTWallet> walletData) { this.walletData = walletData; }

}
