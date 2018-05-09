package net.beyondtelecom.gopay.bt_core.contract.gopay;

import net.beyondtelecom.gopay.bt_common.utilities.PrintableStringClass;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***************************************************************************
 * Created:     05 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class BTVoucherPurchase implements Serializable, PrintableStringClass {

    private Long voucherPurchaseId;
    private Long voucherId;
    private String voucherPin;
    private String voucherSerial;
    private BigDecimal voucherValue;
    private Date transactionTime;
    private String recipient;
    private String serviceProvider;
    private String voucherType;
    private String units;

    public BTVoucherPurchase(){}

    public BTVoucherPurchase(Long voucherPurchaseId, Long voucherId, String voucherPin, String voucherSerial,
                             BigDecimal voucherValue, Date transactionTime, String recipient, String serviceProvider,
                             String voucherType, String units) {
        this.voucherPurchaseId = voucherPurchaseId;
        this.voucherId = voucherId;
        this.voucherPin = voucherPin;
        this.voucherSerial = voucherSerial;
        this.voucherValue = voucherValue;
        this.transactionTime = transactionTime;
        this.recipient = recipient;
        this.serviceProvider = serviceProvider;
        this.voucherType = voucherType;
        this.units = units;
    }

    public Long getVoucherPurchaseId() { return voucherPurchaseId; }

    public void setVoucherPurchaseId(Long voucherPurchaseId) { this.voucherPurchaseId = voucherPurchaseId; }

    public Long getVoucherId() { return voucherId; }

    public void setVoucherId(Long voucherId) { this.voucherId = voucherId; }

    public String getVoucherPin() { return voucherPin; }

    public void setVoucherPin(String voucherPin) { this.voucherPin = voucherPin; }

    public String getVoucherSerial() { return voucherSerial; }

    public void setVoucherSerial(String voucherSerial) { this.voucherSerial = voucherSerial; }

    public BigDecimal getVoucherValue() { return voucherValue; }

    public void setVoucherValue(BigDecimal voucherValue) { this.voucherValue = voucherValue; }

    public Date getTransactionTime() { return transactionTime; }

    public void setTransactionTime(Date transactionTime) { this.transactionTime = transactionTime; }

    public String getRecipient() { return recipient; }

    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getServiceProvider() { return serviceProvider; }

    public void setServiceProvider(String serviceProvider) { this.serviceProvider = serviceProvider; }

    public String getVoucherType() { return voucherType; }

    public void setVoucherType(String voucherType) { this.voucherType = voucherType; }

    public String getUnits() { return units; }

    public void setUnits(String units) { this.units = units; }

    @Override
    public String toString() {
        return this.toPrintableString();
    }
}
