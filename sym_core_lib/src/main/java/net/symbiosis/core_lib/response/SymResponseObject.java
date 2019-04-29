package net.symbiosis.core_lib.response;

import net.symbiosis.core_lib.enumeration.SymResponseCode;

/**
 * User: tkaviya
 * Date: 3/27/2015
 * Time: 7:47 PM
 */
public class SymResponseObject<T> {
    SymResponseCode responseCode;
    T responseObject = null;

    public SymResponseObject(SymResponseCode responseCode, T responseObject) {
        this.responseCode = responseCode;
        this.responseObject = responseObject;
    }

    public SymResponseObject(SymResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public int getCode() {
        return responseCode.code;
    }

    //Override default message with custom message. Return this to allow method chaining
    public SymResponseObject<T> setMessage(String responseMessage) {
        responseCode.message = responseMessage;
        return this;
    }

    public String getMessage() {
        return responseCode.message;
    }

    public SymResponseCode getResponseCode() {
        return responseCode;
    }

    //Change response code for this.symResponseObject. Return this to allow method chaining
    public SymResponseObject<T> setResponseCode(SymResponseCode responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public T getResponseObject() {
        return responseObject;
    }

    //Change object for this.symResponseObject. Return this to allow method chaining
    public SymResponseObject<T> setResponseObject(T responseObject) {
        this.responseObject = responseObject;
        return this;
    }

    @Override
    public String toString() {
        return "{" + responseCode.getCode() + ":" + responseCode.getMessage() + "}";
    }
}
