package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymWallet;
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
public class SymWalletList extends DataContract<SymWalletList> implements Serializable {

    protected ArrayList<SymWallet> walletData;

    public SymWalletList() {
    }

    public SymWalletList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymWalletList(SymResponseCode symResponseCode, SymWallet wallet) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletData = new ArrayList<>();
        this.walletData.add(wallet);
    }

    public SymWalletList(SymResponseCode symResponseCode, ArrayList<SymWallet> walletData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletData = walletData;
    }

    public ArrayList<SymWallet> getWalletData() {
        return walletData;
    }

    public void setWalletData(ArrayList<SymWallet> walletData) {
        this.walletData = walletData;
    }

}
