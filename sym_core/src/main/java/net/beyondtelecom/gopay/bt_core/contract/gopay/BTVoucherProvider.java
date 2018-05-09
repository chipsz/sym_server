package net.beyondtelecom.gopay.bt_core.contract.gopay;

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
public class BTVoucherProvider implements Serializable{
	private Long voucherProviderId;
	private String voucherProviderName;
	private BigDecimal currentBalance;

	public BTVoucherProvider() {}

	public BTVoucherProvider(Long voucherProviderId, String voucherProviderName) {
		this.voucherProviderId = voucherProviderId;
		this.voucherProviderName = voucherProviderName;
	}

	public Long getVoucherProviderId() {
		return voucherProviderId;
	}

	public void setVoucherProviderId(Long voucherProviderId) {
		this.voucherProviderId = voucherProviderId;
	}

	public String getVoucherProviderName() {
		return voucherProviderName;
	}

	public void setVoucherProviderName(String voucherProviderName) {
		this.voucherProviderName = voucherProviderName;
	}

	public BigDecimal getCurrentBalance() { return currentBalance; }

	public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
}
