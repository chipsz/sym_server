package net.symbiosis.core.service;

import net.symbiosis.core.contract.*;

import java.io.InputStream;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     24 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface VoucherProcessor extends RequestProcessor {

	SymServiceProviderList getServiceProvider(Long serviceProviderId);
	SymServiceProviderList getServiceProviders();
	SymVoucherProviderList getVoucherProvider(Long voucherProviderId);
	SymVoucherProviderList getVoucherProviders();
	SymImportBatchList loadVoucherProviderVouchers(Long voucherProviderId, InputStream uploadedInputStream,
                                                   String fileName, String contentType, BigDecimal amount);
	SymImportBatchList getVoucherProviderVoucherImports(Long voucherProviderId);
	SymVoucherList getVoucher(Long voucherId);
	SymVoucherList getVouchers();
	SymVoucherPurchaseList buyVoucher(Long voucherId, Long authUserId, BigDecimal voucherValue,
                                      String recipient, String cashierName);
    SymWalletGroupList getWalletGroups();
	SymWalletGroupVoucherList getWalletGroupVouchers(Long walletGroupId);
//	SymResponse loadMerchantPayment(Long merchantId, BigDecimal amount, Long depositTypeId,
//                                    String depositReference, Long paymentTime, String bankName, String bankReference,
//                                    String bankStatementId);
	SymResponse loadVoucherProviderPayment(Long voucherProviderId, BigDecimal amount);

    SymVoucherPurchaseList getVoucherPurchase(Long voucherPurchaseId);
//	SymWalletList createMerchant(Long systemUserId, Long walletGroupId, String email, String msisdn,
//                                   String username, String deviceId, String firstName, String lastName, String dateOfBirth);
}
