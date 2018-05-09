package net.beyondtelecom.gopay.bt_persistence.entity.complex_type;

import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_financial_institution;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

import javax.persistence.*;

/***************************************************************************
 * Created:     19 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "cashout_account_id"))
public class bt_cashout_account extends bt_entity<bt_cashout_account> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "bt_user_id")
    private bt_user cashout_account_user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "institution_id")
    private bt_financial_institution cashout_institution;
    @Column(nullable = false)
    private String account_nick_name;
    private String account_name;
    @Column(nullable = false)
    private String account_number;
    private String account_branch_code;
    private String account_phone;
    private String account_email;

    public bt_cashout_account() {}

    public bt_cashout_account(bt_user cashout_account_user, bt_financial_institution cashout_institution,
                              String account_nick_name, String account_name, String account_number,
                              String account_branch_code, String account_phone, String account_email) {
        this.cashout_account_user = cashout_account_user;
        this.cashout_institution = cashout_institution;
        this.account_nick_name = account_nick_name;
        this.account_name = account_name;
        this.account_number = account_number;
        this.account_branch_code = account_branch_code;
        this.account_phone = account_phone;
        this.account_email = account_email;
    }

    public bt_user getCashout_account_user() { return cashout_account_user; }

    public void setCashout_account_user(bt_user cashout_account_user) {
        this.cashout_account_user = cashout_account_user;
    }

    public bt_financial_institution getCashout_institution() { return cashout_institution; }

    public void setCashout_institution(bt_financial_institution cashout_institution) {
        this.cashout_institution = cashout_institution;
    }

    public String getAccount_nick_name() { return account_nick_name; }

    public void setAccount_nick_name(String account_nick_name) { this.account_nick_name = account_nick_name; }

    public String getAccount_name() { return account_name; }

    public void setAccount_name(String account_name) { this.account_name = account_name; }

    public String getAccount_number() { return account_number; }

    public void setAccount_number(String account_number) { this.account_number = account_number; }

    public String getAccount_branch_code() { return account_branch_code; }

    public void setAccount_branch_code(String account_branch_code) { this.account_branch_code = account_branch_code; }

    public String getAccount_phone() { return account_phone; }

    public void setAccount_phone(String account_phone) { this.account_phone = account_phone; }

    public String getAccount_email() { return account_email; }

    public void setAccount_email(String account_email) { this.account_email = account_email; }

    @Override
    public String toString() { return account_nick_name; }
}