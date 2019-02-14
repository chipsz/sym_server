package net.symbiosis.api.controller;

import net.symbiosis.api.exception.SymRestException;
import net.symbiosis.core.service.VoucherProcessor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.math.BigDecimal;

/***************************************************************************
 *                                                                         *
 * Created:     16 / 05 / 2018                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_JSON})
//@Api("XML Voucher API")
public class VoucherJSONController extends VoucherXMLController {
    @Autowired
    public VoucherJSONController(VoucherProcessor voucherProcessor) { super(voucherProcessor); }

    @Override
    @GET
    @Path("/serviceProvider/{serviceProviderId}")
    public Response getServiceProvider(@PathParam("serviceProviderId") Long serviceProviderId) throws SymRestException {
        return super.getServiceProvider(serviceProviderId);
    }

    @Override
    @GET @Path("/serviceProvider")
    public Response getServiceProviders() throws SymRestException { return super.getServiceProviders(); }

    @Override
    @GET @Path("/voucherProvider/{voucherProviderId}")
    public Response getVoucherProvider(@PathParam("voucherProviderId") Long voucherProviderId) throws SymRestException {
        return super.getVoucherProvider(voucherProviderId);
    }

    @Override
    @GET @Path("/voucherProvider")
    public Response getVoucherProviders() throws SymRestException { return super.getVoucherProviders(); }

    @Override
    @POST @Path("/voucherProvider/{voucherProviderId}/voucherImport")
    public Response loadVoucherProviderVouchers(@PathParam("voucherProviderId") Long voucherProviderId,
                                                @FormDataParam("fileUpload") InputStream uploadedInputStream,
                                                @FormDataParam("fileUpload") FormDataContentDisposition fileDetail,
                                                @FormParam("amount") BigDecimal amount)
            throws SymRestException {
        return super.loadVoucherProviderVouchers(voucherProviderId, uploadedInputStream, fileDetail, amount);
    }

    @Override
    @GET @Path("/voucherProvider/{voucherProviderId}/voucherImport")
    public Response getVoucherProviderVoucherImports(@PathParam("voucherProviderId") Long voucherProviderId)
            throws SymRestException {
        return super.getVoucherProviderVoucherImports(voucherProviderId);
    }

    @Override
    @POST
    @Path("/voucherProvider/{voucherProviderId}/payment")
    public Response loadVoucherProviderPayment(@PathParam("voucherProviderId") Long voucherProviderId,
                                               @FormParam("amount") BigDecimal amount) throws SymRestException {
        return super.loadVoucherProviderPayment(voucherProviderId, amount);
    }

    @Override
    @GET @Path("/voucher/{voucherId}")
    public Response getVoucher(@PathParam("voucherId") Long voucherId) throws SymRestException {
        return super.getVoucher(voucherId);
    }

    @Override
    @GET @Path("/voucher")
    public Response getVouchers() throws SymRestException { return super.getVouchers(); }

    @Override
    @GET @Path("/walletGroup")
    public Response getWalletGroups() throws SymRestException { return super.getWalletGroups(); }

    @Override
    @GET @Path("/walletGroup/{walletGroupId}/voucher")
    public Response getWalletGroupVouchers(@PathParam("walletGroupId") Long walletGroupId) throws SymRestException {
        return super.getWalletGroupVouchers(walletGroupId);
    }

    @Override
    @GET @Path("/voucherPurchase/{voucherPurchaseId}")
    public Response getVoucherPurchase(@PathParam("voucherPurchaseId") Long voucherPurchaseId) throws SymRestException {
        return super.getVoucherPurchase(voucherPurchaseId);
    }

}
