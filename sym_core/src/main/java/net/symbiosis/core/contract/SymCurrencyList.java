package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymCurrency;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

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
public class SymCurrencyList extends DataContract<SymCurrencyList> implements Serializable {

    protected ArrayList<SymCurrency> currencyData;

    public SymCurrencyList() {
    }

    public SymCurrencyList(SymResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public SymCurrencyList(SymResponseCode symResponseCode, ArrayList<SymCurrency> currencyData) {
        super(symResponseCode);
        this.currencyData = currencyData;
    }

    public SymCurrencyList(SymResponseCode symResponseCode, SymCurrency currency) {
        super(symResponseCode);
        this.currencyData = new ArrayList<>();
        this.currencyData.add(currency);
    }

    public ArrayList<SymCurrency> getCurrencyData() {
        return currencyData;
    }

    public void setCurrencyData(ArrayList<SymCurrency> currencyData) {
        this.currencyData = currencyData;
    }

}
