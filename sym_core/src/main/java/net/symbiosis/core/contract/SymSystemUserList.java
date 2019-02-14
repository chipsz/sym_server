package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymSystemUser;
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
public class SymSystemUserList extends DataContract<SymSystemUserList> implements Serializable {

    protected ArrayList<SymSystemUser> systemUserData;

    public SymSystemUserList() {
    }

    public SymSystemUserList(SymResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public SymSystemUserList(SymResponseCode symResponseCode, SymSystemUser systemUser) {
        super(symResponseCode);
        this.systemUserData = new ArrayList<>();
        this.systemUserData.add(systemUser);
    }

    public SymSystemUserList(SymResponseCode symResponseCode, ArrayList<SymSystemUser> systemUserData) {
        super(symResponseCode);
        this.systemUserData = systemUserData;
    }

    public ArrayList<SymSystemUser> getSystemUserData() {
        return systemUserData;
    }

    public void setSystemUserData(ArrayList<SymSystemUser> SymSystemUser) {
        this.systemUserData = SymSystemUser;
    }
}
