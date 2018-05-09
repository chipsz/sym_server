package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
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
public class SymList extends DataContract<SymList> implements Serializable {

    protected ArrayList responseData;

    public SymList() {
    }

    public SymList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymList(SymResponseCode symResponseCode, ArrayList responseData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.responseData = responseData;
    }

    public ArrayList getResponseData() {
        return responseData;
    }

    public void setResponseData(ArrayList responseData) {
        this.responseData = responseData;
    }
}
