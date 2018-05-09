package net.beyondtelecom.gopay.bt_persistence.dao.complex_type;

import net.beyondtelecom.gopay.bt_persistence.dao.super_class.BeyondTelecomDaoInterface;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */

public interface BTAuthUserDao extends BeyondTelecomDaoInterface<bt_auth_user, Long> {

	bt_auth_user findByBeyondTelecomUserFieldAndChannel(String fieldName, String value, bt_channel channel, boolean onlyActive);

    bt_auth_user findByUsernameAndChannel(String username, bt_channel channel, boolean onlyActive);
    bt_auth_user findByEmailAndChannel(String email, bt_channel channel, boolean onlyActive);
    bt_auth_user findByMsisdnAndChannel(String msisdn, bt_channel channel, boolean onlyActive);

    bt_auth_user findByUsernameAndChannel(String username, bt_channel channel);
    bt_auth_user findByEmailAndChannel(String email, bt_channel channel);
    bt_auth_user findByMsisdnAndChannel(String msisdn, bt_channel channel);
}
