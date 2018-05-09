package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.base.EnumEntityData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTEnumEntity extends DataContract<BTEnumEntity> implements Serializable {

	protected EnumEntityData enumEntityData;

    public BTEnumEntity() {}

    public BTEnumEntity(BTResponseCode btResponseCode) { super(btResponseCode); }

	public BTEnumEntity(BTResponseCode btResponseCode, EnumEntityData enumEntityData) {
		super(btResponseCode);
		this.enumEntityData = enumEntityData;
	}

	public EnumEntityData getEnumEntityData() { return enumEntityData; }

	public void setEnumEntityData(EnumEntityData enumEntityData) { this.enumEntityData = enumEntityData; }
}
