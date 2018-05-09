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
@Table(name = "bt_distribution_channel")
@AttributeOverride(name = "id", column = @Column(name = "distribution_channel_id"))
public class bt_distribution_channel extends bt_enum_entity<bt_distribution_channel> {
	public enum DISTRIBUTION_CHANNEL { RECEIPT, SMS }
	public bt_distribution_channel() {}
	public bt_distribution_channel(String name, Boolean enabled) { super(name, enabled); }
}
