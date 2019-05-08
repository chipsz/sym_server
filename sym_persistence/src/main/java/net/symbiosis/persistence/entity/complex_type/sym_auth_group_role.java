package net.symbiosis.persistence.entity.complex_type;

import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_role;
import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.*;

/**
 * Created with Intelli_j IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
@AttributeOverride(name = "id", column = @Column(name = "auth_group_role_id"))
@Cacheable(false)
public class sym_auth_group_role extends sym_enum_entity<sym_auth_group_role> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_group_id")
    private sym_auth_group auth_group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private sym_role role;

    public sym_auth_group_role() {}

    public sym_auth_group_role(String name, Boolean enabled, sym_auth_group auth_group, sym_role role) {
        super(name, enabled);
        this.auth_group = auth_group;
        this.role = role;
    }

    public sym_auth_group getAuth_group() {
        return auth_group;
    }

    public void setAuth_group(sym_auth_group auth_group) {
        this.auth_group = auth_group;
    }

    public sym_role getRole() {
        return role;
    }

    public void setRole(sym_role role) {
        this.role = role;
    }
}
