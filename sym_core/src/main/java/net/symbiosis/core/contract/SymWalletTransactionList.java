package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymWalletTransaction;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 *                                                                         *
 * Created:     16 / 05 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@XmlRootElement
public class SymWalletTransactionList extends DataContract<SymWalletTransactionList> implements Serializable {
    protected ArrayList<SymWalletTransaction> walletTransactionData;

    public SymWalletTransactionList() {}

    public SymWalletTransactionList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymWalletTransactionList(SymResponseCode symResponseCode, SymWalletTransaction wallet) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletTransactionData = new ArrayList<>();
        this.walletTransactionData.add(wallet);
    }

    public SymWalletTransactionList(SymResponseCode symResponseCode, ArrayList<SymWalletTransaction> walletTransactionData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletTransactionData = walletTransactionData;
    }

    public ArrayList<SymWalletTransaction> getWalletTransactionData() {
        return walletTransactionData;
    }

    public void setWalletTransactionData(ArrayList<SymWalletTransaction> walletTransactionData) {
        this.walletTransactionData = walletTransactionData;
    }
}
