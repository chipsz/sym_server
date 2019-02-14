package net.symbiosis.core.contract.symbiosis;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/
@XmlRootElement
public class SymWalletGroupVoucher implements Serializable {

    private Long walletGroupVoucherId;
    private Long walletGroupId;
    private Long voucherId;
    private Double merchantDiscount;

    public SymWalletGroupVoucher() {
    }

    public SymWalletGroupVoucher(Long walletGroupVoucherId, Long walletGroupId, Long voucherId, Double merchantDiscount) {
        this.walletGroupVoucherId = walletGroupVoucherId;
        this.walletGroupId = walletGroupId;
        this.voucherId = voucherId;
        this.merchantDiscount = merchantDiscount;
    }

    public Long getWalletGroupVoucherId() {
        return walletGroupVoucherId;
    }

    public void setWalletGroupVoucherId(Long walletGroupVoucherId) {
        this.walletGroupVoucherId = walletGroupVoucherId;
    }

    public Long getVoucherGroupId() {
        return walletGroupId;
    }

    public void setVoucherGroupId(Long walletGroupId) {
        this.walletGroupId = walletGroupId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Double getMerchantDiscount() {
        return merchantDiscount;
    }

    public void setMerchantDiscount(Double merchantDiscount) {
        this.merchantDiscount = merchantDiscount;
    }
}
