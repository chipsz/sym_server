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
@AttributeOverride(name = "id", column = @Column(name = "auth_group_id"))
public class sym_auth_group extends sym_enum_entity<sym_auth_group> {
    public sym_auth_group() {
    }

    public sym_auth_group(String name, Boolean enabled) {
        super(name, enabled);
    }
}
