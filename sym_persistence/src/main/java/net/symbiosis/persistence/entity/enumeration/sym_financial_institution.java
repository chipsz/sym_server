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
@Table(name = "sym_financial_institution")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "institution_id")),
        @AttributeOverride(name = "name", column = @Column(name = "institution_name", unique = true))
})
@Cacheable(false)
public class sym_financial_institution extends sym_enum_entity<sym_financial_institution> {

    @Basic
    @Column(unique = true)
    private String short_name;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_type_id")
    private sym_financial_institution_type institution_type;
    @Basic
    @Column(unique = true)
    private String swift_code;
    @Basic
    private String physical_address;
    @Basic
    private String contact_phone;
    @Basic
    private String contact_fax;
    @Basic
    private String contact_email;

    public sym_financial_institution() {
    }

    public sym_financial_institution(String name, Boolean enabled, String short_name,
                                     sym_financial_institution_type institution_type, String swift_code,
                                     String physical_address, String contact_phone, String contact_fax,
                                     String contact_email) {
        super(name, enabled);
        this.short_name = short_name;
        this.institution_type = institution_type;
        this.swift_code = swift_code;
        this.physical_address = physical_address;
        this.contact_phone = contact_phone;
        this.contact_fax = contact_fax;
        this.contact_email = contact_email;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public sym_financial_institution_type getInstitution_type() {
        return institution_type;
    }

    public void setInstitution_type(sym_financial_institution_type institution_type) {
        this.institution_type = institution_type;
    }

    public String getSwift_code() {
        return swift_code;
    }

    public void setSwift_code(String swift_code) {
        this.swift_code = swift_code;
    }

    public String getPhysical_address() {
        return physical_address;
    }

    public void setPhysical_address(String physical_address) {
        this.physical_address = physical_address;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_fax() {
        return contact_fax;
    }

    public void setContact_fax(String contact_fax) {
        this.contact_fax = contact_fax;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }
}
