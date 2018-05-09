package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

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
@Table(name = "sym_distribution_channel")
@AttributeOverride(name = "id", column = @Column(name = "distribution_channel_id"))
public class sym_distribution_channel extends sym_enum_entity<sym_distribution_channel> {
    public enum DISTRIBUTION_CHANNEL {RECEIPT, SMS}

    public sym_distribution_channel() {
    }

    public sym_distribution_channel(String name, Boolean enabled) {
        super(name, enabled);
    }
}
