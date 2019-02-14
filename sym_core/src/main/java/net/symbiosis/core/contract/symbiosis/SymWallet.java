package net.symbiosis.core.contract.symbiosis;

import net.symbiosis.common.utilities.PrintableStringClass;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/
@XmlRootElement
public class SymWallet implements Serializable, PrintableStringClass {

    private Long walletId;
    private Long accountAdminUserId;
    private Long walletGroupId;
    private BigDecimal currentBalance = new BigDecimal(0.0);
    private SymSystemUser systemUser;

    public SymWallet() {
    }

    public SymWallet(Long walletId, Long accountAdminUserId, Long walletGroupId,
                     BigDecimal currentBalance, SymSystemUser systemUser) {
        this.walletId = walletId;
        this.accountAdminUserId = accountAdminUserId;
        this.walletGroupId = walletGroupId;
        this.currentBalance = currentBalance;
        this.systemUser = systemUser;
    }

    public SymWallet(Long walletId, Long accountAdminUserId, Long walletGroup, BigDecimal currentBalance) {
        this.walletId = walletId;
        this.accountAdminUserId = accountAdminUserId;
        this.walletGroupId = walletGroup;
        this.currentBalance = currentBalance;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getAccountAdminUserId() {
        return accountAdminUserId;
    }

    public void setAccountAdminUserId(Long accountAdminUserId) {
        this.accountAdminUserId = accountAdminUserId;
    }

    public Long getVoucherGroupId() {
        return walletGroupId;
    }

    public void setVoucherGroupId(Long walletGroupId) {
        this.walletGroupId = walletGroupId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public SymSystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SymSystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public String toString() {
        return this.toPrintableString();
    }
}
