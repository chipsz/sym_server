package net.beyondtelecom.gopay.bt_core.implementation;

import net.beyondtelecom.gopay.bt_core.contract.base.EnumEntityData;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWallet;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWalletGroup;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCashoutAccount;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCurrency;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTFinancialInstitution;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTSystemUser;
import net.beyondtelecom.gopay.bt_core.service.ConverterService;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.*;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_currency;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_financial_institution;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class ConverterServiceImpl implements ConverterService {

	@Bean
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
	public BTFinancialInstitution toDTO(bt_financial_institution sourceData) {
		if (sourceData == null) { return null; }
		BTFinancialInstitution btFinancialInstitution = new BTFinancialInstitution();
		modelMapper().map(sourceData, btFinancialInstitution);
		btFinancialInstitution.setInstitutionId(sourceData.getId());
		btFinancialInstitution.setInstitutionName(sourceData.getName());
		btFinancialInstitution.setInstitutionShortName(sourceData.getShort_name());
		btFinancialInstitution.setInstitutionType(sourceData.getInstitution_type().getName());
		return btFinancialInstitution;
	}

    @Override
    public BTCashoutAccount toDTO(bt_cashout_account sourceData) {
        if (sourceData == null) { return null; }
        BTCashoutAccount btCashoutAccount = new BTCashoutAccount();
        modelMapper().map(sourceData, btCashoutAccount);
        btCashoutAccount.setCashoutAccountId(sourceData.getId());
        btCashoutAccount.setInstitutionId(sourceData.getCashout_institution().getId());
        btCashoutAccount.setAccountNickName(sourceData.getAccount_nick_name());
        return btCashoutAccount;
    }

    @Override
	public BTCurrency toDTO(bt_currency sourceData) {
		if (sourceData == null) { return null; }
        BTCurrency btCurrency = new BTCurrency();
		modelMapper().map(sourceData, btCurrency);
        btCurrency.setCurrencyId(sourceData.getId());
        btCurrency.setCurrencyName(sourceData.getName());
		return btCurrency;
	}

	@Override
	public BTSystemUser toDTO(bt_user btUser) {
		if (btUser == null) { return null; }
		BTSystemUser btSystemUser = new BTSystemUser();
		modelMapper().map(btUser, btSystemUser);
        btSystemUser.setUserId(btUser.getId());
        btSystemUser.setWalletId(btUser.getWallet().getId());
        btSystemUser.setCompanyName(btUser.getWallet().getCompany().getCompany_name());
        btSystemUser.setCountry(btUser.getCountry().getName());
        btSystemUser.setLanguage(btUser.getLanguage().getName());
        btSystemUser.setUserStatus(btUser.getUser_status().getName());
		return btSystemUser;
	}

	@Override
	public BTSystemUser toDTO(bt_auth_user sourceData) {
		if (sourceData == null) { return null; }
		BTSystemUser btSystemUser = toDTO(sourceData.getUser());
        btSystemUser.setGroup(sourceData.getAuth_group().getName());
		return btSystemUser;
	}

	@Override
	public BTSystemUser toDTO(bt_session sourceData) {
		if (sourceData == null) { return null; }
		BTSystemUser btSystemUser = toDTO(sourceData.getAuth_user());
        btSystemUser.setSessionId(sourceData.getId());
        btSystemUser.setUserId(sourceData.getAuth_user().getUser().getId());
		return btSystemUser;
	}

	@Override
	public BTWalletGroup toDTO(bt_wallet_group sourceData) {
		if (sourceData == null) { return null; }
		BTWalletGroup btWalletGroup = new BTWalletGroup();
		modelMapper().map(sourceData, btWalletGroup);
		btWalletGroup.setWalletGroupId(sourceData.getId());
		return btWalletGroup;
	}

	@Override
	public BTWallet toDTO(bt_wallet sourceData) {
		if (sourceData == null) { return null; }
		BTWallet btWallet = new BTWallet();
		modelMapper().map(sourceData, btWallet);
        btWallet.setWalletId(sourceData.getId());
        btWallet.setAccountAdminUserId(sourceData.getWallet_admin_user().getId());
        btWallet.setVoucherGroupId(sourceData.getWallet_group().getId());
		return btWallet;
	}

	@Override
	public EnumEntityData toDTO(bt_enum_entity sourceData) {
		if (sourceData == null) { return null; }
		EnumEntityData enumEntityData = new EnumEntityData();
		modelMapper().map(sourceData, enumEntityData);
		return enumEntityData;
	}


}
