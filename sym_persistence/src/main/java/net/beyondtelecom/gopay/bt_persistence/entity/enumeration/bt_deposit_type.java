package net.beyondtelecom.gopay.bt_persistence.entity.enumeration;

import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@Table(name = "bt_deposit_type")
@AttributeOverride(name = "id", column = @Column(name = "deposit_type_id"))
public class bt_deposit_type extends bt_enum_entity<bt_deposit_type> {
	public enum DEPOSIT_TYPE { CASH, BANK_DEPOSI, CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER }
	public bt_deposit_type() {}
	public bt_deposit_type(String name, Boolean enabled) { super(name, enabled); }
}
