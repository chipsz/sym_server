package net.beyondtelecom.gopay.bt_persistence.dao.complex_type;

import net.beyondtelecom.gopay.bt_persistence.dao.super_class.BeyondTelecomEnumEntityDao;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_group_role;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;

import java.util.List;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */
public interface BTGroupRoleDao extends BeyondTelecomEnumEntityDao<bt_auth_group_role, Long> {
	List<bt_auth_group_role> findByGroup(bt_auth_group gopayGroup);
}
