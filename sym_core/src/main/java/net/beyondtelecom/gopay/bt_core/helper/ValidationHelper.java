package net.beyondtelecom.gopay.bt_core.helper;

import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_cashout_account;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_wallet;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_deposit_type;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 * *
 * Created:     14 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class ValidationHelper {

    private static final Logger logger = Logger.getLogger(ValidationHelper.class.getSimpleName());

    public static BTResponseObject<bt_user> validateSystemUser(Long gopayUserId) {
        if (gopayUserId == null) {
            return new BTResponseObject<bt_user>(INPUT_INCOMPLETE_REQUEST)
                .setMessage("gopayUserId cannot be null");
        }
        bt_user gopayUser = getEntityManagerRepo().findById(bt_user.class, gopayUserId);
        if (gopayUser == null) {
            return new BTResponseObject<bt_user>(INPUT_INVALID_REQUEST)
                .setMessage(format("System User with id %d was not found", gopayUserId));
        }
        return new BTResponseObject<>(SUCCESS, gopayUser);
    }

    public static BTResponseObject<bt_auth_user> validateAuthUser(Long authUserId) {
        if (authUserId == null) {
            return new BTResponseObject<bt_auth_user>(INPUT_INCOMPLETE_REQUEST)
                .setMessage("authUserId cannot be null");
        }
        bt_auth_user authUser = getEntityManagerRepo().findById(bt_auth_user.class, authUserId);
        if (authUser == null) {
            return new BTResponseObject<bt_auth_user>(INPUT_INVALID_REQUEST)
                .setMessage(format("Auth User with id %d was not found", authUserId));
        }
        return new BTResponseObject<>(SUCCESS, authUser);
    }

    public static BTResponseObject<bt_wallet> validateWallet(Long walletId) {
        if (walletId == null) {
            return new BTResponseObject<bt_wallet>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("walletId cannot be null");
        }
        bt_wallet wallet = getEntityManagerRepo().findById(bt_wallet.class, walletId);
        if (wallet == null) {
            return new BTResponseObject<bt_wallet>(INPUT_INVALID_WALLET)
                    .setMessage(format("Wallet with id %d was not found", walletId));
        }

        return new BTResponseObject<>(SUCCESS, wallet);
    }

    public static BTResponseObject<bt_cashout_account> validateCashoutAccount(Long cashoutAccountId) {
        if (cashoutAccountId == null) {
            return new BTResponseObject<bt_cashout_account>(INPUT_INCOMPLETE_REQUEST)
                .setMessage("cashoutAccountId cannot be null");
        }
        bt_cashout_account cashoutAccount = getEntityManagerRepo().findById(bt_cashout_account.class, cashoutAccountId);
        if (cashoutAccount == null) {
            return new BTResponseObject<bt_cashout_account>(INPUT_INVALID_REQUEST)
                .setMessage(format("Cashout Account with id %d was not found", cashoutAccountId));
        }

        return new BTResponseObject<>(SUCCESS, cashoutAccount);
    }

    public static BTResponseObject<bt_deposit_type> validateDepositType(Long depositTypeId) {
        if (depositTypeId == null) {
            return new BTResponseObject<bt_deposit_type>(INPUT_INCOMPLETE_REQUEST)
                    .setMessage("depositTypeId cannot be null");
        }
        bt_deposit_type depositType = getEntityManagerRepo().findById(bt_deposit_type.class, depositTypeId);
        if (depositType == null) {
            return new BTResponseObject<bt_deposit_type>(INPUT_INVALID_WALLET)
                    .setMessage(format("Deposit type with id %d was not found", depositTypeId));
        }

        return new BTResponseObject<>(SUCCESS, depositType);
    }

    public static BTResponseObject<BigDecimal> validateAmount(BigDecimal amount) {

        if (amount == null) {
            return new BTResponseObject<BigDecimal>(INPUT_INCOMPLETE_REQUEST).setMessage("amount cannot be null");
        }

        if (amount.compareTo(new BigDecimal(0.0)) < 1) {
            return new BTResponseObject<BigDecimal>(INPUT_INVALID_AMOUNT).setMessage("Amount must be greater than 0");
        }

        return new BTResponseObject<>(SUCCESS, amount);
    }
}
