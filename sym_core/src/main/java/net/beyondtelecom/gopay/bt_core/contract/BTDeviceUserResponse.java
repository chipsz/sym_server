package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTDeviceUser;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTDeviceUserResponse extends DataContract<BTDeviceUserResponse> implements Serializable {

    private BTDeviceUser systemUser;

    public BTDeviceUserResponse() {}

    public BTDeviceUserResponse(BTResponseCode btResponseCode) { super(btResponseCode); }

	public BTDeviceUserResponse(BTResponseCode btResponseCode, BTDeviceUser systemUser) {
		super(btResponseCode);
		this.systemUser = systemUser;
	}
    public BTDeviceUser getSystemUser() { return systemUser; }

    public void setSystemUser(BTDeviceUser systemUser) { this.systemUser = systemUser; }
}
