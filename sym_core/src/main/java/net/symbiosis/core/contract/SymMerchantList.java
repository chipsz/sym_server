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
public class SymMerchantList extends DataContract<SymMerchantList> implements Serializable {

    protected ArrayList<SymWallet> merchantData;

    public SymMerchantList() {
    }

    public SymMerchantList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymMerchantList(SymResponseCode symResponseCode, SymWallet merchant) {
        this.symResponse = new SymResponse(symResponseCode);
        this.merchantData = new ArrayList<>();
        this.merchantData.add(merchant);
    }

    public SymMerchantList(SymResponseCode symResponseCode, ArrayList<SymWallet> merchantData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.merchantData = merchantData;
    }

    public ArrayList<SymWallet> getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(ArrayList<SymWallet> merchantData) {
        this.merchantData = merchantData;
    }

}
