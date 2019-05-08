package net.symbiosis.persistence.entity.complex_type.wallet;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
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
@Cacheable(false)
public class sym_wallet_group extends sym_enum_entity<sym_wallet_group> {

    Double default_discount;

    public sym_wallet_group() {
    }

    public Double getDefault_discount() {
        return default_discount;
    }

    public void setDefault_discount(Double default_discount) {
        this.default_discount = default_discount;
    }
}
