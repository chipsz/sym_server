package net.beyondtelecom.gopay.bt_persistence.entity.enumeration;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class bt_response_code extends bt_enum_entity<bt_response_code> {

	@Column(nullable = false)
    private String response_message;

	public bt_response_code() {}

	public bt_response_code(String name, String response_message, Boolean enabled) {
		super(name, enabled);
		this.response_message = response_message;
	}

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public BTResponseCode asBTResponseCode() {
	    return BTResponseCode.valueOf(this.getId().intValue());
    }
}
