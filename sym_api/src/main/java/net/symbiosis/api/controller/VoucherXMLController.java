package net.symbiosis.api.controller;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.symbiosis.api.exception.SymRestException;
import net.symbiosis.api.service.VoucherRestService;
import net.symbiosis.core.service.VoucherProcessor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;

@Component
@Path("/xml/")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_XML})
//@Api("XML Voucher API")
public class VoucherXMLController implements VoucherRestService {

	private final VoucherProcessor voucherProcessor;

	@Context
    UriInfo uriInfo;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    public VoucherXMLController(VoucherProcessor voucherProcessor) {
        this.voucherProcessor = voucherProcessor;
    }

    @Override
	@GET @Path("/serviceProvider/{serviceProviderId}")
	public Response getServiceProvider(@PathParam("serviceProviderId") Long serviceProviderId) throws SymRestException {
		logger.info("Got request to get serviceProvider with id " + serviceProviderId);
		return Response.status(200).entity(voucherProcessor.getServiceProvider(serviceProviderId))
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/serviceProvider")
	public Response getServiceProviders() throws SymRestException {
		logger.info("Got request to get serviceProviders");
		return Response.status(200).entity(voucherProcessor.getServiceProviders())
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/voucherProvider/{voucherProviderId}")
	public Response getVoucherProvider(@PathParam("voucherProviderId") Long voucherProviderId) throws SymRestException {
		logger.info("Got request to get dataList with id " + voucherProviderId);
		return Response.status(200).entity(voucherProcessor.getVoucherProvider(voucherProviderId))
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/voucherProvider")
	public Response getVoucherProviders() throws SymRestException {
		logger.info("Got request to get voucherProviders");
		return Response.status(200).entity(voucherProcessor.getVoucherProviders())
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@POST @Path("/voucherProvider/{voucherProviderId}/voucherImport")
	public Response loadVoucherProviderVouchers(@PathParam("voucherProviderId") Long voucherProviderId,
                                                @FormDataParam("fileUpload") InputStream uploadedInputStream,
                                                @FormDataParam("fileUpload") FormDataContentDisposition fileDetail,
                                                @FormParam("amount") BigDecimal amount)
			throws SymRestException {
		logger.info(format("Got request to load vouchers for voucher provider %s", voucherProviderId));
		return Response.status(200).entity(voucherProcessor
			.loadVoucherProviderVouchers(voucherProviderId, uploadedInputStream,
                fileDetail.getFileName(), fileDetail.getType(), amount))
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/voucherProvider/{voucherProviderId}/voucherImport")
	public Response getVoucherProviderVoucherImports(@PathParam("voucherProviderId") Long voucherProviderId)
			throws SymRestException {
		logger.info(format("Got request to get voucher imports for voucher provider %s", voucherProviderId));
		return Response.status(200).entity(voucherProcessor.getVoucherProviderVoucherImports(voucherProviderId))
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@POST @Path("/voucherProvider/{voucherProviderId}/payment")
	public Response loadVoucherProviderPayment(@PathParam("voucherProviderId") Long voucherProviderId,
                                               @FormParam("amount") BigDecimal amount) throws SymRestException {
		logger.info(format("Got request to load funds for voucher Provider %s with amount %s", voucherProviderId, amount));
		return Response.status(200).entity(voucherProcessor.loadVoucherProviderPayment(voucherProviderId, amount)).header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/voucher/{voucherId}")
	public Response getVoucher(@PathParam("voucherId") Long voucherId) throws SymRestException {
		logger.info("Got request to get voucher with id " + voucherId);
		return Response.status(200).entity(voucherProcessor.getVoucher(voucherId))
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/voucher")
	public Response getVouchers() throws SymRestException {
		logger.info("Got request to get vouchers");
		return Response.status(200).entity(voucherProcessor.getVouchers())
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/walletGroup")
	public Response getWalletGroups() throws SymRestException {
		logger.info("Got request to get wallet group");
		return Response.status(200).entity(voucherProcessor.getWalletGroups())
                .header("Access-Control-Allow-Origin", "*").build();
	}

	@Override
	@GET @Path("/walletGroup/{walletGroupId}/voucher")
	public Response getWalletGroupVouchers(@PathParam("walletGroupId") Long walletGroupId) throws SymRestException {
		logger.info("Got request to get voucher group vouchers");
		return Response.status(200).entity(voucherProcessor.getWalletGroupVouchers(walletGroupId))
                .header("Access-Control-Allow-Origin", "*").build();
	}

    @Override
    @GET @Path("/voucherPurchase/{voucherPurchaseId}")
    public Response getVoucherPurchase(@PathParam("voucherPurchaseId") Long voucherPurchaseId) throws SymRestException {
        logger.info("Got request to get voucher purchase by voucherPurchaseId " + voucherPurchaseId);
        return Response.status(200).entity(voucherProcessor.getVoucherPurchase(voucherPurchaseId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

//	@Override
//	@POST @Path("/wallet")
//	public Response createWallet(@FormParam("accountAdminUserId") Long accountAdminUserId,
//								   @FormParam("walletGroupId") Long walletGroupId,
//								   @FormParam("email") String email,
//								   @FormParam("msisdn") String msisdn,
//								   @FormParam("username") String username,
//								   @FormParam("deviceId") String deviceId,
//								   @FormParam("firstName") String firstName,
//								   @FormParam("lastName") String lastName,
//								   @FormParam("dateOfBirth") String dateOfBirth) throws SymRestException {
//		logger.info("Got request to create wallet for user " + accountAdminUserId + " : " + username);
//		return Response.status(200).entity(voucherProcessor.createWallet(accountAdminUserId, walletGroupId,
//				email, msisdn, username, deviceId, firstName, lastName, dateOfBirth))
//                  .header("Access-Control-Allow-Origin", "*").build();
//	}

//	@Override
//	@POST @Path("/wallet/{walletId}/payment")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Response loadWalletPayment(@PathParam("walletId") Long walletId,
//								@FormParam("amount") BigDecimal amount,
//								@FormParam("depositTypeId") Long depositTypeId,
//								@FormParam("depositReference") String depositReference,
//								@FormParam("paymentTime") Long paymentTime,
//								@FormParam("bankName") String bankName,
//								@FormParam("bankReference") String bankReference,
//								@FormParam("bankStatementId") String bankStatementId) throws SymRestException {
//		logger.info(format("Got request to load wallet %s with %s", walletId, amount));
//		return Response.status(200).entity(voucherProcessor.loadWalletPayment(walletId, amount,
//				depositTypeId, depositReference, paymentTime, bankName, bankReference, bankStatementId))
//                  .header("Access-Control-Allow-Origin", "*").build();
//	}
}
