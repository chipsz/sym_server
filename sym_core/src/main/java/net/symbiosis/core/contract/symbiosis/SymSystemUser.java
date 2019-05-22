package net.symbiosis.core.contract.symbiosis;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_DEFAULT_COUNTRY_CODE;
import static net.symbiosis.core_lib.utilities.CommonUtilities.format10DigitPhoneNumber;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

/***************************************************************************
 * *
 * Created:     21 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@XmlRootElement
public class SymSystemUser implements Serializable {

    protected Long sessionId;
    protected Long authUserId;
    protected Long userId;
    protected Long walletId;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String email;
    protected String msisdn;
    protected String phoneNumber;
    protected String companyName;
    protected Double walletBalance;
    protected String group;
    protected String deviceId;
    protected String authToken;
    protected Date lastLoginDate;
    protected String userStatus;
    protected String country;
    protected String language;
    protected Date dateOfBirth;

    public SymSystemUser() {
    }

    public SymSystemUser(Long sessionId, sym_auth_user symAuthUser, sym_user symUser) {
        this(sessionId, symAuthUser.getId(), symUser.getId(), symUser.getWallet().getId(), symUser.getFirst_name(),
                symUser.getLast_name(), symUser.getUsername(), symUser.getEmail(), symUser.getMsisdn(),
                format10DigitPhoneNumber(symUser.getMsisdn(), getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)),
                symAuthUser.getUser().getWallet().getCompany().getCompany_name(),
                symAuthUser.getUser().getWallet().getCurrent_balance().doubleValue(),
                symAuthUser.getAuth_group().getName(), symAuthUser.getDevice_id(),
                symAuthUser.getAuth_token(), symAuthUser.getLast_login_date());
    }

    public SymSystemUser(Long sessionId, Long authUserId, Long userId, Long walletId, String firstName, String lastName,
                         String username, String email, String msisdn, String phoneNumber, String companyName, Double walletBalance,
                         String group, String deviceId, String authToken, Date lastLoginDate) {
        this.sessionId = sessionId;
        this.authUserId = authUserId;
        this.userId = userId;
        this.walletId = walletId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.msisdn = msisdn;
        this.companyName = companyName;
        this.walletBalance = walletBalance;
        this.group = group;
        this.deviceId = deviceId;
        this.authToken = authToken;
        this.lastLoginDate = lastLoginDate;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

	public Long getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(Long authUserId) {
		this.authUserId = authUserId;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getWalletBalance() { return walletBalance; }

    public void setWalletBalance(Double walletBalance) { this.walletBalance = walletBalance; }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public SymSystemUser setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
