package net.beyondtelecom.gopay.bt_persistence.entity.complex_type;

import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_country;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

import javax.persistence.*;

/***************************************************************************
 * Created:     19 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "company_id"))
public class bt_company extends bt_entity<bt_company> {

    @Column(unique = true)
    private String company_name;
    private String address_line_1;
    private String address_line_2;
    private String address_city;
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_country_id")
    private bt_country address_country;
    private String phone1;
    private String phone2;
    @Column(unique = true)
    private String vat_number;
    @Column(unique = true)
    private String registration_number;

    public bt_company() {}

    public bt_company(String company_name, String address_line_1, String address_line_2, String address_city,
                             bt_country address_country, String phone1, String phone2, String vat_number,
                             String registration_number) {
        this.company_name = company_name;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.address_city = address_city;
        this.address_country = address_country;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.vat_number = vat_number;
        this.registration_number = registration_number;
    }

    public String getCompany_name() { return company_name; }

    public void setCompany_name(String company_name) { this.company_name = company_name; }

    public String getAddress_line_1() { return address_line_1; }

    public void setAddress_line_1(String address_line_1) { this.address_line_1 = address_line_1; }

    public String getAddress_line_2() { return address_line_2; }

    public void setAddress_line_2(String address_line_2) { this.address_line_2 = address_line_2; }

    public String getAddress_city() { return address_city; }

    public void setAddress_city(String address_city) { this.address_city = address_city; }

    public bt_country getAddress_country() { return address_country; }

    public void setAddress_country(bt_country address_country) { this.address_country = address_country; }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String msisdn) {
        this.phone1 = msisdn;
    }

    public String getPhone2() { return phone2; }

    public void setPhone2(String msisdn2) { this.phone2 = msisdn2; }

    public String getVat_number() { return vat_number; }

    public void setVat_number(String vat_number) { this.vat_number = vat_number; }

    public String getRegistration_number() { return registration_number; }

    public void setRegistration_number(String registration_number) { this.registration_number = registration_number; }

    @Override
    public String toString() { return company_name; }
}