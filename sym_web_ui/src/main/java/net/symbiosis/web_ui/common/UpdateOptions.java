package net.symbiosis.web_ui.common;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.sym_company;
import net.symbiosis.persistence.entity.complex_type.sym_wallet_group;
import net.symbiosis.persistence.entity.enumeration.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

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
    private List<sym_country> countries;
    private List<sym_language> languages;
    private List<sym_deposit_type> depositTypes;
    private List<sym_auth_group> authGroups;
    private List<sym_wallet_group> walletGroups;
    private List<sym_channel> channels;
    private List<sym_event_type> eventTypes;
    private List<sym_response_code> userStatuses;
    private List<sym_response_code> transactionStatuses;
    private List<sym_response_code> logStatuses;
    private List<sym_company> companies;

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

    private List<sym_country> initCountries() {
        countries = getEntityManagerRepo().findAll(sym_country.class);
        return countries;
    }

    private List<sym_language> initLanguages() {
        languages = getEntityManagerRepo().findAll(sym_language.class);
        return languages;
    }

    private List<sym_deposit_type> initDepositTypes() {
        depositTypes = getEntityManagerRepo().findAll(sym_deposit_type.class);
        return depositTypes;
    }

    private List<sym_auth_group> initAuthGroups() {
        authGroups = getEntityManagerRepo().findAll(sym_auth_group.class);
        return authGroups;
    }

    private List<sym_channel> initChannels() {
        channels = getEntityManagerRepo().findAll(sym_channel.class);
        return channels;
    }

    private List<sym_event_type> initEventTypes() {
        eventTypes = getEntityManagerRepo().findAll(sym_event_type.class);
        return eventTypes;
    }

    private List<sym_response_code> initUserStatuses() {
        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();
        searchTerms.add(new Pair<>("name", "ACC_ACTIVE"));
        searchTerms.add(new Pair<>("name", "ACC_INACTIVE"));
        searchTerms.add(new Pair<>("name", "ACC_SUSPENDED"));
        searchTerms.add(new Pair<>("name", "ACC_CLOSED"));
        searchTerms.add(new Pair<>("name", "ACC_PASSWORD_TRIES_EXCEEDED"));
        searchTerms.add(new Pair<>("name", "ACC_PASSWORD_EXPIRED"));
        userStatuses = getEntityManagerRepo().findWhere(sym_response_code.class, searchTerms,
                false, false, true, false);
        return userStatuses;
    }

    private List<sym_response_code> initTransactionStatuses() {
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
        transactionStatuses = getEntityManagerRepo().findWhere(sym_response_code.class, searchTerms,
                false, false, true, false);
        return transactionStatuses;
    }

    private List<sym_response_code> initLogStatuses() {
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
        logStatuses = getEntityManagerRepo().findWhere(sym_response_code.class, searchTerms,
                false, false, true, false);
        return logStatuses;
    }

    private List<sym_wallet_group> initWalletGroups() {
        walletGroups = getEntityManagerRepo().findAll(sym_wallet_group.class);
        return walletGroups;
    }

    private List<sym_company> initCompanies() {
        companies = getEntityManagerRepo().findAll(sym_company.class);
        return companies;
    }

    public List<Boolean> getBooleanList() {
        return booleanList;
    }

    public List<sym_country> getCountries() {
        return countries;
    }

    public List<sym_language> getLanguages() {
        return languages;
    }

    public List<sym_deposit_type> getDepositTypes() {
        return depositTypes;
    }

    public List<sym_auth_group> getAuthGroups() {
        return authGroups;
    }

    public List<sym_channel> getChannels() {
        return channels;
    }

    public List<sym_event_type> getEventTypes() {
        return eventTypes;
    }

    public List<sym_response_code> getUserStatuses() {
        return userStatuses;
    }

    public List<sym_response_code> getTransactionStatuses() {
        return transactionStatuses;
    }

    public List<sym_response_code> getLogStatuses() {
        return logStatuses;
    }

    public List<sym_wallet_group> getWalletGroups() {
        return walletGroups;
    }

    public List<sym_company> getCompanies() {
        return companies;
    }
}
