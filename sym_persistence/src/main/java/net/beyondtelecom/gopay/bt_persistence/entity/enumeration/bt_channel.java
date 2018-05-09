package net.beyondtelecom.gopay.bt_persistence.entity.enumeration;

import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
@AttributeOverride(name = "id", column = @Column(name = "channel_id"))
public class bt_channel extends bt_enum_entity<bt_channel> {

    @Basic
    @Column(nullable = false, updatable = false, columnDefinition = "bit default false")
    private Boolean is_pin_based;

    public bt_channel() {}
	public bt_channel(String name, Boolean enabled, Boolean is_pin_based) {
		super(name, enabled);
		this.is_pin_based = is_pin_based;
	}

    public Boolean is_pin_based() { return is_pin_based; }

    public void setIs_pin_based(Boolean is_pin_based) { this.is_pin_based = is_pin_based; }

}
