package net.symbiosis.persistence.helper;

import net.symbiosis.core_lib.enumeration.*;
import net.symbiosis.persistence.entity.enumeration.*;

import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;

/***************************************************************************
 * *
 * Created:     18 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class SymEnumHelper {

    public static sym_country countryFromString(String name) {
        return findByName(sym_country.class, name);
    }

    public static sym_language languageFromString(String name) {
        return findByName(sym_language.class, name);
    }

    public static sym_voucher_type voucherTypeFromString(String name) {
        return findByName(sym_voucher_type.class, name);
    }

    public static sym_auth_group fromEnum(SymAuthGroup authGroup) {
        return findByName(sym_auth_group.class, authGroup.name());
    }

    public static sym_channel fromEnum(SymChannel channel) {
        return findByName(sym_channel.class, channel.name());
    }

    public static sym_distribution_channel fromEnum(SymDistributionChannel distributionChannel) {
        return findByName(sym_distribution_channel.class, distributionChannel.name());
    }

    public static sym_event_type fromEnum(SymEventType eventType) {
        return findByName(sym_event_type.class, eventType.name());
    }

    public static sym_financial_institution_type fromEnum(SymFinancialInstitutionType financialInstitutionType) {
        return findByName(sym_financial_institution_type.class, financialInstitutionType.name());
    }

    public static sym_response_code fromEnum(SymResponseCode responseCode) {
        return findByName(sym_response_code.class, responseCode.name());
    }

    public static sym_preference fromEnum(SymPreference preference) {
        return findByName(sym_preference.class, preference.name());
    }

    public static sym_voucher_status fromEnum(SymVoucherStatus voucherStatus) {
        return findByName(sym_voucher_status.class, voucherStatus.name());
    }

}
