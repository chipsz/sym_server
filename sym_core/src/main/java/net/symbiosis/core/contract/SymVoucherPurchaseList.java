package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymVoucherPurchase;
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
public class SymVoucherPurchaseList extends DataContract<SymVoucherPurchaseList> implements Serializable {

    protected ArrayList<SymVoucherPurchase> voucherPurchaseData;

    public SymVoucherPurchaseList() {
    }

    public SymVoucherPurchaseList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymVoucherPurchaseList(SymResponseCode symResponseCode, ArrayList<SymVoucherPurchase> voucherPurchaseData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.voucherPurchaseData = voucherPurchaseData;
    }

    public SymVoucherPurchaseList(SymResponseCode symResponseCode, SymVoucherPurchase voucherProvider) {
        this.symResponse = new SymResponse(symResponseCode);
        this.voucherPurchaseData = new ArrayList<>();
        this.voucherPurchaseData.add(voucherProvider);
    }

    public ArrayList<SymVoucherPurchase> getVoucherPurchaseData() {
        return voucherPurchaseData;
    }

    public void setVoucherPurchaseData(ArrayList<SymVoucherPurchase> voucherPurchaseData) {
        this.voucherPurchaseData = voucherPurchaseData;
    }
}
