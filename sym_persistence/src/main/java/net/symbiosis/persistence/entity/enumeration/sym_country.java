package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

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
public class sym_country extends sym_enum_entity<sym_country> {

    @Basic
    @Column(nullable = false, length = 2)
    private String iso_code_2;
    @Basic
    @Column(nullable = false, length = 3)
    private String iso_code_3;
    @Basic
    @Column(nullable = false, length = 6)
    private String dialing_code;

    public sym_country() {
    }

    public sym_country(String name, Boolean enabled, String iso_code_2, String iso_code_3, String dialing_code) {
        super(name, enabled);
        this.iso_code_2 = iso_code_2;
        this.iso_code_3 = iso_code_3;
        this.dialing_code = dialing_code;
    }

    public String getIso_code_2() {
        return iso_code_2;
    }

    public void setIso_code_2(String iso_code_2) {
        this.iso_code_2 = iso_code_2;
    }

    public String getIso_code_3() {
        return iso_code_3;
    }

    public void setIso_code_3(String iso_code_3) {
        this.iso_code_3 = iso_code_3;
    }

    public String getDialing_code() {
        return dialing_code;
    }

    public void setDialing_code(String dialing_code) {
        this.dialing_code = dialing_code;
    }
}
