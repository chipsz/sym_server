package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
@AttributeOverride(name = "id", column = @Column(name = "channel_id"))
public class sym_channel extends sym_enum_entity<sym_channel> {

    @Basic
    @Column(nullable = false, updatable = false, columnDefinition = "bit default false")
    private Boolean is_pin_based;

    public sym_channel() {
    }

    public sym_channel(String name, Boolean enabled, Boolean is_pin_based) {
        super(name, enabled);
        this.is_pin_based = is_pin_based;
    }

    public Boolean is_pin_based() {
        return is_pin_based;
    }

    public void setIs_pin_based(Boolean is_pin_based) {
        this.is_pin_based = is_pin_based;
    }

}
