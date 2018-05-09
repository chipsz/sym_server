package net.beyondtelecom.gopay.bt_core.contract.gopay;

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
public class BTWalletGroup implements Serializable {

	private Long walletGroupId;
	private String name;
	private Boolean enabled;
	private Double defaultDiscount;

	public BTWalletGroup(Long walletGroupId, String name, Boolean enabled, Double defaultDiscount) {
		this.walletGroupId = walletGroupId;
		this.name = name;
		this.enabled = enabled;
		this.defaultDiscount = defaultDiscount;
	}

	public BTWalletGroup() {}

	public Long getWalletGroupId() { return walletGroupId; }

	public void setWalletGroupId(Long walletGroupId) { this.walletGroupId = walletGroupId; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public Boolean getEnabled() { return enabled; }

	public void setEnabled(Boolean enabled) { this.enabled = enabled; }

	public Double getDefaultDiscount() { return defaultDiscount; }

	public void setDefaultDiscount(Double defaultDiscount) { this.defaultDiscount = defaultDiscount; }

}
