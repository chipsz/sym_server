package net.beyondtelecom.gopay.bt_core.contract.gopay;

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
public class BTImportBatch implements Serializable {

    private String voucherProviderBatchId;
    private Long voucherId;
    private BigDecimal voucherValue;
    private String voucherProvider;
    private String serviceProvider;
    private String voucherType;
    private String filename;
    private Date importDateTime;
    private Long numberOfVouchers;

    public BTImportBatch() {}

    public BTImportBatch(String voucherProviderBatchId, Long voucherId, BigDecimal voucherValue, String voucherProvider,
                         String serviceProvider, String voucherType, String filename, Date importDateTime,
                         Long numberOfVouchers) {
        this.voucherProviderBatchId = voucherProviderBatchId;
        this.voucherId = voucherId;
        this.voucherValue = voucherValue;
        this.voucherProvider = voucherProvider;
        this.serviceProvider = serviceProvider;
        this.voucherType = voucherType;
        this.filename = filename;
        this.importDateTime = importDateTime;
        this.numberOfVouchers = numberOfVouchers;
    }

    public String getVoucherProviderBatchId() { return voucherProviderBatchId; }

    public void setVoucherProviderBatchId(String voucherProviderBatchId) {
        this.voucherProviderBatchId = voucherProviderBatchId;
    }

    public Long getVoucherId() { return voucherId; }

    public void setVoucherId(Long voucherId) { this.voucherId = voucherId; }

    public BigDecimal getVoucherValue() { return voucherValue; }

    public void setVoucherValue(BigDecimal voucherValue) { this.voucherValue = voucherValue; }

    public String getVoucherProvider() { return voucherProvider; }

    public void setVoucherProvider(String voucherProvider) { this.voucherProvider = voucherProvider; }

    public String getServiceProvider() { return serviceProvider; }

    public void setServiceProvider(String serviceProvider) { this.serviceProvider = serviceProvider; }

    public String getVoucherType() { return voucherType; }

    public void setVoucherType(String voucherType) { this.voucherType = voucherType; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public Date getImportDateTime() { return importDateTime; }

    public void setImportDateTime(Date importDateTime) { this.importDateTime = importDateTime; }

    public Long getNumberOfVouchers() { return numberOfVouchers; }

    public void setNumberOfVouchers(Long numberOfVouchers) { this.numberOfVouchers = numberOfVouchers; }
}
