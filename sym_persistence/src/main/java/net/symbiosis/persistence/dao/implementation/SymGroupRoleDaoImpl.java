package net.symbiosis.persistence.dao.implementation;

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.dao.complex_type.SymGroupRoleDao;
import net.symbiosis.persistence.entity.complex_type.sym_auth_group_role;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ***************************************************************************
 * *
 * Created:     20 / 09 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * ****************************************************************************
 */

@Repository
@Transactional
public class SymGroupRoleDaoImpl extends SymEnumEntityDaoImpl<sym_auth_group_role, Long> implements SymGroupRoleDao {

    protected SymGroupRoleDaoImpl() { super(sym_auth_group_role.class); }

    @Override
    public List<sym_auth_group_role> findByGroup(sym_auth_group symGroup) {
        return findWhere(new Pair<String, Object>("group_id", symGroup.getId()));
    }
}
