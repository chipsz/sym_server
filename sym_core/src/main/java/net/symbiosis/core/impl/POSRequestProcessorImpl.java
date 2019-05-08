package net.symbiosis.core.impl;

import net.symbiosis.authentication.authentication.PosAuthenticationProvider;
import net.symbiosis.core.contract.SymDeviceUserResponse;
import net.symbiosis.core.contract.SymMap;
import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.contract.SymVoucherPurchaseList;
import net.symbiosis.core.contract.symbiosis.SymDeviceUser;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.core.service.POSRequestProcessor;
import net.symbiosis.core.service.VoucherProcessor;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.device.sym_device_pos_machine;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.log.sym_voucher_purchase;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_EMAIL_ALERT_TO;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_FALCON_POS_BINARY_VERSION;
import static net.symbiosis.core_lib.enumeration.SymChannel.POS_MACHINE;
import static net.symbiosis.core_lib.enumeration.SymEventType.USER_LOGIN;
import static net.symbiosis.core_lib.enumeration.SymEventType.USER_REGISTRATION;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     07 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Service
public class POSRequestProcessorImpl implements POSRequestProcessor {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private final sym_channel POS_CHANNEL = fromEnum(POS_MACHINE);
    private final VoucherProcessor voucherProcessor;
    private final ConverterService converterService;

    @Autowired
    public POSRequestProcessorImpl(VoucherProcessor voucherProcessor, ConverterService converterService) {
        this.voucherProcessor = voucherProcessor;
        this.converterService = converterService;
    }

