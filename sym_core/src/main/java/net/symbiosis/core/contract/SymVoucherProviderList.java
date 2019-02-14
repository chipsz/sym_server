package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymVoucherProvider;
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
public class SymVoucherProviderList extends DataContract<SymVoucherProviderList> implements Serializable {

    protected ArrayList<SymVoucherProvider> voucherProviderData;

    public SymVoucherProviderList() {
    }

    public SymVoucherProviderList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymVoucherProviderList(SymResponseCode symResponseCode, ArrayList<SymVoucherProvider> voucherProviderData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.voucherProviderData = voucherProviderData;
    }

    public SymVoucherProviderList(SymResponseCode symResponseCode, SymVoucherProvider voucherProvider) {
        this.symResponse = new SymResponse(symResponseCode);
        this.voucherProviderData = new ArrayList<>();
        this.voucherProviderData.add(voucherProvider);
    }

    public ArrayList<SymVoucherProvider> getVoucherProviderData() {
        return voucherProviderData;
    }

    public void setVoucherProviderData(ArrayList<SymVoucherProvider> voucherProviderData) {
        this.voucherProviderData = voucherProviderData;
    }
}
