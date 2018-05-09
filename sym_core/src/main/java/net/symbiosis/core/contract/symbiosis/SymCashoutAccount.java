package net.symbiosis.core.contract.symbiosis;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     22 / 01 / 2017                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class SymCashoutAccount implements Serializable {

    private Long cashoutAccountId;
    private Long institutionId;
    private String accountNickName;
    private String accountName;
    private String accountNumber;
    private String accountBranchCode;
    private String accountPhone;
    private String accountEmail;

    public SymCashoutAccount() {
    }

    public SymCashoutAccount(Long cashoutAccountId, Long institutionId, String accountNickName, String accountName,
                             String accountNumber, String accountBranchCode, String accountPhone, String accountEmail) {
        this.cashoutAccountId = cashoutAccountId;
        this.institutionId = institutionId;
        this.accountNickName = accountNickName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.accountBranchCode = accountBranchCode;
        this.accountPhone = accountPhone;
        this.accountEmail = accountEmail;
    }

    public Long getCashoutAccountId() {
        return cashoutAccountId;
    }

    public void setCashoutAccountId(Long cashoutAccountId) {
        this.cashoutAccountId = cashoutAccountId;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public String getAccountNickName() {
        return accountNickName;
    }

    public void setAccountNickName(String accountNickName) {
        this.accountNickName = accountNickName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountBranchCode() {
        return accountBranchCode;
    }

    public void setAccountBranchCode(String accountBranchCode) {
        this.accountBranchCode = accountBranchCode;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }
}