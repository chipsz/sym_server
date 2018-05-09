package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymVoucher;
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
public class SymVoucherList extends DataContract<SymVoucherList> implements Serializable {

    protected ArrayList<SymVoucher> voucherData;

    public SymVoucherList() {
    }

    public SymVoucherList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymVoucherList(SymResponseCode symResponseCode, ArrayList<SymVoucher> voucherData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.voucherData = voucherData;
    }

    public SymVoucherList(SymResponseCode symResponseCode, SymVoucher voucher) {
        this.symResponse = new SymResponse(symResponseCode);
        this.voucherData = new ArrayList<>();
        this.voucherData.add(voucher);
    }

    public ArrayList<SymVoucher> getVoucherData() {
        return voucherData;
    }

    public void setVoucherData(ArrayList<SymVoucher> voucherData) {
        this.voucherData = voucherData;
    }

}
