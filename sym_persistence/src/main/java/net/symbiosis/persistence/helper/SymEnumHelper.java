package net.symbiosis.persistence.helper;

import net.symbiosis.core_lib.enumeration.*;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.enumeration.*;

import java.util.List;

import static java.util.Arrays.asList;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

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

    public static Pair<Long, String> getMappedResponseMessage(String systemId, Long responseCode) {
        List<sym_response_mapping> mappedResponseList = getEntityManagerRepo().findWhere(sym_response_mapping.class, asList(
            new Pair<>("system_id", systemId), new Pair<>("response_code_id", responseCode)
        ));

        if (mappedResponseList != null && mappedResponseList.size() == 1) {
            sym_response_mapping mappedResponse = mappedResponseList.get(0);
            return new Pair<>(mappedResponse.getResponse_code_id(), mappedResponse.getMapped_response_message());
        } else {
            return new Pair<>((long)SymResponseCode.GENERAL_ERROR.code, SymResponseCode.GENERAL_ERROR.message);
        }
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
