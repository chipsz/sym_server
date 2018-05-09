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
@AttributeOverride(name = "id", column = @Column(name = "institution_type_id"))
public class bt_financial_institution_type extends bt_enum_entity<bt_financial_institution_type> {
	public bt_financial_institution_type() {}
	public bt_financial_institution_type(String name, Boolean enabled) {
		super(name, enabled);
	}
}
