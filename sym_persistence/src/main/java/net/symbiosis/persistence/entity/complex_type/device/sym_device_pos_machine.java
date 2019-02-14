package net.symbiosis.persistence.entity.complex_type.device;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.super_class.sym_device;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "pos_machine_id"))
public class sym_device_pos_machine extends sym_device<sym_device_pos_machine> {

    @Column(nullable = false)
    private String branch_name;
    @Column(nullable = false)
    private String machine_name;
    @Column(nullable = false, length = 16)
    private String imei1;
    @Column(nullable = false, length = 16)
    private String imei2;
    @Column(length = 16)
    private String imsi1;
    @Column(length = 16)
    private String imsi2;
    @Column(length = 12)
    private String msisdn1;
    @Column(length = 12)
    private String msisdn2;

	public sym_device_pos_machine() {}

    public sym_device_pos_machine(sym_auth_user auth_user, Boolean is_active, Date registration_date,
                                  Date last_auth_date, String branch_name, String machine_name, String imei1,
                                  String imei2, String imsi1, String imsi2, String msisdn1, String msisdn2) {
        super(auth_user, is_active, registration_date, last_auth_date);
        this.branch_name = branch_name;
        this.machine_name = machine_name;
        this.imei1 = imei1;
        this.imei2 = imei2;
        this.imsi1 = imsi1;
        this.imsi2 = imsi2;
        this.msisdn1 = msisdn1;
        this.msisdn2 = msisdn2;
    }

    public sym_auth_user getAuth_user() { return auth_user; }

    public void setAuth_user(sym_auth_user auth_user) { this.auth_user = auth_user; }

    public String getBranch_name() { return branch_name; }

    public void setBranch_name(String branch_name) { this.branch_name = branch_name; }

    public String getMachine_name() { return machine_name; }

    public void setMachine_name(String machine_name) { this.machine_name = machine_name; }

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
