package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class sym_language extends sym_enum_entity<sym_language> {
    public sym_language() {
    }

    public sym_language(String name, Boolean enabled) {
        super(name, enabled);
    }
}
