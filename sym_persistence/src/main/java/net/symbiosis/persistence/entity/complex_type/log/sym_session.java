package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created with Intelli_j IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */

@Entity
public class sym_session extends sym_entity<sym_session> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_user_id")
    private sym_auth_user auth_user;
    @Basic
    private String device_id;
    @Basic(optional = false)
    private String auth_token;
    @Basic(optional = false)
    private Date start_time;
    @Basic(optional = true)
    private Date end_time;

    public sym_session() {}

    public sym_session(sym_auth_user auth_user, String device_id, String auth_token, Date start_time, Date end_time) {
        this.auth_user = auth_user;
        this.device_id = device_id;
        this.auth_token = auth_token;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public sym_auth_user getAuth_user() {
        return auth_user;
    }

    public void setAuth_user(sym_auth_user auth_user) {
        this.auth_user = auth_user;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date timestamp) {
        this.start_time = timestamp;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}
