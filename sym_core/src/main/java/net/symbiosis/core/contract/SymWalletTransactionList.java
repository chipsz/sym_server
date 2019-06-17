package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymWalletTransaction;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
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

    private ArrayList<SymWalletTransaction> walletTransactionData;
    private BigDecimal currentBalance;

    public SymWalletTransactionList() {}

    public SymWalletTransactionList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }


    public SymWalletTransactionList(SymResponseCode symResponseCode, ArrayList<SymWalletTransaction> walletTransactionData, BigDecimal currentBalance) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletTransactionData = walletTransactionData;
        this.currentBalance = currentBalance;
    }

    public ArrayList<SymWalletTransaction> getWalletTransactionData() {
        return walletTransactionData;
    }

    public void setWalletTransactionData(ArrayList<SymWalletTransaction> walletTransactionData) {
        this.walletTransactionData = walletTransactionData;
    }

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
}
