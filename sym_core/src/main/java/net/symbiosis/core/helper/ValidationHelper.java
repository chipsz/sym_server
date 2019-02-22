package net.symbiosis.core.helper;

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_cashout_account;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.sym_deposit_type;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 * *
 * Created:     14 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class ValidationHelper {

    private static final Logger logger = Logger.getLogger(ValidationHelper.class.getSimpleName());

    public static SymResponseObject<sym_user> validateSystemUser(Long symUserId) {
        if (symUserId == null) {
            return new SymResponseObject<sym_user>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("symUserId cannot be null");
        }
        sym_user symUser = getEntityManagerRepo().findById(sym_user.class, symUserId);
        if (symUser == null) {
            return new SymResponseObject<sym_user>(INPUT_INVALID_REQUEST)
                    .setMessage(format("System User with id %d was not found", symUserId));
        }
        return new SymResponseObject<>(SUCCESS, symUser);
    }

    public static SymResponseObject<sym_auth_user> validateAuthUser(Long authUserId) {
        if (authUserId == null) {
            return new SymResponseObject<sym_auth_user>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("authUserId cannot be null");
        }
        sym_auth_user authUser = getEntityManagerRepo().findById(sym_auth_user.class, authUserId);
        if (authUser == null) {
            return new SymResponseObject<sym_auth_user>(INPUT_INVALID_REQUEST)
                    .setMessage(format("Auth User with id %d was not found", authUserId));
        }
        return new SymResponseObject<>(SUCCESS, authUser);
    }

    public static SymResponseObject<sym_voucher> validateVoucher(Long voucherId) {
        if (voucherId == null) {
            return new SymResponseObject<sym_voucher>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("voucherId cannot be null");
        }
        sym_voucher voucher = getEntityManagerRepo().findById(sym_voucher.class, voucherId);
        if (voucher == null) {
            return new SymResponseObject<sym_voucher>(INPUT_INVALID_REQUEST)
                    .setMessage(format("Voucher with id %d was not found", voucherId));
        }
        return new SymResponseObject<>(SUCCESS, voucher);
    }

    public static SymResponseObject<sym_wallet> validateWallet(Long walletId) {
        if (walletId == null) {
            return new SymResponseObject<sym_wallet>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("walletId cannot be null");
        }
        sym_wallet wallet = getEntityManagerRepo().findById(sym_wallet.class, walletId);
        if (wallet == null) {
            return new SymResponseObject<sym_wallet>(INPUT_INVALID_WALLET)
                    .setMessage(format("Wallet with id %d was not found", walletId));
        }

        return new SymResponseObject<>(SUCCESS, wallet);
    }

    public static SymResponseObject<sym_cashout_account> validateCashoutAccount(Long cashoutAccountId) {
        if (cashoutAccountId == null) {
            return new SymResponseObject<sym_cashout_account>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("cashoutAccountId cannot be null");
        }
        sym_cashout_account cashoutAccount = getEntityManagerRepo().findById(sym_cashout_account.class, cashoutAccountId);
        if (cashoutAccount == null) {
            return new SymResponseObject<sym_cashout_account>(INPUT_INVALID_REQUEST)
                    .setMessage(format("Cashout Account with id %d was not found", cashoutAccountId));
        }

        if (cashoutAccount.getIs_active()) {
            return new SymResponseObject<sym_cashout_account>(INPUT_INVALID_REQUEST)
                    .setMessage(format("Cashout Account with id %d is disabled", cashoutAccountId));
        }

        return new SymResponseObject<>(SUCCESS, cashoutAccount);
    }

    public static SymResponseObject<sym_deposit_type> validateDepositType(Long depositTypeId) {
        if (depositTypeId == null) {
            return new SymResponseObject<sym_deposit_type>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("depositTypeId cannot be null");
        }
        sym_deposit_type depositType = getEntityManagerRepo().findById(sym_deposit_type.class, depositTypeId);
        if (depositType == null) {
            return new SymResponseObject<sym_deposit_type>(INPUT_INVALID_WALLET)
                    .setMessage(format("Deposit type with id %d was not found", depositTypeId));
        }

        return new SymResponseObject<>(SUCCESS, depositType);
    }

    public static SymResponseObject<BigDecimal> validateAmount(BigDecimal amount) {

        if (amount == null) {
            return new SymResponseObject<BigDecimal>(INPUT_INCOMPLETE_REQUEST).setMessage("amount cannot be null");
        }

        if (amount.compareTo(new BigDecimal(0.0)) < 1) {
            return new SymResponseObject<BigDecimal>(INPUT_INVALID_AMOUNT).setMessage("Amount must be greater than 0");
        }

        return new SymResponseObject<>(SUCCESS, amount);
    }

    public static SymResponseObject<sym_voucher_provider> validateVoucherProvider(Long voucherProviderId) {
        if (voucherProviderId == null) {
            return new SymResponseObject<sym_voucher_provider>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("voucherProviderId cannot be null");
        }
        sym_voucher_provider voucherProvider = getEntityManagerRepo()
                .findById(sym_voucher_provider.class, voucherProviderId);

        if (voucherProvider == null) {
            return new SymResponseObject<sym_voucher_provider>(INPUT_INVALID_REQUEST)
                    .setMessage(format("Voucher Provider with id %s was not found", voucherProviderId));
        }

        return new SymResponseObject<>(SUCCESS, voucherProvider);
    }

}
