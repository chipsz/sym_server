package net.symbiosis.core.service;

import net.symbiosis.core.contract.base.EnumEntityData;
import net.symbiosis.core.contract.symbiosis.*;
import net.symbiosis.persistence.entity.complex_type.log.sym_import_batch;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_service_provider;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_purchase;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_cashout_account;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group_voucher_discount;
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

    EnumEntityData toDTO(sym_enum_entity enumEntity);

    SymFinancialInstitution toDTO(sym_financial_institution symFinancialInstitution);

    SymCashoutAccount toDTO(sym_cashout_account symCashoutAccount);

    SymCurrency toDTO(sym_currency symCurrency);

    SymImportBatch toDTO(sym_import_batch importBatch);

    SymSystemUser toDTO(sym_user symUser);

    SymSystemUser toDTO(sym_auth_user symAuthUser);

    SymSystemUser toDTO(sym_session symSession);

    SymWalletGroup toDTO(sym_wallet_group walletGroup);

    SymWallet toDTO(sym_wallet wallet);

    SymServiceProvider toDTO(sym_service_provider serviceProvider);

    SymVoucherProvider toDTO(sym_voucher_provider voucherProvider);

    SymVoucherPurchase toDTO(sym_voucher_purchase voucherPurchase);

    SymVoucher toDTO(sym_voucher voucher);

    SymWalletGroupVoucher toDTO(sym_wallet_group_voucher_discount walletGroupVoucher);

}
