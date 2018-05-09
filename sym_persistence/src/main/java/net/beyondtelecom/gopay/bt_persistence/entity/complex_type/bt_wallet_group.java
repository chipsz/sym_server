package net.beyondtelecom.gopay.bt_persistence.entity.complex_type;

import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "wallet_group_id"))
public class bt_wallet_group extends bt_enum_entity<bt_wallet_group> {

	Double default_discount;

	public bt_wallet_group() {}

	public Double getDefault_discount() {
		return default_discount;
	}

	public void setDefault_discount(Double default_discount) {
		this.default_discount = default_discount;
	}
}
