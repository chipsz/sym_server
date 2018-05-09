package net.beyondtelecom.gopay.bt_persistence.entity.complex_type;

import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_preference;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class bt_user_preference extends bt_entity<bt_user_preference> {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "bt_user_id")
    private bt_user user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_preference_id")
    private bt_preference preference;

    @Basic
    private String preference_value;

    public bt_user_preference() {}

    public bt_user_preference(bt_user user, bt_preference preference, String preference_value) {
        this.user = user;
        this.preference = preference;
        this.preference_value = preference_value;
    }

    public bt_user getUser() { return user; }

    public void setUser(bt_user user) { this.user = user; }

    public bt_preference getPreference() { return preference; }

    public void setPreference(bt_preference user_preference) {
        this.preference = user_preference;
    }

    public String getPreference_value() { return preference_value; }

    public void setPreference_value(String preference_value) { this.preference_value = preference_value; }
}
