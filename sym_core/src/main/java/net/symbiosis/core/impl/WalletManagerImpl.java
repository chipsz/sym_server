package net.symbiosis.core.impl;

import net.symbiosis.core.service.WalletManager;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_transfer_charge;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_SYSTEM_NAME;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

/***************************************************************************
 * Created:     23 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Service
public class WalletManagerImpl implements WalletManager {

    private static final Logger logger = Logger.getLogger(WalletManagerImpl.class.getSimpleName());

    @Override
    public synchronized SymResponseObject<sym_wallet> updateWalletBalance(sym_wallet wallet, BigDecimal amount) {

        if (wallet == null) {
            logger.severe("Balance update not processed! Invalid wallet(null) specified.");
            return new SymResponseObject<>(INPUT_INCOMPLETE_REQUEST);
        }

        if (amount == null) {
            logger.severe("Balance update not processed! Invalid amount(null) specified.");
            return new SymResponseObject<>(INPUT_INCOMPLETE_REQUEST, wallet);
        }

        //make sure we are using an up to date balance
        wallet.refresh();
        logger.info(format("Updating wallet id %s (current balance: %s) with amount %s",
                wallet.getId(), wallet.getCurrent_balance().doubleValue(), amount.toPlainString()));

        try {
            //check if new balance will be greater than or equal to 0
            if (wallet.getCurrent_balance().add(amount).compareTo(new BigDecimal(0.0)) < 0) {
                logger.severe("Balance update not processed! Insufficient funds.");
                return new SymResponseObject<>(INSUFFICIENT_FUNDS, wallet);
            }

            wallet.setCurrent_balance(wallet.getCurrent_balance().add(amount)).save();

            logger.info(format("Balance updated successfully. New balance for wallet %s is %s",
                    wallet.getWallet_admin_user(), wallet.getCurrent_balance().toPlainString()));

            return new SymResponseObject<>(SUCCESS, wallet);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new SymResponseObject<>(GENERAL_ERROR, wallet);
        }
    }

    @Override
    public synchronized SymResponseObject<sym_wallet> transferWalletBalance(sym_wallet fromWallet, sym_wallet toWallet, BigDecimal amount) {

        //debit fromWallet
        SymResponseObject<sym_wallet> updateResponse = updateWalletBalance(fromWallet,amount.multiply(new BigDecimal(-1)));
        if (!updateResponse.getResponseCode().equals(SUCCESS)) {
            String response = "Transfer not processed! " + updateResponse.getMessage();
            logger.severe(response);
            return new SymResponseObject<sym_wallet>(updateResponse.getResponseCode()).setMessage(response);
        }

        //credit toWallet
        updateResponse = updateWalletBalance(toWallet,amount);
        if (!updateResponse.getResponseCode().equals(SUCCESS)) {
            String response = "Transfer not processed! " + updateResponse.getMessage();
            logger.severe(response);
            return new SymResponseObject<sym_wallet>(updateResponse.getResponseCode()).setMessage(response);
        }

        return updateResponse;
    }

    @Override
    public synchronized SymResponseObject<sym_wallet> transferWalletBalanceWithCharges(sym_wallet fromWallet, sym_wallet toWallet, BigDecimal amount) {


        //calculate voucher amounts based on discount
        List<sym_wallet_transfer_charge> walletTransferCharges = getEntityManagerRepo().findWhere(
                sym_wallet_transfer_charge.class, asList(
                    new Pair<>("wallet_group_id", fromWallet.getWallet_group().getId()),
                    new Pair<>("starting_value <", amount.doubleValue()),
                    new Pair<>("ending_value >", amount.doubleValue())
                ));

        if (walletTransferCharges == null || walletTransferCharges.size() != 1) {
            String response = format("Charge not defined for group %s, amount %s", fromWallet.getWallet_group().getId(), amount);
            logger.severe(response);
            sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME),"Charge not defined", response);
            return new SymResponseObject<>(GENERAL_ERROR);
        }

        BigDecimal walletCost = amount.add(amount.multiply(new BigDecimal(walletTransferCharges.get(0).getWallet_charge()/100.0)));


        //debit fromWallet
        SymResponseObject<sym_wallet> updateResponse = updateWalletBalance(fromWallet,walletCost.multiply(new BigDecimal(-1)));
        if (!updateResponse.getResponseCode().equals(SUCCESS)) {
            String response = "Transfer not processed! " + updateResponse.getMessage();
            logger.severe(response);
            return new SymResponseObject<sym_wallet>(updateResponse.getResponseCode()).setMessage(response);
        }

        //credit toWallet
        updateResponse = updateWalletBalance(toWallet,amount);
        if (!updateResponse.getResponseCode().equals(SUCCESS)) {
            String response = "Transfer not processed! " + updateResponse.getMessage();
            logger.severe(response);
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
