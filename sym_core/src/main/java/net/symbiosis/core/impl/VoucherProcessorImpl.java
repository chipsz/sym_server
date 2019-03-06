package net.symbiosis.core.impl;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.common.utilities.FileUtils;
import net.symbiosis.core.contract.*;
import net.symbiosis.core.contract.symbiosis.*;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.core.service.VoucherProcessor;
import net.symbiosis.core.service.WalletManager;
import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.utilities.IOUtils;
import net.symbiosis.core_lib.utilities.SymValidator;
import net.symbiosis.persistence.entity.complex_type.log.sym_import_batch;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.voucher.*;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.enumeration.sym_voucher_status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.common.security.Security.encryptAES;
import static net.symbiosis.core.helper.UploadFileHelper.*;
import static net.symbiosis.core.helper.ValidationHelper.*;
import static net.symbiosis.core.impl.WalletManagerImpl.updateVoucherProviderBalance;
import static net.symbiosis.core_lib.enumeration.SymDistributionChannel.RECEIPT;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.enumeration.SymVoucherStatus.*;
import static net.symbiosis.core_lib.utilities.CommonUtilities.getRealThrowable;
import static net.symbiosis.core_lib.utilities.IOUtils.readArrayFromFile;
import static net.symbiosis.core_lib.utilities.IOUtils.writeToFile;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *																		   *
 * Created:	 20 / 10 / 2016												   *
 * Author:	  Tsungai Kaviya											   *
 * Contact:	 tsungai.kaviya@gmail.com									   *
 * 																		   *
 ***************************************************************************/

@Service
public class VoucherProcessorImpl implements VoucherProcessor {

    private final ConverterService converterService;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    private final WalletManager walletManager;

    @Autowired
    public VoucherProcessorImpl(ConverterService converterService, WalletManager walletManager) {
        this.converterService = converterService;
        this.walletManager = walletManager;
    }

    @Override
	public SymServiceProviderList getServiceProvider(Long serviceProviderId) {
		logger.info(format("Getting service provider with Id %s", serviceProviderId));
		sym_service_provider serviceProvider = getEntityManagerRepo().findById(sym_service_provider.class, serviceProviderId);
		if (serviceProvider == null) { return new SymServiceProviderList(DATA_NOT_FOUND); }
		return new SymServiceProviderList(SUCCESS, converterService.toDTO(serviceProvider));
	}

	@Override
	public SymServiceProviderList getServiceProviders() {
		logger.info("Getting service provider list");
		ArrayList<SymServiceProvider> serviceProviders = new ArrayList<>();
		getEntityManagerRepo().findAll(sym_service_provider.class).forEach(sp -> serviceProviders.add(converterService.toDTO(sp)));
		logger.info(format("Returning %s voucher providers", serviceProviders.size()));
		return new SymServiceProviderList(SUCCESS, serviceProviders);
	}

	@Override
	public SymVoucherProviderList getVoucherProvider(Long voucherProviderId) {
		logger.info(format("Getting voucher provider with Id %s", voucherProviderId));
		sym_voucher_provider voucherProvider = getEntityManagerRepo().findById(sym_voucher_provider.class, voucherProviderId);
		if (voucherProvider == null) { return new SymVoucherProviderList(DATA_NOT_FOUND); }
		return new SymVoucherProviderList(SUCCESS, converterService.toDTO(voucherProvider));
	}

	@Override
	public SymVoucherProviderList getVoucherProviders() {
		logger.info("Getting voucher provider list");
		ArrayList<SymVoucherProvider> voucherProviders = new ArrayList<>();
		getEntityManagerRepo().findAll(sym_voucher_provider.class).forEach(vp -> voucherProviders.add(converterService.toDTO(vp)));
		logger.info(format("Returning %s voucher providers", voucherProviders.size()));
		return new SymVoucherProviderList(SUCCESS, voucherProviders);
	}

