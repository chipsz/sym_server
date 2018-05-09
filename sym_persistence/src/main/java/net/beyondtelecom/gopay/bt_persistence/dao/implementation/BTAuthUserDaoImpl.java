package net.beyondtelecom.gopay.bt_persistence.dao.implementation;

import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.dao.complex_type.BTAuthUserDao;
import net.beyondtelecom.gopay.bt_persistence.dao.super_class.AbstractDao;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.ACC_ACTIVE;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;
/**
* Created with IntelliJ IDEA.
* bt_user: tkaviya
* Date: 8/10/13
* Time: 11:25 AM
*/

@Repository
@Transactional
public class BTAuthUserDaoImpl extends AbstractDao<bt_auth_user, Long> implements BTAuthUserDao
{
	public BTAuthUserDaoImpl() { super(bt_auth_user.class); }

	public bt_auth_user findByBeyondTelecomUserFieldAndChannel(
			String fieldName, String value, bt_channel channel, boolean onlyActive) {

		if (channel == null) { return null; }

        List<Pair<String, ?>> criteria;

		if (!onlyActive)
        {
            criteria = asList(
                    new Pair<>("user." + fieldName, value),
                    new Pair<>("channel_id", channel.getId()));
        } else {
            criteria = asList(
                new Pair<>("user." + fieldName, value),
                new Pair<>("channel_id", channel.getId()),
                new Pair<>("user.user_status", findByName(bt_response_code.class, ACC_ACTIVE.name()).getId()));
        }

		List<bt_auth_user> results = getEntityManagerRepo().findWhere(getEntityClass(), criteria);

		if (results.size() == 1) {
			return results.get(0);
		}
		return null;
	}

    public bt_auth_user findByUsernameAndChannel(String username, bt_channel channel, boolean onlyActive) {
		return findByBeyondTelecomUserFieldAndChannel("username", username, channel, onlyActive);
	}

	public bt_auth_user findByEmailAndChannel(String email, bt_channel channel, boolean onlyActive) {
		return findByBeyondTelecomUserFieldAndChannel("email", email, channel, onlyActive);
	}

    public bt_auth_user findByMsisdnAndChannel(String msisdn, bt_channel channel, boolean onlyActive) {
		return findByBeyondTelecomUserFieldAndChannel("msisdn", msisdn, channel, onlyActive);
	}

    public bt_auth_user findByUsernameAndChannel(String username, bt_channel channel) {
	    return findByUsernameAndChannel(username, channel, false);
	}

    public bt_auth_user findByEmailAndChannel(String email, bt_channel channel) {
	    return findByEmailAndChannel(email, channel, false);
	}

    public bt_auth_user findByMsisdnAndChannel(String msisdn, bt_channel channel) {
	    return findByMsisdnAndChannel(msisdn, channel, false);
	}
}
