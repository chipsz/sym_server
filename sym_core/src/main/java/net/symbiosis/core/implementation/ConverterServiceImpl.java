package net.symbiosis.core.implementation;

import net.symbiosis.core.contract.base.EnumEntityData;
import net.symbiosis.core.contract.symbiosis.*;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.*;
import net.symbiosis.persistence.entity.enumeration.sym_currency;
import net.symbiosis.persistence.entity.enumeration.sym_financial_institution;
import net.symbiosis.persistence.entity.super_class.sym_enum_entity;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Service;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Service
public class ConverterServiceImpl implements ConverterService {

    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFullTypeMatchingRequired(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
        return mapper;
    }

    @Override
    public SymFinancialInstitution toDTO(sym_financial_institution sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymFinancialInstitution symFinancialInstitution = new SymFinancialInstitution();
        modelMapper().map(sourceData, symFinancialInstitution);
        symFinancialInstitution.setInstitutionId(sourceData.getId());
        symFinancialInstitution.setInstitutionName(sourceData.getName());
        symFinancialInstitution.setInstitutionShortName(sourceData.getShort_name());
        symFinancialInstitution.setInstitutionType(sourceData.getInstitution_type().getName());
        return symFinancialInstitution;
    }

    @Override
    public SymCashoutAccount toDTO(sym_cashout_account sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymCashoutAccount symCashoutAccount = new SymCashoutAccount();
        modelMapper().map(sourceData, symCashoutAccount);
        symCashoutAccount.setCashoutAccountId(sourceData.getId());
        symCashoutAccount.setInstitutionId(sourceData.getCashout_institution().getId());
        symCashoutAccount.setAccountNickName(sourceData.getAccount_nick_name());
        return symCashoutAccount;
    }

    @Override
    public SymCurrency toDTO(sym_currency sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymCurrency symCurrency = new SymCurrency();
        modelMapper().map(sourceData, symCurrency);
        symCurrency.setCurrencyId(sourceData.getId());
        symCurrency.setCurrencyName(sourceData.getName());
        return symCurrency;
    }

    @Override
    public SymSystemUser toDTO(sym_user symUser) {
        if (symUser == null) {
            return null;
        }
        SymSystemUser symSystemUser = new SymSystemUser();
        modelMapper().map(symUser, symSystemUser);
        symSystemUser.setUserId(symUser.getId());
        symSystemUser.setWalletId(symUser.getWallet().getId());
        symSystemUser.setCompanyName(symUser.getWallet().getCompany().getCompany_name());
        symSystemUser.setCountry(symUser.getCountry().getName());
        symSystemUser.setLanguage(symUser.getLanguage().getName());
        symSystemUser.setUserStatus(symUser.getUser_status().getName());
        return symSystemUser;
    }

    @Override
    public SymSystemUser toDTO(sym_auth_user sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymSystemUser symSystemUser = toDTO(sourceData.getUser());
        symSystemUser.setGroup(sourceData.getAuth_group().getName());
        return symSystemUser;
    }

    @Override
    public SymSystemUser toDTO(sym_session sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymSystemUser symSystemUser = toDTO(sourceData.getAuth_user());
        symSystemUser.setSessionId(sourceData.getId());
        symSystemUser.setUserId(sourceData.getAuth_user().getUser().getId());
        return symSystemUser;
    }

    @Override
    public SymWalletGroup toDTO(sym_wallet_group sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymWalletGroup symWalletGroup = new SymWalletGroup();
        modelMapper().map(sourceData, symWalletGroup);
        symWalletGroup.setWalletGroupId(sourceData.getId());
        return symWalletGroup;
    }

    @Override
    public SymWallet toDTO(sym_wallet sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymWallet symWallet = new SymWallet();
        modelMapper().map(sourceData, symWallet);
        symWallet.setWalletId(sourceData.getId());
        symWallet.setAccountAdminUserId(sourceData.getWallet_admin_user().getId());
        symWallet.setVoucherGroupId(sourceData.getWallet_group().getId());
        return symWallet;
    }

    @Override
    public EnumEntityData toDTO(sym_enum_entity sourceData) {
        if (sourceData == null) {
            return null;
        }
        EnumEntityData enumEntityData = new EnumEntityData();
        modelMapper().map(sourceData, enumEntityData);
        return enumEntityData;
    }


}
