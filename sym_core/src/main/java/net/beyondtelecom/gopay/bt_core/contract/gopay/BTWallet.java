package net.beyondtelecom.gopay.bt_core.contract.gopay;

import net.beyondtelecom.gopay.bt_common.utilities.PrintableStringClass;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTSystemUser;

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
public class BTWallet implements Serializable, PrintableStringClass {

	private Long walletId;
	private Long accountAdminUserId;
	private Long walletGroupId;
	private BigDecimal currentBalance = new BigDecimal(0.0);
	private BTSystemUser systemUser;

	public BTWallet(){}

	public BTWallet(Long walletId, Long accountAdminUserId, Long walletGroupId,
                    BigDecimal currentBalance, BTSystemUser systemUser) {
		this.walletId = walletId;
		this.accountAdminUserId = accountAdminUserId;
		this.walletGroupId = walletGroupId;
		this.currentBalance = currentBalance;
		this.systemUser = systemUser;
	}

	public BTWallet(Long walletId, Long accountAdminUserId, Long walletGroup, BigDecimal currentBalance) {
		this.walletId = walletId;
		this.accountAdminUserId = accountAdminUserId;
		this.walletGroupId = walletGroup;
		this.currentBalance = currentBalance;
	}

	public Long getWalletId() { return walletId; }

	public void setWalletId(Long walletId) { this.walletId = walletId; }

	public Long getAccountAdminUserId() { return accountAdminUserId; }

	public void setAccountAdminUserId(Long accountAdminUserId) { this.accountAdminUserId = accountAdminUserId; }

	public Long getVoucherGroupId() { return walletGroupId; }

	public void setVoucherGroupId(Long walletGroupId) { this.walletGroupId = walletGroupId; }

	public BigDecimal getCurrentBalance() { return currentBalance; }

	public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }

	public BTSystemUser getSystemUser() { return systemUser; }

	public void setSystemUser(BTSystemUser systemUser) { this.systemUser = systemUser; }

	@Override
	public String toString() {
		return this.toPrintableString();
	}
}
