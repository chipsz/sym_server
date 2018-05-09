package net.beyondtelecom.gopay.bt_core.contract.base;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.BTResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public abstract class DataContract<T extends DataContract> implements Serializable
{
    protected BTResponse btResponse;

    public DataContract() {}

    public DataContract(BTResponseCode btResponse) { this.btResponse = new BTResponse(btResponse); }

    public BTResponse getBTResponse() { return btResponse; }

    public T setBTResponse(BTResponse btResponse) { this.btResponse = btResponse; return (T)this; }

    public T setResponse(String response) { this.btResponse.setResponse_message(response); return (T)this; }

    public T setBTResponseCode(BTResponseCode btResponseCode) {
        this.setBTResponse(new BTResponse(btResponseCode));
        return (T)this;
    }
}
