package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymCashoutAccount;
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
public class SymCashoutAccountList extends DataContract<SymCashoutAccountList> implements Serializable {

    protected ArrayList<SymCashoutAccount> cashoutAccountData;

    public SymCashoutAccountList() {
    }

    public SymCashoutAccountList(SymResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public SymCashoutAccountList(SymResponseCode symResponseCode, ArrayList<SymCashoutAccount> cashoutAccountData) {
        super(symResponseCode);
        this.cashoutAccountData = cashoutAccountData;
    }

    public SymCashoutAccountList(SymResponseCode symResponseCode, SymCashoutAccount financialInstitution) {
        super(symResponseCode);
        this.cashoutAccountData = new ArrayList<>();
        this.cashoutAccountData.add(financialInstitution);
    }

    public ArrayList<SymCashoutAccount> getCashoutAccountData() {
        return cashoutAccountData;
    }

    public void setCashoutAccountData(ArrayList<SymCashoutAccount> cashoutAccountData) {
        this.cashoutAccountData = cashoutAccountData;
    }

}
