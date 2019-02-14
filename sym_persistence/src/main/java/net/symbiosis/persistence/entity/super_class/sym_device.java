package net.symbiosis.persistence.entity.super_class;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;

import javax.persistence.*;
import java.util.Date;

/***************************************************************************
 * *
 * Created:     14 / 02 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@MappedSuperclass
public abstract class sym_device<E extends sym_device> extends sym_entity<E> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_user_id")
    protected sym_auth_user auth_user;
    @Basic
    @Column(nullable = false, columnDefinition = "bit default true")
    protected Boolean is_active = true;
    @Basic(optional = false)
    protected Date registration_date;
    @Basic(optional = false)
    protected Date last_auth_date;

	public sym_device() {}

    public sym_device(sym_auth_user auth_user, Boolean is_active, Date registration_date, Date last_auth_date) {
        this.auth_user = auth_user;
        this.is_active = is_active;
        this.registration_date = registration_date;
        this.last_auth_date = last_auth_date;
    }

    public sym_auth_user getAuth_user() { return auth_user; }

    public void setAuth_user(sym_auth_user auth_user) { this.auth_user = auth_user; }

    public Boolean getIs_active() { return is_active; }

    public void setIs_active(Boolean is_active) { this.is_active = is_active; }

    public Date getRegistration_date() { return registration_date; }

    public void setRegistration_date(Date registration_date) { this.registration_date = registration_date; }

    public Date getLast_auth_date() { return last_auth_date; }

    public void setLast_auth_date(Date last_auth_date) { this.last_auth_date = last_auth_date; }
}
