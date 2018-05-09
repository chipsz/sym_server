package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTSystemUser;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTSystemUserList extends DataContract<BTSystemUserList> implements Serializable {

	protected ArrayList<BTSystemUser> systemUserData;

    public BTSystemUserList() {}

    public BTSystemUserList(BTResponseCode btResponseCode) { super(btResponseCode); }

	public BTSystemUserList(BTResponseCode btResponseCode, BTSystemUser systemUser) {
		super(btResponseCode);
		this.systemUserData = new ArrayList<>();
		this.systemUserData.add(systemUser);
	}

	public BTSystemUserList(BTResponseCode btResponseCode, ArrayList<BTSystemUser> systemUserData) {
		super(btResponseCode);
		this.systemUserData = systemUserData;
	}

	public ArrayList<BTSystemUser> getSystemUserData() { return systemUserData; }

	public void setSystemUserData(ArrayList<BTSystemUser> BTSystemUser) { this.systemUserData = BTSystemUser; }
}
