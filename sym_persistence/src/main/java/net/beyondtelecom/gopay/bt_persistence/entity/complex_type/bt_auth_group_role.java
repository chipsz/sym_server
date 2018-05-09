package net.beyondtelecom.gopay.bt_persistence.entity.complex_type;

import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_role;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;

import javax.persistence.*;

/**
 * Created with Intelli_j IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
@AttributeOverride(name = "id", column = @Column(name = "auth_group_role_id"))
public class bt_auth_group_role extends bt_enum_entity<bt_auth_group_role> {

	@ManyToOne(optional = false)
	@JoinColumn(name = "auth_group_id")
	private bt_auth_group auth_group;

	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id")
	private bt_role role;

	public bt_auth_group_role() {}

	public bt_auth_group_role(String name, Boolean enabled, bt_auth_group auth_group, bt_role role) {
		super(name, enabled);
		this.auth_group = auth_group;
		this.role = role;
	}
    public bt_auth_group getAuth_group() {
        return auth_group;
    }

    public void setAuth_group(bt_auth_group auth_group) {
        this.auth_group = auth_group;
    }

    public bt_role getRole() {
		return role;
	}

	public void setRole(bt_role role) {
		this.role = role;
	}
}
