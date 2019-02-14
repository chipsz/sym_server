package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

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
public class sym_financial_institution_type extends sym_enum_entity<sym_financial_institution_type> {
    public sym_financial_institution_type() {
    }

    public sym_financial_institution_type(String name, Boolean enabled) {
        super(name, enabled);
    }
}
