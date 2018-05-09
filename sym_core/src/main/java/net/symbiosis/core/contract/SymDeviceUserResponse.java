package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymDeviceUser;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class SymDeviceUserResponse extends DataContract<SymDeviceUserResponse> implements Serializable {

    private SymDeviceUser systemUser;

    public SymDeviceUserResponse() {
    }

    public SymDeviceUserResponse(SymResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public SymDeviceUserResponse(SymResponseCode symResponseCode, SymDeviceUser systemUser) {
        super(symResponseCode);
        this.systemUser = systemUser;
    }

    public SymDeviceUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SymDeviceUser systemUser) {
        this.systemUser = systemUser;
    }
}
