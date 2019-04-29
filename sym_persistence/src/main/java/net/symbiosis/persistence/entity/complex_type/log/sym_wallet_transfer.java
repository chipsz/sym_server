package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***************************************************************************
 * Created:     19 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "wallet_transfer_id"))
public class sym_wallet_transfer extends sym_entity<sym_wallet_transfer> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_user_id")
    private sym_auth_user auth_user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "wallet_id")
    private sym_wallet recipient_wallet;
    @Basic(optional = false)
    private BigDecimal transfer_amount;
    @Basic(optional = false)
    private BigDecimal previous_balance;
    @Basic
    private BigDecimal new_balance;
    @Basic(optional = false)
    private Date transaction_time;

    public sym_wallet_transfer() {}

    public sym_wallet_transfer(sym_auth_user auth_user, sym_wallet recipient_wallet, BigDecimal transfer_amount,
                               BigDecimal previous_balance, BigDecimal new_balance, Date transaction_time) {
        this.auth_user = auth_user;
        this.recipient_wallet = recipient_wallet;
        this.transfer_amount = transfer_amount;
        this.previous_balance = previous_balance;
        this.new_balance = new_balance;
        this.transaction_time = transaction_time;
    }

    public sym_auth_user getAuth_user() {
        return auth_user;
    }

    public void setAuth_user(sym_auth_user auth_user) {
        this.auth_user = auth_user;
    }

    public sym_wallet getRecipient_wallet() { return recipient_wallet; }

    public void setRecipient_wallet(sym_wallet recipient_wallet) { this.recipient_wallet = recipient_wallet; }

    public BigDecimal getTransfer_amount() { return transfer_amount; }

    public void setTransfer_amount(BigDecimal transfer_amount) { this.transfer_amount = transfer_amount; }

    public BigDecimal getPrevious_balance() { return previous_balance; }

    public void setPrevious_balance(BigDecimal previous_balance) { this.previous_balance = previous_balance; }

    public BigDecimal getNew_balance() { return new_balance; }

    public void setNew_balance(BigDecimal new_balance) { this.new_balance = new_balance; }

    public Date getTransaction_time() { return transaction_time; }

    public void setTransaction_time(Date transaction_time) { this.transaction_time = transaction_time; }
}