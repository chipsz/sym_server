package net.beyondtelecom.gopay.bt_persistence.helper;

import net.beyondtelecom.gopay.bt_persistence.dao.EntityManagerRepo;
import net.beyondtelecom.gopay.bt_persistence.dao.complex_type.BTAuthUserDao;
import net.beyondtelecom.gopay.bt_persistence.dao.complex_type.BTGroupRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 11/14/13
 * Time: 8:00 PM
 */
@Component
public class DaoManager
{
	@Autowired private EntityManagerRepo entityManagerRepo;
	@Autowired private BTAuthUserDao gopayAuthUserDao;
    @Autowired private BTGroupRoleDao gopayGroupRoleDao;

	private static DaoManager daoManager = null;

	private DaoManager() { daoManager = this; }

	public static DaoManager getInstance()
	{
		if (daoManager == null) { daoManager = new DaoManager(); }
		return daoManager;
	}

	public static BTAuthUserDao getAuthUserDao() { return getInstance().gopayAuthUserDao; }
    public static BTGroupRoleDao getUserGroupRoleDao() { return getInstance().gopayGroupRoleDao; }
	public static EntityManagerRepo getEntityManagerRepo() { return getInstance().entityManagerRepo; }
}
