package net.symbiosis.persistence.dao.complex_type;

import net.symbiosis.persistence.dao.super_class.SymbisoisDaoInterface;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */

public interface SymAuthUserDao extends SymbisoisDaoInterface<sym_auth_user, Long> {

    sym_auth_user findBySymUserFieldAndChannel(String fieldName, String value, sym_channel channel, boolean onlyActive);

    sym_auth_user findByUsernameAndChannel(String username, sym_channel channel, boolean onlyActive);

    sym_auth_user findByEmailAndChannel(String email, sym_channel channel, boolean onlyActive);

    sym_auth_user findByMsisdnAndChannel(String msisdn, sym_channel channel, boolean onlyActive);

    sym_auth_user findByUsernameAndChannel(String username, sym_channel channel);

    sym_auth_user findByEmailAndChannel(String email, sym_channel channel);

    sym_auth_user findByMsisdnAndChannel(String msisdn, sym_channel channel);
}
