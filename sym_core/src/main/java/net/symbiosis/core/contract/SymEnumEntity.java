package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.base.EnumEntityData;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class SymEnumEntity extends DataContract<SymEnumEntity> implements Serializable {

    protected EnumEntityData enumEntityData;

    public SymEnumEntity() {
    }

    public SymEnumEntity(SymResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public SymEnumEntity(SymResponseCode symResponseCode, EnumEntityData enumEntityData) {
        super(symResponseCode);
        this.enumEntityData = enumEntityData;
    }

    public EnumEntityData getEnumEntityData() {
        return enumEntityData;
    }

    public void setEnumEntityData(EnumEntityData enumEntityData) {
        this.enumEntityData = enumEntityData;
    }
}
