package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;

/***************************************************************************
 * Created:     16 / 12 / 2016                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class SymMap extends DataContract<SymMap> implements Serializable {

    protected Map responseData;

    public SymMap() {
    }

    public SymMap(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymMap(SymResponseCode symResponseCode, Map responseData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.responseData = responseData;
    }

    public Map getResponseData() {
        return responseData;
    }

    public void setResponseData(Map responseData) {
        this.responseData = responseData;
    }
}
