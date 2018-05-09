package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTResponse implements Serializable
{
    protected BTResponseCode response;
    protected Integer response_code;
    protected String response_message;

    public BTResponse() {}

    public BTResponse(BTResponseCode btResponseCode) {
        this.setResponse(btResponseCode);
    }

    public BTResponse(bt_response_code btResponseCode) {
        this.setResponse(BTResponseCode.valueOf(btResponseCode.getId().intValue()));
    }

    public BTResponseCode getResponse() { return response; }

    public void setResponse(BTResponseCode response) {
        this.response = response;
        this.response_code = response.getCode();
        this.response_message = response.getMessage();
    }

    public Integer getResponse_code() { return response_code; }

    public BTResponse setResponse_code(Integer response_code) { this.response_code = response_code; return this; }

    public String getResponse_message() { return response_message; }

    public BTResponse setResponse_message(String response_message) { this.response_message = response_message; return this; }
}