	@Override
	public SymImportBatchList loadVoucherProviderVouchers(Long voucherProviderId, InputStream uploadedInputStream,
                                                          String fileName, String contentType, BigDecimal manualAmount) {
		logger.info(format("Loading vouchers for voucher provider %s", voucherProviderId));

		if (fileName == null || contentType == null || uploadedInputStream == null) {
		    return new SymImportBatchList(INPUT_INCOMPLETE_REQUEST)
                .setResponse("Upload file (fileUpload) not specified.");
        }

        List<sym_import_batch> existingImport = getEntityManagerRepo().findWhere(
            sym_import_batch.class, new ArrayList<>(asList(
                    new Pair<>("filename", fileName),
                    new Pair<>("filename", fileName + ".dec")
                )
            ), false, false, true, false
        );

		if (existingImport != null && existingImport.size() >= 1) {
            return new SymImportBatchList(EXISTING_DATA_FOUND)
                .setResponse("File with name " + fileName + " has already been uploaded");
        }

		String uploadedFileLocation = IOUtils.getOSDefaultTempDir() + fileName;
		logger.info(format("Downloading file to %s", uploadedFileLocation));

		File voucherPinFile; ArrayList<String> fileContents;
        try {
            //save uploaded file to disk
            voucherPinFile = writeToFile(uploadedInputStream, uploadedFileLocation);
		}
		catch (Exception e) { e.printStackTrace(); return new SymImportBatchList(GENERAL_ERROR); }

		List<sym_pin_import_config> pinImportConfigs = getEntityManagerRepo()
			.findWhere(sym_pin_import_config.class, new Pair<>("voucher_provider_id", voucherProviderId));

		sym_pin_import_config pinImportConfig = null;

		if (pinImportConfigs.size() == 1) {
			pinImportConfig = pinImportConfigs.get(0);
		} else if (pinImportConfigs.size() == 0) {
			logger.info(format("No configuration for the specified voucher provider %s", voucherProviderId));
			return new SymImportBatchList(NOT_SUPPORTED);
		} else {
			//if there are multiple configs for a voucher provider, try to match using a file name
			for (sym_pin_import_config config : pinImportConfigs) {
				if (config.getFilename_regex() != null && voucherPinFile.getName().matches(config.getFilename_regex())) {
					pinImportConfig = config;
				}
			}
			if (pinImportConfig == null) {
				logger.info(format("No configuration for the uploaded file %s", voucherPinFile.getName()));
				return new SymImportBatchList(NOT_SUPPORTED);
			}
		}
        String mimeType = FileUtils.getMimeType(voucherPinFile);
        if (mimeType != null && (mimeType.equals("application/pgp") || mimeType.equals("application/pgp-encrypted"))) {
            logger.info(format("File %s needs to be PGP decrypted", voucherPinFile.getName()));
		    SymResponseObject<File> decryptResponse = decryptPGPFile(voucherPinFile.getAbsolutePath(), pinImportConfig);
            if (!decryptResponse.getResponseCode().equals(SUCCESS)) {
                return new SymImportBatchList(decryptResponse.getResponseCode()).setResponse(decryptResponse.getMessage());
            }
            voucherPinFile = decryptResponse.getResponseObject();
        } else if (mimeType != null && !mimeType.equals("text/plain")) {
            return new SymImportBatchList(NOT_SUPPORTED).setResponse(format("ContentType %s is not supported", contentType));
        }

        try { fileContents = readArrayFromFile(voucherPinFile.getAbsolutePath()); }
        catch (Exception e) { e.printStackTrace(); return new SymImportBatchList(GENERAL_ERROR); }

        double amount;

		if (manualAmount == null) {
            SymResponseObject<Double> amountResponse = getAmountUsingConfig(pinImportConfig, voucherPinFile.getName(), fileContents);
            if (!amountResponse.getResponseCode().equals(SUCCESS)) { return new SymImportBatchList(amountResponse.getResponseCode()); }
            amount = amountResponse.getResponseObject() / pinImportConfig.getDivide_amount_by();
        } else {
		    amount = manualAmount.doubleValue();
        }

		SymResponseObject<sym_voucher> uploadedVoucher = getEntityManagerRepo().findUniqueWhere(sym_voucher.class,
			asList(
				new Pair<>("voucher_value", amount),
				new Pair<>("service_provider_id", pinImportConfig.getService_provider().getId()),
				new Pair<>("voucher_provider_id", pinImportConfig.getVoucher_provider().getId()),
				new Pair<>("voucher_type_id", pinImportConfig.getVoucher_type().getId())
			)
		);

		if (!uploadedVoucher.getResponseCode().equals(SUCCESS)) {
			String error = format("Could not find %s voucher configured for amount %s %s from service provider %s",
                pinImportConfig.getVoucher_provider().getName(), amount, pinImportConfig.getVoucher_type().getName(),
                pinImportConfig.getVoucher_provider().getName());
		    logger.severe(error);
			return new SymImportBatchList(NOT_SUPPORTED).setResponse(error);
		}

		SymResponseObject<String> batchIdResponse = getBatchIdUsingConfig(pinImportConfig, voucherPinFile.getName(), fileContents);
		if (!batchIdResponse.getResponseCode().equals(SUCCESS)) { return new SymImportBatchList(batchIdResponse.getResponseCode()); }

		SymResponseObject<Date> expiryResponse = getExpiryUsingConfig(pinImportConfig, voucherPinFile.getName(), fileContents);
		if (!expiryResponse.getResponseCode().equals(SUCCESS)) { return new SymImportBatchList(expiryResponse.getResponseCode()); }

		SymResponseObject<Integer> numVouchersResponse = getNumVouchersUsingConfig(pinImportConfig, voucherPinFile.getName(), fileContents);
		if (!numVouchersResponse.getResponseCode().equals(SUCCESS)) { return new SymImportBatchList(numVouchersResponse.getResponseCode()); }
		int numberOfVouchers = numVouchersResponse.getResponseObject();

		sym_voucher_status voucherStatus;
		if (expiryResponse.getResponseObject().getTime() > new Date().getTime()) {
			voucherStatus = fromEnum(NEW);
		} else {
			voucherStatus = fromEnum(EXPIRED);
		}

		sym_import_batch newImportBatch = new sym_import_batch(batchIdResponse.getResponseObject(),
			uploadedVoucher.getResponseObject(), voucherPinFile.getName(), new Date(), (long)numberOfVouchers);

		ArrayList<sym_pinbased_voucher> newVouchers = new ArrayList<>();
		String[] lineData;
		for (int c = 0; c < fileContents.size(); c++) {
			if (c + 1 < pinImportConfig.getPin_start_line() || fileContents.get(c).length() < 3) { continue; }
			lineData = fileContents.get(c).split(pinImportConfig.getDelimiter());
			if (lineData.length < 2) { continue; }

			String voucherPin = lineData[pinImportConfig.getPin_pos()-1];
			String voucherSerial = lineData[pinImportConfig.getSerial_pos()-1];

			if (voucherPin.length() != pinImportConfig.getPin_length()) {
				logger.warning(format("Invalid voucher pin length found a line number %s", c + 1));
				continue;
			}
			if (voucherSerial.length() != pinImportConfig.getSerial_length()) {
				logger.warning(format("Invalid voucher serial length found a line number %s", c + 1));
				continue;
			}

			newVouchers.add(new sym_pinbased_voucher(
					uploadedVoucher.getResponseObject(),
					encryptAES(getProperty("AES128BitKey"), getProperty("AESInitializationVector"), voucherPin),
					voucherStatus, newImportBatch, voucherSerial, expiryResponse.getResponseObject(), null, null)
			);
		}

		if (numberOfVouchers != -1 && numberOfVouchers != newVouchers.size()) {
            String error = format("Number of vouchers expected (%s) did not match number of vouchers to found (%s)",
                numberOfVouchers, newVouchers.size());
            logger.severe(error);
			return new SymImportBatchList(GENERAL_ERROR).setResponse(error);
		}

		try {
			//everything has worked out, we can now persist all the vouchers and the voucher batch
            newImportBatch.setNumber_of_vouchers((long)newVouchers.size()).save();
            newVouchers.forEach(sym_pinbased_voucher::save);
		} catch (Throwable ex) {
            newImportBatch.delete();
            ex.printStackTrace();
			return new SymImportBatchList(GENERAL_ERROR).setResponse(getRealThrowable(ex).getMessage());
		}

		try {
			if (!voucherPinFile.delete()) {
				logger.warning(format("Failed to delete uploaded pin file %s", voucherPinFile.getAbsolutePath()));
			}
		} catch (Exception e) { e.printStackTrace(); }

		return new SymImportBatchList(SUCCESS, converterService.toDTO(newImportBatch));
	}

