package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCashoutAccount;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     22 / 01 / 2017                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class BTCashoutAccountList extends DataContract<BTCashoutAccountList> implements Serializable {

	protected ArrayList<BTCashoutAccount> cashoutAccountData;

	public BTCashoutAccountList() {}

	public BTCashoutAccountList(BTResponseCode btResponseCode) { super(btResponseCode); }

	public BTCashoutAccountList(BTResponseCode btResponseCode, ArrayList<BTCashoutAccount> cashoutAccountData) {
		super(btResponseCode);
		this.cashoutAccountData = cashoutAccountData;
	}

	public BTCashoutAccountList(BTResponseCode btResponseCode, BTCashoutAccount financialInstitution) {
		super(btResponseCode);
		this.cashoutAccountData = new ArrayList<>();
		this.cashoutAccountData.add(financialInstitution);
	}

	public ArrayList<BTCashoutAccount> getCashoutAccountData() { return cashoutAccountData; }

	public void setCashoutAccountData(ArrayList<BTCashoutAccount> cashoutAccountData) {
	    this.cashoutAccountData = cashoutAccountData;
	}

}
