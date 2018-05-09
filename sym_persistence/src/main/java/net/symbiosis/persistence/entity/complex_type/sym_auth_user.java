package net.symbiosis.persistence.entity.complex_type;

import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "auth_user_id"))
public class sym_auth_user extends sym_entity<sym_auth_user> {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "sym_user_id")
    private sym_user user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_id")
    private sym_channel channel;
    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_group_id")
    private sym_auth_group auth_group;
    @Basic(optional = true)
    private String device_id;
    @Basic(optional = true)
    private String auth_token;
    @Basic(optional = false)
    private Date registration_date;
    @Basic(optional = true)
    private Date last_auth_date;
    @Basic(optional = true)
    private Date last_login_date;

    public sym_auth_user() {
    }

    public sym_auth_user(sym_user user, sym_channel channel, sym_auth_group auth_group,
                         String device_id, String auth_token, Date registration_date, Date last_auth_date,
                         Date last_login_date) {
        this.user = user;
        this.channel = channel;
        this.auth_group = auth_group;
        this.device_id = device_id;
        this.auth_token = auth_token;
        this.registration_date = registration_date;
        this.last_auth_date = last_auth_date;
        this.last_login_date = last_login_date;
    }

    public sym_user getUser() {
        return user;
    }

    public sym_auth_user setUser(sym_user user) {
        this.user = user;
        return this;
    }

    public sym_channel getChannel() {
        return channel;
    }

    public sym_auth_user setChannel(sym_channel channel) {
        this.channel = channel;
        return this;
    }

    public sym_auth_group getAuth_group() {
        return auth_group;
    }

    public void setAuth_group(sym_auth_group group) {
        this.auth_group = group;
    }

    public String getDevice_id() {
        return device_id;
    }

    public sym_auth_user setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public sym_auth_user setAuth_token(String auth_token) {
        this.auth_token = auth_token;
        return this;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public sym_auth_user setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
        return this;
    }

    public Date getLast_auth_date() {
        return last_auth_date;
    }

    public sym_auth_user setLast_auth_date(Date last_auth_date) {
        this.last_auth_date = last_auth_date;
        return this;
    }

    public Date getLast_login_date() {
        return last_login_date;
    }

    public sym_auth_user setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
        return this;
    }
}
