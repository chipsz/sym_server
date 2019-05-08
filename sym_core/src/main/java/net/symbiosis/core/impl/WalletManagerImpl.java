package net.symbiosis.core.impl;

import net.symbiosis.core.service.WalletManager;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_wallet_transaction;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.utilities.CommonUtilities.isNullOrEmpty;

/***************************************************************************
 * Created:     23 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Service
public class WalletManagerImpl implements WalletManager {

    private static final Logger logger = Logger.getLogger(WalletManagerImpl.class.getSimpleName());

    @Override
    public synchronized SymResponseObject<sym_wallet> updateWalletBalance(sym_wallet_transaction walletTransactionDetails) {

        if (walletTransactionDetails.getId() != null) {
            logger.severe("Wallet balance update failed! Wallet transaction details already exist. Cannot update existing transaction.");
            return new SymResponseObject<sym_wallet>(INPUT_INVALID_REQUEST).setMessage("Cannot update existing transaction!");
        }
        if (walletTransactionDetails.getTransaction_amount() == null) {
            logger.severe("Wallet balance update failed! Invalid amount (null) specified.");
            return new SymResponseObject<sym_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid amount (null) specified!");
        }
        if (walletTransactionDetails.getEvent_type() == null) {
            logger.severe("Wallet balance update failed! Invalid event type (null) specified.");
            return new SymResponseObject<sym_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid event type (null) specified!");
        }
        if (walletTransactionDetails.getTransaction_link_reference() == null) {
            logger.severe("Wallet balance update failed! Invalid link reference (null) specified.");
            return new SymResponseObject<sym_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid link reference (null) specified!");
        }
        if (walletTransactionDetails.getTransaction_time() == null) {
            logger.severe("Wallet balance update failed! Invalid transaction time (null) specified.");
            return new SymResponseObject<sym_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid transaction time (null) specified!");
        }
        if (isNullOrEmpty(walletTransactionDetails.getTransaction_description())) {
            logger.severe("Wallet balance update failed! Transaction description not specified.");
            return new SymResponseObject<sym_wallet>(INPUT_INVALID_REQUEST).setMessage("Transaction description not specified!");
        }

        //make sure we are using an up to date balance
        sym_wallet wallet = walletTransactionDetails.getWallet().refresh();
        logger.info(format("Updating wallet id %s (current balance: %s) with amount %s",
                wallet.getId(), wallet.getCurrent_balance().doubleValue(), walletTransactionDetails.getTransaction_amount().doubleValue()));

        try {
            //check if new balance will be greater than or equal to 0
            if (wallet.getCurrent_balance().add(walletTransactionDetails.getTransaction_amount()).compareTo(new BigDecimal(0.0)) < 0) {
                logger.severe("Balance update not processed! Insufficient funds.");
                return new SymResponseObject<>(INSUFFICIENT_FUNDS, wallet);
            }

            wallet.setCurrent_balance(wallet.getCurrent_balance().add(walletTransactionDetails.getTransaction_amount())).save();

            logger.info(format("Balance updated successfully. New balance for wallet %s is %s",
                    wallet.getWallet_admin_user(), wallet.getCurrent_balance().toPlainString()));

            walletTransactionDetails.save();

            return new SymResponseObject<>(SUCCESS, wallet);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new SymResponseObject<>(GENERAL_ERROR, wallet);
        }
    }

    @Override
    public synchronized SymResponseObject<sym_wallet> transferWalletBalanceWithCharges(sym_wallet_transaction fromWalletDetails, sym_wallet_transaction toWalletDetails) {

        //debit fromWallet
        SymResponseObject<sym_wallet> updateResponse = updateWalletBalance(fromWalletDetails);
        if (!updateResponse.getResponseCode().equals(SUCCESS)) {
            String response = "Transfer not processed! " + updateResponse.getMessage();
            logger.severe(response);
            return new SymResponseObject<sym_wallet>(updateResponse.getResponseCode()).setMessage(response);
        }

        //credit toWallet
        updateResponse = updateWalletBalance(toWalletDetails);

        if (!updateResponse.getResponseCode().equals(SUCCESS)) {
            String response = "Transfer not processed! " + updateResponse.getMessage();
            logger.severe(response);
            //reverse amount to initial wallet
            sym_wallet_transaction reversalTransaction = new sym_wallet_transaction(
                fromWalletDetails.getWallet(), fromWalletDetails.getEvent_type(),
                fromWalletDetails.getTransaction_amount().multiply(new BigDecimal(-1)),
                "Wallet transfer reversed! " + updateResponse.getMessage(), fromWalletDetails.getTransaction_link_reference(),
                fromWalletDetails.getTransaction_time()
            );
            updateWalletBalance(reversalTransaction);
            return new SymResponseObject<sym_wallet>(updateResponse.getResponseCode()).setMessage(response);
        }

        return updateResponse;
    }

    static synchronized SymResponseObject<sym_voucher_provider> updateVoucherProviderBalance(
            sym_voucher_provider voucherProvider, BigDecimal amount) {

        if (voucherProvider == null || amount == null) {
            return new SymResponseObject<>(INPUT_INCOMPLETE_REQUEST);
        }

        logger.info(format("Updating voucherProvider %s balance (%s) with amount %s",
                voucherProvider.getName(), voucherProvider.getCurrent_balance().toPlainString(), amount.toPlainString()));

        try {
            //make sure we are using an up to date balance
            voucherProvider.refresh();
            //check if new balance will be greater than or equal to 0
            if (voucherProvider.getCurrent_balance().add(amount).compareTo(new BigDecimal(0.0)) < 0) {
                return new SymResponseObject<>(INSUFFICIENT_FUNDS, voucherProvider);
            }

            voucherProvider.setCurrent_balance(voucherProvider.getCurrent_balance().add(amount)).save();

            logger.info(format("New balance for voucherProvider %s is %s", voucherProvider.getName(),
                    voucherProvider.getCurrent_balance().toPlainString()));

            return new SymResponseObject<>(SUCCESS, voucherProvider);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new SymResponseObject<>(GENERAL_ERROR, voucherProvider);
        }

    }

}
