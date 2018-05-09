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
@Table(name = "sym_deposit_type")
@AttributeOverride(name = "id", column = @Column(name = "deposit_type_id"))
public class sym_deposit_type extends sym_enum_entity<sym_deposit_type> {
    public enum DEPOSIT_TYPE {CASH, BANK_DEPOSI, CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER}

    public sym_deposit_type() {
    }

    public sym_deposit_type(String name, Boolean enabled) {
        super(name, enabled);
    }
}