	@Override
	public SymImportBatchList getVoucherProviderVoucherImports(Long voucherProviderId) {
		logger.info(format("Getting voucher imports for voucher provider %s", voucherProviderId));
		ArrayList<SymImportBatch> importBatches = new ArrayList<>();
		getEntityManagerRepo()
				.findWhere(sym_import_batch.class, new Pair<>("voucher.voucher_provider", voucherProviderId))
                .forEach(sp -> importBatches.add(converterService.toDTO(sp)));
		logger.info(format("Returning %s voucher providers", importBatches.size()));
		return new SymImportBatchList(SUCCESS, importBatches);
	}

	@Override
	public SymVoucherList getVoucher(Long voucherId) {
		logger.info(format("Getting voucher with Id %s", voucherId));
		sym_voucher voucher = getEntityManagerRepo().findById(sym_voucher.class, voucherId);
		if (voucher == null) { return new SymVoucherList(DATA_NOT_FOUND); }
		logger.info(format("Returning voucher with Id %s: %s", voucherId, voucher.toString()));
		return new SymVoucherList(SUCCESS, converterService.toDTO(voucher));
	}

	@Override
	public SymVoucherList getVouchers() {
		logger.info("Getting voucher list");
		ArrayList<SymVoucher> vouchers = new ArrayList<>();
		getEntityManagerRepo()
            .findWhere(sym_voucher.class, new Pair<>("is_active", 1))
            .forEach(v -> vouchers.add(converterService.toDTO(v)));
		logger.info(format("Returning %s vouchers", vouchers.size()));
		return new SymVoucherList(SUCCESS, vouchers);
	}

