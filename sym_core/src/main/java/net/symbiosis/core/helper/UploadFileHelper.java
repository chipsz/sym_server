package net.symbiosis.core.helper;

import net.symbiosis.common.security.PGPKeyBasedFileProcessor;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.utilities.ReferenceGenerator;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_pin_import_config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static net.symbiosis.common.utilities.SymTransformer.stringToDate;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_DEFAULT_VOUCHER_EXPIRY_DAYS;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.utilities.CommonUtilities.getValueByStringPattern;
import static net.symbiosis.core_lib.utilities.IOUtils.readFromFile;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

/***************************************************************************
 * *
 * Created:     15 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class UploadFileHelper {

	private static final Logger logger = Logger.getLogger(UploadFileHelper.class.getSimpleName());

	public static SymResponseObject<File> decryptPGPFile(String filePath, sym_pin_import_config pinImportConfig) {

	    if (filePath == null || pinImportConfig == null) {
            return new SymResponseObject<>(INPUT_INCOMPLETE_REQUEST);
        } else if (pinImportConfig.getPgp_private_key_file() == null || pinImportConfig.getPgp_key_pass() == null) {
            return new SymResponseObject<>(CONFIGURATION_INVALID);
        }

	    logger.info(format("Decrypting PGP file %s with key %s", filePath, pinImportConfig.getPgp_private_key_file()));
        ClassLoader classLoader = UploadFileHelper.class.getClassLoader();
        InputStream pgpKeyFileStream = classLoader.getResourceAsStream("pgp/" + pinImportConfig.getPgp_private_key_file());
        String outputFilePath = filePath + ".dec";
        logger.info(format("Performing decryption and saving result to %s", outputFilePath));
        try {
            outputFilePath = PGPKeyBasedFileProcessor.decryptFile(
                filePath, pgpKeyFileStream, pinImportConfig.getPgp_key_pass().toCharArray(), outputFilePath
            );

            logger.info(format("Decrypted PGP file. Output file saved to %s", readFromFile(outputFilePath)));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new SymResponseObject<File>(GENERAL_ERROR).setMessage(e.getMessage());
        }
        finally { if (pgpKeyFileStream != null) { try { pgpKeyFileStream.close(); } catch (IOException e) {} } }
        return new SymResponseObject<>(SUCCESS, new File(outputFilePath));
    }

	public static SymResponseObject<Double> getAmountUsingConfig(sym_pin_import_config pinImportConfig,
		String pinFileName, ArrayList<String> pinFileContents) {

		if (pinImportConfig == null || pinFileName == null ||
			pinFileContents == null || pinImportConfig.getAmount_regex() == null) {
			logger.severe("Invalid/No configuration to get amount. May need to be specified manually.");
			return new SymResponseObject<Double>(CONFIGURATION_INVALID).setMessage(
                "Invalid/No configuration to get amount. You may need to specify amount manually."
            );
		}
		if (pinImportConfig.getAmount_in_filename()) {
			SymResponseObject<String> amountResponse = getValueByStringPattern(pinFileName,
					pinImportConfig.getAmount_regex(), pinImportConfig.getAmount_pos());
			if (!amountResponse.getResponseCode().equals(SUCCESS)) {
				logger.severe("Failed to get amount from filename");
				return new SymResponseObject<Double>(amountResponse.getResponseCode()).setMessage("Failed to get amount from filename");
			}
			try { return new SymResponseObject<>(SUCCESS, parseDouble(amountResponse.getResponseObject())); }
			catch (Exception ex) {
				logger.severe("Failed to parse amount from filename");
				return new SymResponseObject<Double>(amountResponse.getResponseCode()).setMessage("Failed to parse amount from filename");
			}
		} else if (!pinImportConfig.getAmount_in_contents()) {
			logger.severe("No configuration to get amount data");
			return new SymResponseObject<Double>(CONFIGURATION_INVALID).setMessage("No configuration to get amount data");
		} else {
			if (pinImportConfig.getAmount_line_num() == null) {
				logger.severe("No configured line number for amount data");
				return new SymResponseObject<Double>(CONFIGURATION_INVALID).setMessage("No configured line number for amount data");
			}
			SymResponseObject<String> amountResponse = getValueByStringPattern(pinFileContents.get(
				pinImportConfig.getAmount_line_num()-1), pinImportConfig.getAmount_regex(), pinImportConfig.getAmount_pos());
			if (!amountResponse.getResponseCode().equals(SUCCESS)) {
				logger.severe("Failed to get amount from file contents");
				return new SymResponseObject<Double>(amountResponse.getResponseCode()).setMessage("Failed to get amount from file contents");
			}
			try { return new SymResponseObject<>(SUCCESS, parseDouble(amountResponse.getResponseObject())); }
			catch (Exception ex) {
				logger.severe("Failed to parse amount from file contents");
				return new SymResponseObject<Double>(amountResponse.getResponseCode()).setMessage("Failed to parse amount from file contents");
			}
		}
	}

	public static SymResponseObject<String> getBatchIdUsingConfig(sym_pin_import_config pinImportConfig,
		String pinFileName, ArrayList<String> pinFileContents) {

		if (pinImportConfig == null || pinFileName == null ||
			pinFileContents == null || pinImportConfig.getBatch_id_regex() == null) {
			logger.severe("Invalid configuration to get batchId");
			return new SymResponseObject<>(CONFIGURATION_INVALID);
		}
		if (pinImportConfig.getBatch_id_in_filename()) {
			logger.info("Getting batchId from filename");
			SymResponseObject<String> batchIdResponse = getValueByStringPattern(pinFileName,
					pinImportConfig.getBatch_id_regex(), pinImportConfig.getBatch_id_pos());
			if (!batchIdResponse.getResponseCode().equals(SUCCESS)) {
				logger.severe("Failed to get batchId from filename");
				return new SymResponseObject<>(batchIdResponse.getResponseCode());
			}
			return new SymResponseObject<>(SUCCESS, batchIdResponse.getResponseObject());
		} else if (!pinImportConfig.getBatch_id_in_contents()) {
			logger.info("No configuration to get batchId data. Generating new value");
			return new SymResponseObject<>(SUCCESS, ReferenceGenerator.GenTrails());
		} else {
			logger.info("Getting batchId from file contents");
			if (pinImportConfig.getBatch_id_line_num() == null) {
				logger.severe("No configured line number for batch id");
				return new SymResponseObject<>(CONFIGURATION_INVALID);
			}
			SymResponseObject<String> batchIdResponse = getValueByStringPattern(pinFileContents.get(
				pinImportConfig.getBatch_id_line_num()-1), pinImportConfig.getBatch_id_regex(), pinImportConfig.getBatch_id_pos());
			if (!batchIdResponse.getResponseCode().equals(SUCCESS)) {
				logger.severe("Failed to get batch id from file contents");
				return new SymResponseObject<>(batchIdResponse.getResponseCode());
			}
			return new SymResponseObject<>(SUCCESS, batchIdResponse.getResponseObject());
		}
	}

	public static SymResponseObject<Date> getExpiryUsingConfig(sym_pin_import_config pinImportConfig,
		String pinFileName, ArrayList<String> pinFileContents) {
		if (pinImportConfig == null || pinFileName == null || pinFileContents == null) {
			logger.severe("Invalid configuration to get expiry date");
			return new SymResponseObject<>(CONFIGURATION_INVALID);
		}
		if (pinImportConfig.getExpiry_in_filename()) {
			logger.info("Getting expiry date from filename");
			SymResponseObject<String> expiryDateResponse = getValueByStringPattern(
					pinFileName, pinImportConfig.getExpiry_regex(), pinImportConfig.getExpiry_pos());
			if (!expiryDateResponse.getResponseCode().equals(SUCCESS)) {
				logger.severe("Failed to get expiry date from filename");
				return new SymResponseObject<>(expiryDateResponse.getResponseCode());
			}
			try {
				Date voucherExpiryDate =
					stringToDate(expiryDateResponse.getResponseObject(), pinImportConfig.getExpiry_date_format());
				if (voucherExpiryDate == null) {
					logger.severe(format("Failed to parse expiry date from filename with format %s",
							pinImportConfig.getExpiry_date_format()));
					return new SymResponseObject<>(expiryDateResponse.getResponseCode());
				}
				return new SymResponseObject<>(SUCCESS, voucherExpiryDate);
			} catch (Exception ex) {
				logger.severe("Failed to parse expiry date from filename");
				return new SymResponseObject<>(expiryDateResponse.getResponseCode());
			}
		} else if (!pinImportConfig.getExpiry_in_contents()) {
			logger.info(format("No configuration to get expiry date. Generating date for %s days in the future",
                    getSymConfigDao().getConfig(CONFIG_DEFAULT_VOUCHER_EXPIRY_DAYS)));
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, parseInt(getSymConfigDao().getConfig(CONFIG_DEFAULT_VOUCHER_EXPIRY_DAYS)));
			return new SymResponseObject<>(SUCCESS, calendar.getTime());
		} else {
			logger.info("Getting expiry date from file contents");
			if (pinImportConfig.getExpiry_line_num() == null) {
				logger.severe("No configured line number for expiry date");
				return new SymResponseObject<>(CONFIGURATION_INVALID);
			}
			SymResponseObject<String> expiryResponse = getValueByStringPattern(pinFileContents.get(
				pinImportConfig.getExpiry_line_num()-1), pinImportConfig.getExpiry_regex(), pinImportConfig.getExpiry_pos());
			if (!expiryResponse.getResponseCode().equals(SUCCESS)) {
				logger.severe("Failed to get expiry date from file contents");
				return new SymResponseObject<>(expiryResponse.getResponseCode());
			}
			Date voucherExpiryDate =
					stringToDate(expiryResponse.getResponseObject(), pinImportConfig.getExpiry_date_format());
			if (voucherExpiryDate == null) {
				logger.severe(format("Failed to parse expiry date from filename with format %s",
						pinImportConfig.getExpiry_date_format()));
				return new SymResponseObject<>(expiryResponse.getResponseCode());
			}
			return new SymResponseObject<>(SUCCESS, voucherExpiryDate);
		}
	}

	public static SymResponseObject<Integer> getNumVouchersUsingConfig(sym_pin_import_config pinImportConfig,
		String pinFileName, ArrayList<String> pinFileContents) {
        if (pinImportConfig == null || pinFileName == null || pinFileContents == null) {
            logger.severe("Invalid configuration to get total vouchers");
            return new SymResponseObject<>(CONFIGURATION_INVALID);
        }
        if (pinImportConfig.getTotal_num_regex() == null) {
            logger.warning("No configuration to get total vouchers. Number of loaded vouchers will not be validated");
            return new SymResponseObject<>(SUCCESS, -1);
        }
        if (pinImportConfig.getTotal_num_in_filename()) {
			SymResponseObject<String> totalNumberResponse = getValueByStringPattern(pinFileName,
				pinImportConfig.getTotal_num_regex(), pinImportConfig.getTotal_num_pos());
			if (!totalNumberResponse.getResponseCode().equals(SUCCESS)) {
				logger.severe("Failed to get total number of vouchers from filename");
				return new SymResponseObject<>(totalNumberResponse.getResponseCode());
			}
			try { return new SymResponseObject<>(SUCCESS, parseInt(totalNumberResponse.getResponseObject())); }
			catch (Exception ex) {
				logger.severe("Failed to parse total number of vouchers from filename");
				return new SymResponseObject<>(totalNumberResponse.getResponseCode());
			}
		} else if (!pinImportConfig.getTotal_num_in_contents()) {
			logger.warning("No configuration to get total vouchers. Number of loaded vouchers will not be validated");
            return new SymResponseObject<>(SUCCESS, -1);
		} else {
            if (pinImportConfig.getTotal_num_line_num() == null) {
                logger.warning("No line number configured to get total vouchers. Number of loaded vouchers will not be validated");
                return new SymResponseObject<>(CONFIGURATION_INVALID);
            }
            SymResponseObject<String> totalNumberResponse = getValueByStringPattern( pinFileContents.get(
				pinImportConfig.getTotal_num_line_num()-1), pinImportConfig.getTotal_num_regex(), pinImportConfig.getTotal_num_pos());
            if (!totalNumberResponse.getResponseCode().equals(SUCCESS)) {
                logger.severe("Failed to get total number of vouchers from file contents");
                return new SymResponseObject<>(totalNumberResponse.getResponseCode());
            }
            try { return new SymResponseObject<>(SUCCESS, parseInt(totalNumberResponse.getResponseObject())); }
            catch (Exception ex) {
                logger.severe("Failed to parse total number of vouchers from file contents");
                return new SymResponseObject<>(totalNumberResponse.getResponseCode());
            }
        }


	}
}
