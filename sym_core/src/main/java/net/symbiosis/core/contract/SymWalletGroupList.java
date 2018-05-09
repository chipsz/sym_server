package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymWalletGroup;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class SymWalletGroupList extends DataContract<SymWalletGroupList> implements Serializable {

    protected ArrayList<SymWalletGroup> walletGroupData;

    public SymWalletGroupList() {
    }

    public SymWalletGroupList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymWalletGroupList(SymResponseCode symResponseCode, ArrayList<SymWalletGroup> walletGroupData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletGroupData = walletGroupData;
    }

    public SymWalletGroupList(SymResponseCode symResponseCode, SymWalletGroup walletGroup) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletGroupData = new ArrayList<>();
        this.walletGroupData.add(walletGroup);
    }

    public ArrayList<SymWalletGroup> getVoucherGroupData() {
        return walletGroupData;
    }

    public void setVoucherGroupData(ArrayList<SymWalletGroup> walletGroupData) {
        this.walletGroupData = walletGroupData;
    }

}