	@Override
	public SymVoucherPurchaseList buyVoucher(Long voucherId, Long authUserId, BigDecimal voucherValue,
                                             String recipient, String cashierName) {

        logger.info(format("Performing voucherId %s purchase (amount=%s) for authUser %s by cashier %s",
            voucherId, voucherValue == null ? "not specified" : voucherValue, authUserId, cashierName));

		if (!SymValidator.isValidName(cashierName)) {
			return new SymVoucherPurchaseList(SymResponseCode.INPUT_INVALID_CASHIER);
		}

		SymResponseObject<sym_voucher> voucherResponse = validateVoucher(voucherId);
		if (!voucherResponse.getResponseCode().equals(SUCCESS)) {
			return new SymVoucherPurchaseList(voucherResponse.getResponseCode());
		}

		if (!voucherResponse.getResponseObject().getIs_active()) {
			return new SymVoucherPurchaseList(INPUT_INVALID_AMOUNT);
		}

		SymResponseObject<sym_auth_user> authUserResponse = validateAuthUser(authUserId);
		if (!authUserResponse.getResponseCode().equals(SUCCESS)) {
			return new SymVoucherPurchaseList(authUserResponse.getResponseCode());
		}

        Long systemUserId = authUserResponse.getResponseObject().getUser().getId();

        SymResponseObject<sym_user> systemUserResponse = validateSystemUser(systemUserId);
		if (!systemUserResponse.getResponseCode().equals(SUCCESS)) {
			return new SymVoucherPurchaseList(systemUserResponse.getResponseCode());
		}

        sym_wallet wallet = authUserResponse.getResponseObject().getUser().getWallet();

		if (voucherResponse.getResponseObject().getIs_fixed()) {
			voucherValue = voucherResponse.getResponseObject().getVoucher_value();
		}
		else if (voucherValue == null) {
            SymVoucherPurchaseList response = new SymVoucherPurchaseList(INPUT_INCOMPLETE_REQUEST);
            response.getSymResponse().setResponse_message("voucherValue must be specified for variable vouchers");
            return response;
		}
		else if (voucherValue.compareTo(new BigDecimal(0.0)) < 1) {
            SymVoucherPurchaseList response = new SymVoucherPurchaseList(INPUT_INVALID_AMOUNT);
            response.getSymResponse().setResponse_message("voucherValue must be greater than 0");
            return response;
		}

		//log voucher purchase, it now has the minimum logging values
		logger.info(format("Logging voucher purchase of %s for wallet %s", voucherValue, wallet.getId()));
		sym_voucher_purchase voucherPurchaseResponse = new sym_voucher_purchase(voucherResponse.getResponseObject(),
            null, wallet, authUserResponse.getResponseObject(), fromEnum(GENERAL_ERROR),
            voucherValue, null, null, new Date(), false, recipient,
            cashierName, fromEnum(RECEIPT)).save();

		//calculate voucher amounts based on discount
		List<sym_wallet_group_voucher> walletGroupVoucher = getEntityManagerRepo().findWhere(
                sym_wallet_group_voucher.class, asList(
				new Pair<>("wallet_group_id", wallet.getWallet_group().getId()),
				new Pair<>("voucher_id", voucherId)
		));

		if (walletGroupVoucher == null || walletGroupVoucher.size() != 1) {
			logger.info(format("Wallet %s does not belong to a valid voucher group ", wallet.getId()));
			//wallet does not belong to a valid wallet group
			voucherPurchaseResponse.setResponse_code(
				fromEnum(CONFIGURATION_INVALID)).save();
			return new SymVoucherPurchaseList(GENERAL_ERROR);
		}

		double walletDiscountPercentage, voucherProviderDiscountPercentage, walletCost, voucherProviderCost;

		voucherProviderDiscountPercentage = ((100 - voucherResponse.getResponseObject().getVoucher_provider_discount()) / 100);
		voucherProviderCost = voucherProviderDiscountPercentage * voucherValue.doubleValue();
		voucherPurchaseResponse.setVoucher_provider_value(new BigDecimal(voucherProviderCost)).save();

		walletDiscountPercentage = ((100 - walletGroupVoucher.get(0).getWallet_discount()) / 100);
		walletCost = walletDiscountPercentage * voucherValue.doubleValue();
		voucherPurchaseResponse.setWallet_cost(new BigDecimal(walletCost)).save();

		logger.info(format("voucherProviderCost: %s", voucherProviderCost));
		logger.info(format("walletCost: %s", walletCost));

		//verify stock before deducting funds
		List<sym_pinbased_voucher> pinbasedVouchers = null;
		if (voucherResponse.getResponseObject().getIs_pin_based()) {

            Long stockAmount = getEntityManagerRepo().countWhere(sym_pinbased_voucher.class,
                asList(
                    new Pair<>("voucher_id", voucherId),
                    new Pair<>("voucher_status", fromEnum(NEW).getId())
                ), false, false);

            if (stockAmount == 0) {
                //insufficient stock
                logger.info(format("Insufficient stock of voucher %s", voucherId));
                voucherPurchaseResponse.setResponse_code(fromEnum(INSUFFICIENT_STOCK)).save();


                String voucherDesc = voucherResponse.getResponseObject().getService_provider().getName() + " " +
                        voucherResponse.getResponseObject().getVoucher_value().toPlainString() + " " +
                        voucherResponse.getResponseObject().getVoucher_type().getName();

                sendEmailAlert("Sym_JARVIS", "Stock depleted for " + voucherDesc,
                        "Stock depleted for voucher " + voucherId + ": " + voucherDesc);

                return new SymVoucherPurchaseList(INSUFFICIENT_STOCK);
            } else if (stockAmount < Integer.parseInt(getProperty("LowStockThreshold"))) {
                String voucherDesc = voucherResponse.getResponseObject().getService_provider().getName() + " " +
                                     voucherResponse.getResponseObject().getVoucher_value().toPlainString() + " " +
                                     voucherResponse.getResponseObject().getVoucher_type().getName();

                sendEmailAlert("Sym_JARVIS", format("Low Stock (%d) for " + voucherDesc, stockAmount),
                    format("Running low on stock (%d remaining) of voucher " + voucherId + ": " + voucherDesc, stockAmount)
                );
            }

            pinbasedVouchers = getEntityManagerRepo().findWhere(sym_pinbased_voucher.class,
                asList(
                    new Pair<>("voucher_id", voucherId),
				    new Pair<>("voucher_status", fromEnum(NEW).getId())),
                    1,false,false, false, false);

            if (pinbasedVouchers == null || pinbasedVouchers.size() != 1) {
                //insufficient stock
                String voucherDesc = voucherResponse.getResponseObject().getService_provider().getName() + " " +
                        voucherResponse.getResponseObject().getVoucher_value().toPlainString() + " " +
                        voucherResponse.getResponseObject().getVoucher_type().getName();

                sendEmailAlert("Sym_JARVIS", "Stock depleted for " + voucherDesc,
                        "Stock depleted for voucher " + voucherId + ": " + voucherDesc);

                logger.info(format("Insufficient stock of voucher %s", voucherId));
                voucherPurchaseResponse.setResponse_code(fromEnum(INSUFFICIENT_STOCK)).save();
                return new SymVoucherPurchaseList(INSUFFICIENT_STOCK);
            }
        }

		//deduct the funds to make sure wallet has the cheese for the transaction
		SymResponseObject<sym_wallet> balanceResponse =
                walletManager.updateWalletBalance(wallet, new BigDecimal(walletCost * -1));

		if (!balanceResponse.getResponseCode().equals(SUCCESS)) {
			logger.info(format("Failed to update wallet balance %s", balanceResponse.getMessage()));
			sym_response_code responseCode = getEntityManagerRepo()
				.findById(sym_response_code.class, (long)balanceResponse.getCode());
			voucherPurchaseResponse.setResponse_code(responseCode).save();
			return new SymVoucherPurchaseList(SymResponseCode.valueOf(responseCode.getId().intValue()));
		}

		//after this point, any failure must result in balance reversal because wallet has already been charged
		try {
            sym_pinbased_voucher distributionVoucher;
			if (voucherResponse.getResponseObject().getIs_pin_based() && pinbasedVouchers != null) {

				logger.info(format("Get pin based voucher %s from database. Marking it as sold",
						pinbasedVouchers.get(0).getId()));
				distributionVoucher = pinbasedVouchers.get(0)
                    .setVoucher_status(fromEnum(SOLD))
                    .setDistributed_date(new Date()).save();
                voucherPurchaseResponse.setPinbased_voucher(distributionVoucher).save();
            } else {
				//voucher is not in the database, go and fetch it from voucher provider
				logger.info(format("Need to get voucher from voucher provider %s",
					voucherResponse.getResponseObject().getVoucher_provider().getName()));
                walletManager.updateWalletBalance(wallet, new BigDecimal(walletCost));

                //TODO if reversal fails, send email

				return new SymVoucherPurchaseList(NOT_SUPPORTED);
			}

            voucherPurchaseResponse.setResponse_code(fromEnum(SUCCESS)).save();

			logger.info(format("Distributing voucher using distribution channel %s", RECEIPT.name()));
			return new SymVoucherPurchaseList(SUCCESS, converterService.toDTO(voucherPurchaseResponse));

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.severe(format("Error performing voucher purchase %s", ex.getMessage()));
            walletManager.updateWalletBalance(wallet, new BigDecimal(walletCost));
			return new SymVoucherPurchaseList(GENERAL_ERROR);
		}
	}

