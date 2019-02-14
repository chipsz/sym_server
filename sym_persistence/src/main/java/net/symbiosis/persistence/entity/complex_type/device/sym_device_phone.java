package net.symbiosis.persistence.entity.complex_type.device;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.super_class.sym_device;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/***************************************************************************
 * *
 * Created:     14 / 02 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "phone_id"))
public class sym_device_phone extends sym_device<sym_device_phone> {

    @Column(length = 15)
    private String imei1;
    @Column(length = 15)
    private String imei2;
    @Column(length = 16)
    private String imsi1;
    @Column(length = 16)
    private String imsi2;
    @Column(length = 12)
    private String msisdn1;
    @Column(length = 12)
    private String msisdn2;

	public sym_device_phone() {}

    public sym_device_phone(sym_auth_user auth_user, Boolean is_active, Date registration_date, Date last_auth_date,
                            String imei1, String imei2, String imsi1, String imsi2, String msisdn1, String msisdn2) {
        super(auth_user, is_active, registration_date, last_auth_date);
        this.imei1 = imei1;
        this.imei2 = imei2;
        this.imsi1 = imsi1;
        this.imsi2 = imsi2;
        this.msisdn1 = msisdn1;
        this.msisdn2 = msisdn2;
    }

    public sym_auth_user getAuth_user() { return auth_user; }

    public void setAuth_user(sym_auth_user auth_user) { this.auth_user = auth_user; }

    public String getImei1() { return imei1; }

    public void setImei1(String imei1) { this.imei1 = imei1; }

    public String getImei2() { return imei2; }

    public void setImei2(String imei2) { this.imei2 = imei2; }

    public String getImsi1() { return imsi1; }

    public void setImsi1(String imsi1) { this.imsi1 = imsi1; }

    public String getImsi2() { return imsi2; }

    public void setImsi2(String imsi2) { this.imsi2 = imsi2; }

    public String getMsisdn1() { return msisdn1; }

    public void setMsisdn1(String msisdn1) { this.msisdn1 = msisdn1; }

    public String getMsisdn2() { return msisdn2; }

    public void setMsisdn2(String msisdn2) { this.msisdn2 = msisdn2; }
}
