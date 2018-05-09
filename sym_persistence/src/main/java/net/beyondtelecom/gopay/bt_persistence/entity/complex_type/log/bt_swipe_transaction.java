package net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_financial_institution;
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
@AttributeOverride(name = "id", column = @Column(name = "swipe_transaction_id"))
public class bt_swipe_transaction extends bt_entity<bt_swipe_transaction> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_user_id")
    private bt_auth_user auth_user;
    @Basic
    private String user_reference;
    @Basic
    private String bank_reference;
    @Basic(optional = false)
    private String card_number;
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

    public bt_swipe_transaction(bt_auth_user auth_user, String user_reference, String bank_reference,
                                String card_number, BigDecimal transaction_amount, BigDecimal previous_balance,
                                BigDecimal new_balance, Date transaction_time, bt_response_code transaction_status) {
        this.auth_user = auth_user;
        this.user_reference = user_reference;
        this.bank_reference = bank_reference;
        this.card_number = card_number;
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

    public String getBank_reference() {
        return bank_reference;
    }

    public void setBank_reference(String bank_reference) {
        this.bank_reference = bank_reference;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
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