package net.beyondtelecom.gopay.bt_persistence.entity.enumeration;

import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class bt_preference extends bt_enum_entity<bt_preference> {
    public bt_preference() {}
	public bt_preference(String name, Boolean enabled) {
		super(name, enabled);
	}
}
