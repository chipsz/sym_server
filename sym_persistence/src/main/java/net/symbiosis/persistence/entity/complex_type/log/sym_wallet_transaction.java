package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.sym_event_type;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***************************************************************************
 *                                                                         *
 * Created:     29 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "wallet_transaction_id"))
public class sym_wallet_transaction extends sym_entity<sym_wallet_transaction> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "wallet_id")
    private sym_wallet wallet;
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_type_id")
    private sym_event_type event_type;
    @Column(nullable = false)
    private BigDecimal transaction_amount;
    @Column(nullable = false)
    private String transaction_description;
    @Column(nullable = false)
    private Long transaction_link_reference;
    @Column(nullable = false)
    private Date transaction_time;

    public sym_wallet_transaction() {}

    public sym_wallet_transaction(sym_wallet wallet, sym_event_type event_type, BigDecimal transaction_amount,
                                  String transaction_description, Long transaction_link_reference,
                                  Date transaction_time) {
        this.wallet = wallet;
        this.event_type = event_type;
        this.transaction_amount = transaction_amount;
        this.transaction_description = transaction_description;
        this.transaction_link_reference = transaction_link_reference;
        this.transaction_time = transaction_time;
    }

    public sym_wallet getWallet() { return wallet; }

    public void setWallet(sym_wallet wallet) { this.wallet = wallet; }

    public sym_event_type getEvent_type() { return event_type; }

    public void setEvent_type(sym_event_type event_type) { this.event_type = event_type; }

    public BigDecimal getTransaction_amount() { return transaction_amount; }

    public void setTransaction_amount(BigDecimal transaction_amount) { this.transaction_amount = transaction_amount; }

    public String getTransaction_description() { return transaction_description; }

    public void setTransaction_description(String transaction_description) { this.transaction_description = transaction_description; }

    public Long getTransaction_link_reference() { return transaction_link_reference; }

    public void setTransaction_link_reference(Long transaction_link_reference) { this.transaction_link_reference = transaction_link_reference; }

    public Date getTransaction_time() { return transaction_time; }

    public void setTransaction_time(Date transaction_time) { this.transaction_time = transaction_time; }
}
