package net.symbiosis.api.service;

import net.symbiosis.api.exception.SymRestException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.math.BigDecimal;

/***************************************************************************
 *                                                                         *
 * Created:     28 / 05 / 2018                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface VoucherRestService {

    Response getServiceProvider(Long serviceProviderId) throws SymRestException;
    Response getServiceProviders() throws SymRestException;
    Response getVoucherProvider(Long voucherProviderId) throws SymRestException;
    Response getVoucherProviders() throws SymRestException;
    Response loadVoucherProviderVouchers(Long voucherProviderId, InputStream uploadedInputStream,
                                         FormDataContentDisposition fileDetail, BigDecimal amount) throws SymRestException;
    Response getVoucherProviderVoucherImports(Long voucherProviderId)throws SymRestException;
    Response loadVoucherProviderPayment(Long voucherProviderId, BigDecimal amount) throws SymRestException;
    Response getVoucher(Long voucherId) throws SymRestException;
    Response getVouchers() throws SymRestException;
    Response getWalletGroups() throws SymRestException;
    Response getWalletGroupVouchers(Long walletGroupId) throws SymRestException;
    Response getVoucherPurchase(Long voucherPurchaseId) throws SymRestException;
}
