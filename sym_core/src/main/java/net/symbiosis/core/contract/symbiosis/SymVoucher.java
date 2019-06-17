package net.symbiosis.core.contract.symbiosis;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/
@XmlRootElement
public class SymVoucher implements Serializable {

    private Long voucherId;
    private BigDecimal voucherValue;
    private Boolean isActive;
    private String serviceProvider;
    private String voucherType;
    private String voucherProvider;
    private Double defaultMerchantDiscount;
    private String units;
    private Boolean isFixed;
    private Boolean isPinBased;
    private String description;

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public BigDecimal getVoucherValue() {
        return voucherValue;
    }

    public void setVoucherValue(BigDecimal voucherValue) {
        this.voucherValue = voucherValue;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherProvider() {
        return voucherProvider;
    }

    public void setVoucherProvider(String voucherProvider) {
        this.voucherProvider = voucherProvider;
    }

    public Double getDefaultMerchantDiscount() {
        return defaultMerchantDiscount;
    }

    public void setDefaultMerchantDiscount(Double defaultMerchantDiscount) {
        this.defaultMerchantDiscount = defaultMerchantDiscount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean fixed) {
        isFixed = fixed;
    }

    public Boolean getIsPinBased() {
        return isPinBased;
    }

    public void setIsPinBased(Boolean pinBased) {
        isPinBased = pinBased;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
