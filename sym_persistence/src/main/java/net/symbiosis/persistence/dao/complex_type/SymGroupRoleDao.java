package net.symbiosis.persistence.dao.complex_type;

import net.symbiosis.persistence.dao.super_class.SymbisoisEnumEntityDao;
import net.symbiosis.persistence.entity.complex_type.sym_auth_group_role;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;

import java.util.List;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */
public interface SymGroupRoleDao extends SymbisoisEnumEntityDao<sym_auth_group_role, Long> {
    List<sym_auth_group_role> findByGroup(sym_auth_group symGroup);
}
