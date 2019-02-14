package net.symbiosis.persistence.entity.complex_type;

import net.symbiosis.persistence.entity.enumeration.sym_preference;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class sym_user_preference extends sym_entity<sym_user_preference> {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sym_user_id")
    private sym_user user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_preference_id")
    private sym_preference preference;

    @Basic
    private String preference_value;

    public sym_user_preference() {
    }

    public sym_user_preference(sym_user user, sym_preference preference, String preference_value) {
        this.user = user;
        this.preference = preference;
        this.preference_value = preference_value;
    }

    public sym_user getUser() {
        return user;
    }

    public void setUser(sym_user user) {
        this.user = user;
    }

    public sym_preference getPreference() {
        return preference;
    }

    public void setPreference(sym_preference user_preference) {
        this.preference = user_preference;
    }

    public String getPreference_value() {
        return preference_value;
    }

    public void setPreference_value(String preference_value) {
        this.preference_value = preference_value;
    }
}
