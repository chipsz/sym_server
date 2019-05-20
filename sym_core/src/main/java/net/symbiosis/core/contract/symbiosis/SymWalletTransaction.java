package net.symbiosis.core.contract.symbiosis;

import net.symbiosis.core_lib.interfaces.PrintableStringClass;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@XmlRootElement
public class SymWalletTransaction implements Serializable, PrintableStringClass {
    private Long walletId;
    private String eventType;
    private BigDecimal transactionAmount;
    private String transactionDescription;
    private Date transactionTime;

    public SymWalletTransaction() {}

    public SymWalletTransaction(Long walletId, String eventType, BigDecimal transactionAmount, String transactionDescription, Date transactionTime) {
        this.walletId = walletId;
        this.eventType = eventType;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.transactionTime = transactionTime;
    }

    public Long getWalletId() { return walletId; }

    public void setWalletId(Long walletId) { this.walletId = walletId; }

    public String getEventType() { return eventType; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    public BigDecimal getTransactionAmount() { return transactionAmount; }

    public void setTransactionAmount(BigDecimal transactionAmount) { this.transactionAmount = transactionAmount; }

    public String getTransactionDescription() { return transactionDescription; }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public Date getTransactionTime() { return transactionTime; }

    public void setTransactionTime(Date transactionTime) { this.transactionTime = transactionTime; }

    @Override
    public String toString() {
        return this.toPrintableString();
    }
}
