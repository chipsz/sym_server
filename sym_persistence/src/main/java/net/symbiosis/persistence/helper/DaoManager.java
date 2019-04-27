package net.symbiosis.persistence.helper;

import net.symbiosis.persistence.dao.EntityManagerRepo;
import net.symbiosis.persistence.dao.complex_type.SymAuthUserDao;
import net.symbiosis.persistence.dao.complex_type.SymConfigDao;
import net.symbiosis.persistence.dao.complex_type.SymGroupRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 11/14/13
 * Time: 8:00 PM
 */
@Component
public class DaoManager {
    @Autowired
    private EntityManagerRepo entityManagerRepo;
    @Autowired
    private SymConfigDao symConfigDao;
    @Autowired
    private SymAuthUserDao authUserDao;
    @Autowired
    private SymGroupRoleDao groupRoleDao;

    private static DaoManager daoManager = null;

    private DaoManager() {
        daoManager = this;
    }

    public static DaoManager getInstance() {
        if (daoManager == null) {
            daoManager = new DaoManager();
        }
        return daoManager;
    }

    public static SymAuthUserDao getAuthUserDao() {
        return getInstance().authUserDao;
    }

    public static SymConfigDao getSymConfigDao() {
        return getInstance().symConfigDao;
    }

    public static SymGroupRoleDao getUserGroupRoleDao() {
        return getInstance().groupRoleDao;
    }

    public static EntityManagerRepo getEntityManagerRepo() {
        return getInstance().entityManagerRepo;
    }
}
