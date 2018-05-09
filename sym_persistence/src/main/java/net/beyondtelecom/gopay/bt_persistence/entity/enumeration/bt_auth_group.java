package net.beyondtelecom.gopay.bt_persistence.entity.enumeration;

import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
@AttributeOverride(name = "id", column = @Column(name = "auth_group_id"))
public class bt_auth_group extends bt_enum_entity<bt_auth_group> {
	public bt_auth_group() {}
	public bt_auth_group(String name, Boolean enabled) {
		super(name, enabled);
	}
}
