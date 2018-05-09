package net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_cashout_account;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***************************************************************************
 * Created:     19 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "cashout_transaction_id"))
public class bt_cashout_transaction extends bt_entity<bt_cashout_transaction> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_user_id")
    private bt_auth_user auth_user;
    @Basic
    private String user_reference;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cashout_account_id")
    private bt_cashout_account cashout_account;
    @Basic(optional = false)
    private BigDecimal transaction_amount;
    @Basic(optional = false)
    private BigDecimal previous_balance;
    @Basic
    private BigDecimal new_balance;
    @Basic(optional = false)
    private Date transaction_time;
    @ManyToOne
    private bt_response_code transaction_status;

    public bt_cashout_transaction(bt_auth_user auth_user, String user_reference, bt_cashout_account cashout_account,
                                  BigDecimal transaction_amount, BigDecimal previous_balance, BigDecimal new_balance,
                                  Date transaction_time, bt_response_code transaction_status) {
        this.auth_user = auth_user;
        this.user_reference = user_reference;
        this.cashout_account = cashout_account;
        this.transaction_amount = transaction_amount;
        this.previous_balance = previous_balance;
        this.new_balance = new_balance;
        this.transaction_time = transaction_time;
        this.transaction_status = transaction_status;
    }

    public bt_auth_user getAuth_user() {
        return auth_user;
    }

    public void setAuth_user(bt_auth_user auth_user) {
        this.auth_user = auth_user;
    }

    public String getUser_reference() {
        return user_reference;
    }

    public void setUser_reference(String user_reference) {
        this.user_reference = user_reference;
    }

    public bt_cashout_account getCashout_account() {
        return cashout_account;
    }

    public void setCashout_account(bt_cashout_account cashout_account) {
        this.cashout_account = cashout_account;
    }

    public BigDecimal getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(BigDecimal transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public BigDecimal getPrevious_balance() {
        return previous_balance;
    }

    public void setPrevious_balance(BigDecimal previous_balance) {
        this.previous_balance = previous_balance;
    }

    public BigDecimal getNew_balance() {
        return new_balance;
    }

    public void setNew_balance(BigDecimal new_balance) {
        this.new_balance = new_balance;
    }

    public Date getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(Date transaction_time) {
        this.transaction_time = transaction_time;
    }

    public bt_response_code getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(bt_response_code transaction_status) {
        this.transaction_status = transaction_status;
    }
}