    @Override
    public SymMap getFalconVersion() {
        HashMap<String, String> versionInfo = new HashMap<>();
        versionInfo.put("version", getSymConfigDao().getConfig(CONFIG_FALCON_POS_BINARY_VERSION));
        logger.info("Returning version " + getSymConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO));
        return new SymMap(SUCCESS, versionInfo);
    }

    @Override
    public SymDeviceUserResponse startPosSession(String imei, String username, String pin) {
        logger.info(format("Performing login for %s", username));

        sym_request_response_log requestResponseLog = new sym_request_response_log(
            POS_CHANNEL, fromEnum(USER_LOGIN),
            format("IMEI:'%s' | username:'%s' | pin_length:[%s]", imei, username, pin == null ? 0 : pin.length())
        );

        PosAuthenticationProvider authenticationProvider = new PosAuthenticationProvider(
            requestResponseLog, imei, username, pin
        );

        SymResponseObject<sym_auth_user> authResponse = authenticationProvider.authenticateUser();

        requestResponseLog.setAuth_user(authResponse.getResponseObject());
        requestResponseLog.setResponse_code(fromEnum(authResponse.getResponseCode()));

        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            requestResponseLog.setOutgoing_response(authResponse.getMessage());
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.save();
            return new SymDeviceUserResponse(authResponse.getResponseCode());
        }

        SymDeviceUser userResponse = new SymDeviceUser(
            authResponse.getResponseObject().getAuth_group().getName(),
            authResponse.getResponseObject().getAuth_token(),
            authResponse.getResponseObject().getLast_login_date(),
            getSymConfigDao().getConfig(CONFIG_FALCON_POS_BINARY_VERSION)
        );

        requestResponseLog.setOutgoing_response(authResponse.getMessage());
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setSystem_user(authResponse.getResponseObject().getUser());
        requestResponseLog.save();
        return new SymDeviceUserResponse(authResponse.getResponseCode(), userResponse);
    }

    @Override
    public SymResponse endPosSession(Long sessionId, String imei, String authToken) {
        return null;
    }

    @Override
    public SymDeviceUserResponse registerPosUser(String branchName, String machineName, String imei1, String imei2,
                                       String imsi1, String imsi2, String msisdn1, String msisdn2, String username, String pin) {

        logger.info(format("Performing POS registration for %s on POS device", username));

        String incomingRequest = format("branchName=%s|machineName=%s|imei1=%s|imei2=%s|imsi1=%s|imsi2=%s|msisdn1=%s|msisdn2=%s|username=%s",
                branchName, machineName, imei1, imei2, imsi1, imsi2, msisdn1, msisdn2, username);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                POS_CHANNEL, fromEnum(USER_REGISTRATION), incomingRequest
        );

        if (imei1 == null || imei2 == null) {
            requestResponseLog.setResponse_code(fromEnum(INPUT_INCOMPLETE_REQUEST));
            requestResponseLog.setOutgoing_response(INPUT_INCOMPLETE_REQUEST.getMessage());
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.save();
            return new SymDeviceUserResponse(INPUT_INCOMPLETE_REQUEST);
        }

        PosAuthenticationProvider posAuthenticationProvider = new PosAuthenticationProvider(
            requestResponseLog, imei1, username, pin
        );

        SymResponseObject<sym_auth_user> userResponse = posAuthenticationProvider.authenticateUser();

        //validate user credentials
        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            requestResponseLog.setResponse_code(fromEnum(userResponse.getResponseCode()));
            requestResponseLog.setOutgoing_response(userResponse.getResponseCode().getMessage());
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.save();
            return new SymDeviceUserResponse(userResponse.getResponseCode());
        }

        //check for existing registration
        List<sym_device_pos_machine> existingRegistration = getEntityManagerRepo().findWhere(sym_device_pos_machine.class, asList(
            new Pair<>("is_active", 1),
            new Pair<>("imei1", imei1),
            new Pair<>("imei2", imei2)
        ));

        sym_device_pos_machine posMachine;

        //deactivate any existing registrations
        if (existingRegistration != null) {
            for (sym_device_pos_machine existingPos : existingRegistration) {
                existingPos.setIs_active(false);
            }
        }

        //activate new machine
        new sym_device_pos_machine(userResponse.getResponseObject(), true, new Date(), new Date(),
                branchName, machineName, imei1, imei2, imsi1, imsi2, msisdn1, msisdn2)
        .save();

        requestResponseLog.setResponse_code(fromEnum(SUCCESS));
        requestResponseLog.setOutgoing_response(SUCCESS.getMessage());
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.save();

        return new SymDeviceUserResponse(SUCCESS, new SymDeviceUser(userResponse.getResponseObject(), getSymConfigDao().getConfig(CONFIG_FALCON_POS_BINARY_VERSION)));
    }

    @Override
    public SymVoucherPurchaseList buyVoucher(Long voucherId, String username, String pin, BigDecimal voucherValue,
                                             String recipient, String cashierName) {

        logger.info(format("Got POS machine request by user %s for voucherId %s (amount=%s) purchase from cashier %s",
                username, voucherId, voucherValue == null ? "not specified" : voucherValue, cashierName));

        List<sym_device_pos_machine> posMachines = getEntityManagerRepo().findWhere(
            sym_device_pos_machine.class, asList(
                new Pair<>("is_active", 1),
                new Pair<>("auth_user.user.username", username)
            )
        );

        if (posMachines.size() != 1) { return new SymVoucherPurchaseList(AUTH_NON_EXISTENT); }

        sym_auth_user authUser = posMachines.get(0).getAuth_user();

        logger.info(format("Using company %s wallet", authUser.getUser().getWallet().getCompany().getCompany_name()));


        String incomingRequest = format("voucherId=%s|imei=%s|pin=%s|cashier=%s|voucherValue=%s|recipient=%s",
            voucherId, posMachines.get(0).getImei1(), pin, cashierName, voucherValue, recipient);

        sym_request_response_log log = new sym_request_response_log(fromEnum(POS_MACHINE), fromEnum(USER_LOGIN), incomingRequest);

        PosAuthenticationProvider authProvider = new PosAuthenticationProvider(log, posMachines.get(0).getImei1(), authUser.getUser().getUsername(), pin);

        SymResponseObject<sym_auth_user> authResponse = authProvider.authenticateUser();

        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logger.info(format("Authentication failed. %s", authResponse.getMessage()));
            logResponse(authUser, log, authResponse.getResponseCode());
            return new SymVoucherPurchaseList(authResponse.getResponseCode());
        }

        SymVoucherPurchaseList purchaseResponse = voucherProcessor.buyVoucher(voucherId, authUser.getId(), voucherValue, recipient, cashierName);

        logResponse(authUser, log, purchaseResponse.getSymResponse().getResponse());

        return purchaseResponse;

    }

    @Override
    public SymVoucherPurchaseList queryTransaction(Long voucherPurchaseId, String username, String pin) {
        logger.info(format("Got POS machine request by %s to query voucher purchase %s", username, voucherPurchaseId));
        List<sym_device_pos_machine> posMachines = getEntityManagerRepo().findWhere(
            sym_device_pos_machine.class, asList(
                    new Pair<>("is_active", 1),
                    new Pair<>("auth_user.user.username", username)
            )
        );

        if (posMachines.size() != 1) { return new SymVoucherPurchaseList(AUTH_NON_EXISTENT); }

        sym_auth_user authUser = posMachines.get(0).getAuth_user();

        logger.info(format("Using company %s wallet", authUser.getUser().getWallet().getCompany().getCompany_name()));

        String incomingRequest = format("voucherPurchaseId=%s|imei=%s|pin=%s", voucherPurchaseId, posMachines.get(0).getImei1(), pin);

        sym_request_response_log log = new sym_request_response_log(fromEnum(POS_MACHINE), fromEnum(USER_LOGIN), incomingRequest);

        PosAuthenticationProvider authProvider = new PosAuthenticationProvider(log, posMachines.get(0).getImei1(), authUser.getUser().getUsername(), pin);

        SymResponseObject<sym_auth_user> authResponse = authProvider.authenticateUser();

        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            log.setOutgoing_response(authResponse.getMessage());
            log.setOutgoing_response_time(new Date());
            log.setResponse_code(fromEnum(authResponse.getResponseCode()));
            log.save();
            logger.info(format("Authentication failed. %s", authResponse.getMessage()));
            return new SymVoucherPurchaseList(authResponse.getResponseCode());
        }

        sym_voucher_purchase voucherPurchaseResponse = getEntityManagerRepo().findById(
                sym_voucher_purchase.class, voucherPurchaseId
        );

        if (voucherPurchaseResponse == null) {
            return new SymVoucherPurchaseList(DATA_NOT_FOUND);
        }

        if (!voucherPurchaseResponse.getWallet().getId().equals(authUser.getUser().getWallet().getId())) {
            return new SymVoucherPurchaseList(INPUT_INVALID_WALLET);
        }

        if (!voucherPurchaseResponse.getResponse_code().equals(fromEnum(SUCCESS))) {
            return new SymVoucherPurchaseList(voucherPurchaseResponse.getResponse_code().asSymResponseCode());
        }

        return new SymVoucherPurchaseList(SUCCESS, converterService.toDTO(voucherPurchaseResponse));
    }
}
