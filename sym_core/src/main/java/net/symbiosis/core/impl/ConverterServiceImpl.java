package net.symbiosis.core.impl;

import net.symbiosis.core.contract.base.EnumEntityData;
import net.symbiosis.core.contract.symbiosis.*;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.persistence.entity.complex_type.log.sym_import_batch;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.log.sym_voucher_purchase;
import net.symbiosis.persistence.entity.complex_type.log.sym_wallet_transaction;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_service_provider;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_cashout_account;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group_voucher_discount;
import net.symbiosis.persistence.entity.enumeration.sym_currency;
import net.symbiosis.persistence.entity.enumeration.sym_financial_institution;
import net.symbiosis.persistence.entity.super_class.sym_enum_entity;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Service;

import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_DEFAULT_COUNTRY_CODE;
import static net.symbiosis.core_lib.security.Security.decryptAES;
import static net.symbiosis.core_lib.utilities.CommonUtilities.format10DigitPhoneNumber;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

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
    public SymImportBatch toDTO(sym_import_batch sourceData) {
        if (sourceData == null) { return null; }
        SymImportBatch ettlImportBatch = new SymImportBatch();
        modelMapper().map(sourceData, ettlImportBatch);
        ettlImportBatch.setServiceProvider(sourceData.getVoucher().getService_provider().getName());
        ettlImportBatch.setVoucherProvider(sourceData.getVoucher().getVoucher_provider().getName());
        ettlImportBatch.setVoucherValue(sourceData.getVoucher().getVoucher_value());
        ettlImportBatch.setVoucherType(sourceData.getVoucher().getVoucher_type().getName());
        return ettlImportBatch;
    }

    @Override
    public SymSystemUser toDTO(sym_user symUser) {
        if (symUser == null) {
            return null;
        }
        SymSystemUser symSystemUser = new SymSystemUser();
        modelMapper().map(symUser, symSystemUser);
        symSystemUser.setUserId(symUser.getId());
        symSystemUser.setPhoneNumber(format10DigitPhoneNumber(symUser.getMsisdn(), getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)));
        symSystemUser.setWalletId(symUser.getWallet().getId());
        symSystemUser.setCompanyName(symUser.getWallet().getCompany().getCompany_name());
        symSystemUser.setWalletBalance(symUser.getWallet().getCurrent_balance().doubleValue());
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
        symSystemUser.setAuthUserId(sourceData.getId());
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
        symSystemUser.setAuthToken(sourceData.getAuth_token());
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
    public SymWalletTransaction toDTO(sym_wallet_transaction sourceData) {
        if (sourceData == null) {
            return null;
        }
        SymWalletTransaction symWalletTransaction = new SymWalletTransaction();
        modelMapper().map(sourceData, symWalletTransaction);
        symWalletTransaction.setWalletId(sourceData.getWallet().getId());
        symWalletTransaction.setEventType(sourceData.getEvent_type().getName());
        return symWalletTransaction;
    }

    @Override
    public SymServiceProvider toDTO(sym_service_provider sourceData) {
        if (sourceData == null) { return null; }
        SymServiceProvider serviceProvider = new SymServiceProvider();
        modelMapper().map(sourceData, serviceProvider);
        return serviceProvider;
    }

    @Override
    public SymVoucherProvider toDTO(sym_voucher_provider sourceData) {
        if (sourceData == null) { return null; }
        SymVoucherProvider voucherProvider = new SymVoucherProvider();
        modelMapper().map(sourceData, voucherProvider);
        return voucherProvider;
    }

    @Override
    public SymVoucherPurchase toDTO(sym_voucher_purchase sourceData) {
        if (sourceData == null) { return null; }
        SymVoucherPurchase voucherPurchase = new SymVoucherPurchase();
        modelMapper().map(sourceData, voucherPurchase);
        if (sourceData.getPinbased_voucher() != null) {
            voucherPurchase.setVoucherSerial(sourceData.getPinbased_voucher().getSerial_number());
            voucherPurchase.setVoucherPin(
                decryptAES(getProperty("AES128BitKey"), getProperty("AESInitializationVector"),
                    sourceData.getPinbased_voucher().getVoucher_pin()
                )
            );
        }
        voucherPurchase.setVoucherPurchaseId(sourceData.getId());
        voucherPurchase.setServiceProvider(sourceData.getVoucher().getService_provider().getName());
        voucherPurchase.setVoucherType(sourceData.getVoucher().getVoucher_type().getName());
        voucherPurchase.setUnits(sourceData.getVoucher().getUnits());
        return voucherPurchase;
    }

    @Override
    public SymVoucher toDTO(sym_voucher sourceData) {
        if (sourceData == null) { return null; }
        SymVoucher ettlVoucher = new SymVoucher();
        modelMapper().map(sourceData, ettlVoucher);
        ettlVoucher.setVoucherId(sourceData.getId());
        ettlVoucher.setVoucherType(sourceData.getVoucher_type().getName());
        ettlVoucher.setVoucherProvider(sourceData.getVoucher_provider().getName());
        ettlVoucher.setServiceProvider(sourceData.getService_provider().getName());
        return ettlVoucher;
    }

    @Override
    public SymWalletGroupVoucher toDTO(sym_wallet_group_voucher_discount sourceData) {
        if (sourceData == null) { return null; }
        SymWalletGroupVoucher ettlWalletGroupVoucher = new SymWalletGroupVoucher();
        modelMapper().map(sourceData, ettlWalletGroupVoucher);
        return ettlWalletGroupVoucher;
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