	@Override
	public SymWalletGroupList getWalletGroups() {
		logger.info("Getting voucher group list");
		ArrayList<SymWalletGroup> walletGroups = new ArrayList<>();
		getEntityManagerRepo().findAll(sym_wallet_group.class).forEach(vg -> walletGroups.add(converterService.toDTO(vg)));
		logger.info(format("Returning %s voucher groups", walletGroups.size()));
		return new SymWalletGroupList(SUCCESS, walletGroups);
	}

	@Override
	public SymWalletGroupVoucherList getWalletGroupVouchers(Long walletGroupId) {
		logger.info("Getting vouchers for voucher group " + walletGroupId);
		ArrayList<SymWalletGroupVoucher> walletGroupVouchers = new ArrayList<>();
		getEntityManagerRepo()
				.findWhere(sym_wallet_group_voucher.class, new Pair<>("wallet_group", walletGroupId))
				.forEach(vg -> walletGroupVouchers.add(converterService.toDTO(vg)));
		logger.info(format("Returning %s voucher group vouchers", walletGroupVouchers.size()));
		return new SymWalletGroupVoucherList(SUCCESS, walletGroupVouchers);

	}

//	@Override
//	public SymWalletList createWallet(Long accountAdminUserId, Long walletGroupId, String email, String msisdn, String username,
//										  String deviceId, String firstName, String lastName, String dateOfBirth) {
//
//		logger.info("Creating new wallet.");
//
//		//check if system user data has been specified
//		if (accountAdminUserId == null) {
//			logger.info("No existing user specified. Check parameters for creating new user.");
//
//			if (username == null && email == null && msisdn == null) {
//				SymWalletList response = new SymWalletList(INPUT_INCOMPLETE_REQUEST);
//				response.getSymResponse().setResponse_message("Cannot create a wallet with no user information");
//				return response;
//			}
//
//			if (username == null) { return new SymWalletList(INPUT_INVALID_USERNAME); }
//			if (email == null) { return new SymWalletList(INPUT_INVALID_EMAIL); }
//			if (msisdn == null) { return new SymWalletList(INPUT_INVALID_MSISDN); }
//		}
//
//		//check if system user data does not conflict
//		if (accountAdminUserId != null && (username != null || email != null || msisdn != null)) {
//			SymWalletList response = new SymWalletList(INPUT_INVALID_REQUEST);
//			response.getSymResponse()
//				.setResponse_message("Cannot create a new user and specify existing user data simultaneously");
//			return response;
//		}
//
//		if (walletGroupId == null) {
//			SymWalletList response = new SymWalletList(INPUT_INCOMPLETE_REQUEST);
//			response.getSymResponse().setResponse_message("You must specify a walletGroupId to register a wallet");
//			return response;
//		}
//
//		//check for valid voucher group
//		logger.info("Finding voucher group " + walletGroupId);
//		sym_wallet_group walletGroup = getEntityManagerRepo().findById(sym_wallet_group.class, walletGroupId);
//
//		if (walletGroup == null) {
//			SymWalletList response = new SymWalletList(DATA_NOT_FOUND);
//			response.getSymResponse().setResponse_message("Cannot find voucher group with id " + walletGroupId);
//			return response;
//		}
//
//		//register with existing user
//		if (accountAdminUserId != null) {
//			logger.info("Registering wallet with existing user " + accountAdminUserId);
//			sym_user systemUser = getEntityManagerRepo().findById(sym_user.class, accountAdminUserId);
//
//			if (systemUser == null) {
//				SymWalletList response = new SymWalletList(DATA_NOT_FOUND);
//				response.getSymResponse().setResponse_message("Cannot find system user with id " + accountAdminUserId);
//				return response;
//			}
//
//			List<sym_wallet> existingWallet = getEntityManagerRepo()
//				.findWhere(sym_wallet.class, new Pair<>("sym_user_id", accountAdminUserId));
//
//			//check if user is already assigned to another wallet
//			if (existingWallet != null && existingWallet.size() > 0) {
//				SymWalletList response = new SymWalletList(PREVIOUS_REGISTRATION_FOUND);
//				response.getSymResponse().setResponse_message("Cannot register more than one wallet per user");
//				return response;
//			}
//
//			//create and save new wallet
//			sym_wallet newWallet = new sym_wallet(new BigDecimal(0.0), systemUser, walletGroup).save();
//
//			List<sym_system_user_wallet> systemUserWallet = getEntityManagerRepo()
//				.findWhere(sym_system_user_wallet.class, new Pair<>("sym_user_id", systemUser.getId()));
//
//			if (systemUserWallet == null) { new sym_system_user_wallet(systemUser, newWallet).save(); }
//			else { systemUserWallet.get(0).setWallet(newWallet); }
//
//			return new SymWalletList(SUCCESS, converterService.toDTO(newWallet));
//		}
//
//		//create new system user
//		SymSystemUserList userRegResponse = symbiosisRequestProcessor.registerWebUser(
//			email, msisdn, username, deviceId, firstName, lastName, dateOfBirth);
//
//		//if creating new system user succeeds, proceed to create a new wallet
//		if (userRegResponse.getSymResponse().getResponse_code().equals(SUCCESS.getCode())) {
//			logger.info("Created new user " + userRegResponse.getSystemUserData().get(0).getUsername());
//			sym_user systemUser = getEntityManagerRepo()
//					.findById(sym_user.class, userRegResponse.getSystemUserData().get(0).getUserId());
//			sym_wallet newWallet = new sym_wallet(new BigDecimal(0.0), systemUser, walletGroup).save();
//			new sym_system_user_wallet(systemUser, newWallet).save();
//			return new SymWalletList(SUCCESS, converterService.toDTO(newWallet));
//		} else {
//			logger.info("Failed to create new user " + userRegResponse.getSymResponse().getResponse_message());
//			return new SymWalletList(userRegResponse.getSymResponse().getResponse());
//		}
//	}

//	@Override
//	public SymResponse loadWalletPayment(Long walletId, BigDecimal amount, Long depositTypeId,
//                                           String depositReference, Long paymentTime, String bankName, String bankReference, String bankStatementId) {
//
//		//TODO check load money permissions
//		//validate amount
//		SymResponseObject<BigDecimal> amountResponse = validateAmount(amount);
//		if (!amountResponse.getResponseCode().equals(SUCCESS)) {
//			return new SymResponse(amountResponse.getResponseCode());
//		}
//
//		//validate deposit type
//		SymResponseObject<sym_deposit_type> depositTypeResponse = validateDepositType(depositTypeId);
//		if (!depositTypeResponse.getResponseCode().equals(SUCCESS)) {
//			return new SymResponse(depositTypeResponse.getResponseCode());
//		}
//
//		//validate wallet
//		SymResponseObject<sym_wallet> walletResponse = validateWallet(walletId);
//		if (!walletResponse.getResponseCode().equals(SUCCESS)) {
//			return new SymResponse(walletResponse.getResponseCode());
//		}
//
//		SymResponseCode loadResult = updateWalletBalance(walletResponse.getResponseObject(), amount);
//
//		if (loadResult.equals(SUCCESS)) {
//			new sym_incoming_payment(amount, depositTypeResponse.getResponseObject(), walletResponse.getResponseObject(),
//				depositReference, paymentTime == null ? null : new Date(paymentTime), new Date(),
//				getOrCreateBank(bankName).getResponseObject(), bankReference, bankStatementId).save();
//		}
//
//		return new SymResponse(loadResult);
//	}

