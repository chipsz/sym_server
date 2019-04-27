package net.symbiosis.persistence.dao.implementation;

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.dao.complex_type.SymAuthUserDao;
import net.symbiosis.persistence.dao.super_class.AbstractDao;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.ACC_ACTIVE;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

/**
 * Created with IntelliJ IDEA.
 * sym_user: tkaviya
 * Date: 8/10/13
 * Time: 11:25 AM
 */

@Repository
@Transactional
public class SymAuthUserDaoImpl extends AbstractDao<sym_auth_user, Long> implements SymAuthUserDao {
    public SymAuthUserDaoImpl() {
        super(sym_auth_user.class);
    }

    public sym_auth_user findBySymUserFieldAndChannel(
            String fieldName, String value, sym_channel channel, boolean onlyActive) {

        if (channel == null) {
            return null;
        }

        List<Pair<String, ?>> criteria;

        if (!onlyActive) {
            criteria = asList(
                    new Pair<>("user." + fieldName, value),
                    new Pair<>("channel_id", channel.getId()));
        } else {
            criteria = asList(
                    new Pair<>("user." + fieldName, value),
                    new Pair<>("channel_id", channel.getId()),
                    new Pair<>("user.user_status", findByName(sym_response_code.class, ACC_ACTIVE.name()).getId()));
        }

        List<sym_auth_user> results = getEntityManagerRepo().findWhere(getEntityClass(), criteria);

        if (results.size() == 1) {
            return results.get(0);
        }
        return null;
    }

    public sym_auth_user findByUsernameAndChannel(String username, sym_channel channel, boolean onlyActive) {
        return findBySymUserFieldAndChannel("username", username, channel, onlyActive);
    }

    public sym_auth_user findByEmailAndChannel(String email, sym_channel channel, boolean onlyActive) {
        return findBySymUserFieldAndChannel("email", email, channel, onlyActive);
    }

    public sym_auth_user findByMsisdnAndChannel(String msisdn, sym_channel channel, boolean onlyActive) {
        return findBySymUserFieldAndChannel("msisdn", msisdn, channel, onlyActive);
    }

    public sym_auth_user findByUsernameAndChannel(String username, sym_channel channel) {
        return findByUsernameAndChannel(username, channel, false);
    }

    public sym_auth_user findByEmailAndChannel(String email, sym_channel channel) {
        return findByEmailAndChannel(email, channel, false);
    }

    public sym_auth_user findByMsisdnAndChannel(String msisdn, sym_channel channel) {
        return findByMsisdnAndChannel(msisdn, channel, false);
    }
}
