package net.beyondtelecom.gopay.bt_persistence.entity.complex_type;

import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "auth_user_id"))
public class bt_auth_user extends bt_entity<bt_auth_user>
{
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "bt_user_id")
	private bt_user user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_id")
	private bt_channel channel;
    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_group_id")
	private bt_auth_group auth_group;
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

	public bt_auth_user() {}

    public bt_auth_user(bt_user user, bt_channel channel, bt_auth_group auth_group,
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

    public bt_user getUser() { return user; }

	public bt_auth_user setUser(bt_user user) { this.user = user; return this; }

	public bt_channel getChannel() {
		return channel;
	}

	public bt_auth_user setChannel(bt_channel channel) { this.channel = channel; return this; }

	public bt_auth_group getAuth_group() {
		return auth_group;
	}

	public void setAuth_group(bt_auth_group group) { this.auth_group = group; }

    public String getDevice_id() {
        return device_id;
    }

    public bt_auth_user setDevice_id(String device_id) { this.device_id = device_id; return this; }

    public String getAuth_token() {
        return auth_token;
    }

    public bt_auth_user setAuth_token(String auth_token) { this.auth_token = auth_token; return this; }

    public Date getRegistration_date() {
        return registration_date;
    }

    public bt_auth_user setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
	    return this;
    }

    public Date getLast_auth_date() {
        return last_auth_date;
    }

    public bt_auth_user setLast_auth_date(Date last_auth_date) {
        this.last_auth_date = last_auth_date;
	    return this;
    }

    public Date getLast_login_date() {
        return last_login_date;
    }

    public bt_auth_user setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
	    return this;
    }
}