	@Override
	public SymResponse loadVoucherProviderPayment(Long voucherProviderId, BigDecimal amount) {

		//TODO check load money permissions
        logger.info(format("Attempting to load voucher provider %s with amount %s", voucherProviderId, amount));
		if (voucherProviderId == null) {
			return new SymResponse(INPUT_INCOMPLETE_REQUEST).setResponse_message("voucherProviderId not supplied");
		}

		//validate amount
		SymResponseObject<BigDecimal> validateAmountResponse = validateAmount(amount);
		if (!validateAmountResponse.getResponseCode().equals(SUCCESS)) {
			return new SymResponse(validateAmountResponse.getResponseCode());
		}

		//validate wallet
		SymResponseObject<sym_voucher_provider> voucherProviderResponse = validateVoucherProvider(voucherProviderId);
		if (!voucherProviderResponse.getResponseCode().equals(SUCCESS)) {
			return new SymResponse(voucherProviderResponse.getResponseCode());
		}

		SymResponseObject<sym_voucher_provider> loadResult = updateVoucherProviderBalance(voucherProviderResponse.getResponseObject(), amount);

		if (loadResult.getResponseCode().equals(SUCCESS)) {
			new sym_voucher_provider_payment(amount, new Date()).save();
		}

		return new SymResponse(loadResult.getResponseCode());
	}

    @Override
    public SymVoucherPurchaseList getVoucherPurchase(Long voucherPurchaseId) {

        logger.info(format("Getting voucher purchase by id %s", voucherPurchaseId));

        sym_voucher_purchase voucherPurchaseResponse = getEntityManagerRepo().findById(
            sym_voucher_purchase.class, voucherPurchaseId
        );

        if (voucherPurchaseResponse == null) {
            return new SymVoucherPurchaseList(DATA_NOT_FOUND);
        }

        return new SymVoucherPurchaseList(SUCCESS, converterService.toDTO(voucherPurchaseResponse));
    }
}
