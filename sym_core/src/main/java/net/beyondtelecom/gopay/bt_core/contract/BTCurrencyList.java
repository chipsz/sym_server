package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCurrency;

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
public class BTCurrencyList extends DataContract<BTCurrencyList> implements Serializable {

	protected ArrayList<BTCurrency> currencyData;

	public BTCurrencyList() {}

	public BTCurrencyList(BTResponseCode btResponseCode) { super(btResponseCode); }

	public BTCurrencyList(BTResponseCode btResponseCode, ArrayList<BTCurrency> currencyData) {
		super(btResponseCode);
		this.currencyData = currencyData;
	}

	public BTCurrencyList(BTResponseCode btResponseCode, BTCurrency currency) {
		super(btResponseCode);
		this.currencyData = new ArrayList<>();
		this.currencyData.add(currency);
	}

	public ArrayList<BTCurrency> getCurrencyData() { return currencyData; }

	public void setCurrencyData(ArrayList<BTCurrency> currencyData) {
	    this.currencyData = currencyData;
	}

}
