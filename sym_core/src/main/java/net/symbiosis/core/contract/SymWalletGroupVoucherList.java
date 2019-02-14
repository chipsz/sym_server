package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymWalletGroupVoucher;
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
public class SymWalletGroupVoucherList extends DataContract<SymWalletGroupVoucherList> implements Serializable {

    protected ArrayList<SymWalletGroupVoucher> walletGroupVoucherData;

    public SymWalletGroupVoucherList() {
    }

    public SymWalletGroupVoucherList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymWalletGroupVoucherList(SymResponseCode symResponseCode, ArrayList<SymWalletGroupVoucher> walletGroupVoucherData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletGroupVoucherData = walletGroupVoucherData;
    }

    public SymWalletGroupVoucherList(SymResponseCode symResponseCode, SymWalletGroupVoucher walletGroupVoucher) {
        this.symResponse = new SymResponse(symResponseCode);
        this.walletGroupVoucherData = new ArrayList<>();
        this.walletGroupVoucherData.add(walletGroupVoucher);
    }

    public ArrayList<SymWalletGroupVoucher> getWalletGroupVoucherData() {
        return walletGroupVoucherData;
    }

    public void setWalletGroupVoucherData(ArrayList<SymWalletGroupVoucher> walletGroupVoucherData) {
        this.walletGroupVoucherData = walletGroupVoucherData;
    }

}
