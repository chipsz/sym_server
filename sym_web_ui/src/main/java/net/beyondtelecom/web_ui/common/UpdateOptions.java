package net.beyondtelecom.web_ui.common;

import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_company;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_wallet_group;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.*;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_deposit_type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
public class UpdateOptions implements Serializable {

    private List<Boolean> booleanList = new ArrayList<>(asList(true, false));
    private List<bt_country> countries;
    private List<bt_language> languages;
    private List<bt_deposit_type> depositTypes;
    private List<bt_auth_group> authGroups;
    private List<bt_wallet_group> walletGroups;
    private List<bt_channel> channels;
    private List<bt_event_type> eventTypes;
    private List<bt_response_code> userStatuses;
    private List<bt_response_code> transactionStatuses;
    private List<bt_response_code> logStatuses;
    private List<bt_company> companies;

    UpdateOptions() {
        initCountries();
        initLanguages();
        initDepositTypes();
        initAuthGroups();
        initChannels();
        initEventTypes();
        initUserStatuses();
        initTransactionStatuses();
        initLogStatuses();
        initWalletGroups();
        initCompanies();
    }

    private List<bt_country> initCountries() {
        countries = getEntityManagerRepo().findAll(bt_country.class);
        return countries;
    }

    private List<bt_language> initLanguages() {
        languages = getEntityManagerRepo().findAll(bt_language.class);
        return languages;
    }

    private List<bt_deposit_type> initDepositTypes() {
        depositTypes = getEntityManagerRepo().findAll(bt_deposit_type.class);
        return depositTypes;
    }

    private List<bt_auth_group> initAuthGroups() {
        authGroups = getEntityManagerRepo().findAll(bt_auth_group.class);
        return authGroups;
    }

    private List<bt_channel> initChannels() {
        channels = getEntityManagerRepo().findAll(bt_channel.class);
        return channels;
    }

    private List<bt_event_type> initEventTypes() {
        eventTypes = getEntityManagerRepo().findAll(bt_event_type.class);
        return eventTypes;
    }

    private List<bt_response_code> initUserStatuses() {
        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();
        searchTerms.add(new Pair<>("name", "ACC_ACTIVE"));
        searchTerms.add(new Pair<>("name", "ACC_INACTIVE"));
        searchTerms.add(new Pair<>("name", "ACC_SUSPENDED"));
        searchTerms.add(new Pair<>("name", "ACC_CLOSED"));
        searchTerms.add(new Pair<>("name", "ACC_PASSWORD_TRIES_EXCEEDED"));
        searchTerms.add(new Pair<>("name", "ACC_PASSWORD_EXPIRED"));
        userStatuses = getEntityManagerRepo().findWhere(bt_response_code.class, searchTerms,
            false, false, true, false);
        return userStatuses;
    }

    private List<bt_response_code> initTransactionStatuses() {
        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();
        searchTerms.add(new Pair<>("name", "SUCCESS"));
        searchTerms.add(new Pair<>("name", "GENERAL_ERROR"));
        searchTerms.add(new Pair<>("name", "AUTH_AUTHENTICATION_FAILED"));
        searchTerms.add(new Pair<>("name", "INSUFFICIENT_FUNDS"));
        searchTerms.add(new Pair<>("name", "INSUFFICIENT_STOCK"));
        searchTerms.add(new Pair<>("name", "INPUT_INCOMPLETE_REQUEST"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_AMOUNT"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_WALLET"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_CASHIER"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_REQUEST"));
        searchTerms.add(new Pair<>("name", "NOT_SUPPORTED"));
        transactionStatuses = getEntityManagerRepo().findWhere(bt_response_code.class, searchTerms,
            false, false, true, false);
        return transactionStatuses;
    }

    private List<bt_response_code> initLogStatuses() {
        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();
        searchTerms.add(new Pair<>("name", "SUCCESS"));
        searchTerms.add(new Pair<>("name", "GENERAL_ERROR"));
        searchTerms.add(new Pair<>("name", "AUTH_AUTHENTICATION_FAILED"));
        searchTerms.add(new Pair<>("name", "DATA_NOT_FOUND"));
        searchTerms.add(new Pair<>("name", "NOT_SUPPORTED"));
        searchTerms.add(new Pair<>("name", "AUTH_INSUFFICIENT_PRIVILEGES"));
        searchTerms.add(new Pair<>("name", "AUTH_AUTHENTICATION_FAILED"));
        searchTerms.add(new Pair<>("name", "AUTH_INCORRECT_PASSWORD"));
        searchTerms.add(new Pair<>("name", "AUTH_NON_EXISTENT"));
        searchTerms.add(new Pair<>("name", "EXISTING_DATA_FOUND"));
        searchTerms.add(new Pair<>("name", "REGISTRATION_FAILED"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_USERNAME_FOUND"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_MSISDN_FOUND"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_EMAIL_FOUND"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_REGISTRATION_FOUND"));
        logStatuses = getEntityManagerRepo().findWhere(bt_response_code.class, searchTerms,
            false, false, true, false);
        return logStatuses;
    }

    private List<bt_wallet_group> initWalletGroups() {
        walletGroups = getEntityManagerRepo().findAll(bt_wallet_group.class);
        return walletGroups;
    }

    private List<bt_company> initCompanies() {
        companies = getEntityManagerRepo().findAll(bt_company.class);
        return companies;
    }

    public List<Boolean> getBooleanList() { return booleanList; }

    public List<bt_country> getCountries() { return countries; }

    public List<bt_language> getLanguages() { return languages; }

    public List<bt_deposit_type> getDepositTypes() { return depositTypes; }

    public List<bt_auth_group> getAuthGroups() { return authGroups; }

    public List<bt_channel> getChannels() { return channels; }

    public List<bt_event_type> getEventTypes() { return eventTypes; }

    public List<bt_response_code> getUserStatuses() { return userStatuses; }

    public List<bt_response_code> getTransactionStatuses() { return transactionStatuses; }

    public List<bt_response_code> getLogStatuses() { return logStatuses; }

    public List<bt_wallet_group> getWalletGroups() { return walletGroups; }

    public List<bt_company> getCompanies() { return companies; }
}
