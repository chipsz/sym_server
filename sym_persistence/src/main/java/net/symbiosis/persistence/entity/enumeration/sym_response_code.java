package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class sym_response_code extends sym_enum_entity<sym_response_code> {

    @Column(nullable = false)
    private String response_message;

    public sym_response_code() {
    }

    public sym_response_code(String name, String response_message, Boolean enabled) {
        super(name, enabled);
        this.response_message = response_message;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public SymResponseCode asSymResponseCode() {
        return SymResponseCode.valueOf(this.getId());
    }
}
