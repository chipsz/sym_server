package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.*;

/***************************************************************************
 * Created:     22 / 01 / 2017                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Entity
@Table(name = "sym_currency")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "currency_id")),
        @AttributeOverride(name = "name", column = @Column(name = "currency_name", unique = true))
})
public class sym_currency extends sym_enum_entity<sym_currency> {

    @Basic
    @Column(unique = true, nullable = false, length = 3)
    private String iso_4217_code;
    @Basic
    @Column(unique = true, nullable = false, length = 3)
    private String iso_4217_num;

    public sym_currency() {
    }

    public sym_currency(String name, Boolean enabled, String iso_4217_code, String iso_4217_num) {
        super(name, enabled);
        this.iso_4217_code = iso_4217_code;
        this.iso_4217_num = iso_4217_num;
    }

    public String getIso_4217_code() {
        return iso_4217_code;
    }

    public void setIso_4217_code(String iso_code_2) {
        this.iso_4217_code = iso_code_2;
    }

    public String getIso_4217_num() {
        return iso_4217_num;
    }

    public void setIso_4217_num(String iso_code_3) {
        this.iso_4217_num = iso_code_3;
    }
}
