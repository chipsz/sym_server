package net.symbiosis.core.contract;

import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class SymResponse implements Serializable {
    protected SymResponseCode response;
    protected Integer response_code;
    protected String response_message;

    public SymResponse() {
    }

    public SymResponse(SymResponseCode symResponseCode) {
        this.setResponse(symResponseCode);
    }

    public SymResponse(sym_response_code symResponseCode) {
        this.setResponse(SymResponseCode.valueOf(symResponseCode.getId()));
    }

    public SymResponseCode getResponse() {
        return response;
    }

    public void setResponse(SymResponseCode response) {
        this.response = response;
        this.response_code = response.getCode();
        this.response_message = response.getMessage();
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public SymResponse setResponse_code(Integer response_code) {
        this.response_code = response_code;
        return this;
    }

    public String getResponse_message() {
        return response_message;
    }

    public SymResponse setResponse_message(String response_message) {
        this.response_message = response_message;
        return this;
    }
}
