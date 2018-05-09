package net.beyondtelecom.gopay.bt_core.service;

import net.beyondtelecom.gopay.bt_core.contract.base.EnumEntityData;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWallet;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWalletGroup;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCashoutAccount;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCurrency;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTFinancialInstitution;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTSystemUser;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.*;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_currency;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_financial_institution;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_enum_entity;
import org.modelmapper.ModelMapper;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface ConverterService {
	ModelMapper modelMapper();

	BTFinancialInstitution toDTO(bt_financial_institution btFinancialInstitution);

	BTCashoutAccount toDTO(bt_cashout_account btCashoutAccount);

	BTCurrency toDTO(bt_currency btCurrency);

	BTSystemUser toDTO(bt_user btUser);

	BTSystemUser toDTO(bt_auth_user btAuthUser);

    BTSystemUser toDTO(bt_session btSession);

	BTWalletGroup toDTO(bt_wallet_group walletGroup);

	BTWallet toDTO(bt_wallet wallet);

	EnumEntityData toDTO(bt_enum_entity enumEntity);
}
