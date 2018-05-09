package net.symbiosis.core.service;

import net.symbiosis.core.contract.base.EnumEntityData;
import net.symbiosis.core.contract.symbiosis.*;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.*;
import net.symbiosis.persistence.entity.enumeration.sym_currency;
import net.symbiosis.persistence.entity.enumeration.sym_financial_institution;
import net.symbiosis.persistence.entity.super_class.sym_enum_entity;
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

    SymFinancialInstitution toDTO(sym_financial_institution symFinancialInstitution);

    SymCashoutAccount toDTO(sym_cashout_account symCashoutAccount);

    SymCurrency toDTO(sym_currency symCurrency);

    SymSystemUser toDTO(sym_user symUser);

    SymSystemUser toDTO(sym_auth_user symAuthUser);

    SymSystemUser toDTO(sym_session symSession);

    SymWalletGroup toDTO(sym_wallet_group walletGroup);

    SymWallet toDTO(sym_wallet wallet);

    EnumEntityData toDTO(sym_enum_entity enumEntity);
}
