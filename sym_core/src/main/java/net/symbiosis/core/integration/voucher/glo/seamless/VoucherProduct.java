/**
 * VoucherProduct.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class VoucherProduct  implements java.io.Serializable {
    private Amount productAmount;

    private String productSKU;

    public VoucherProduct() {
    }

    public VoucherProduct(
           Amount productAmount,
           String productSKU) {
           this.productAmount = productAmount;
           this.productSKU = productSKU;
    }


    /**
     * Gets the productAmount value for this VoucherProduct.
     * 
     * @return productAmount
     */
    public Amount getProductAmount() {
        return productAmount;
    }


    /**
     * Sets the productAmount value for this VoucherProduct.
     * 
     * @param productAmount
     */
    public void setProductAmount(Amount productAmount) {
        this.productAmount = productAmount;
    }


    /**
     * Gets the productSKU value for this VoucherProduct.
     * 
     * @return productSKU
     */
    public String getProductSKU() {
        return productSKU;
    }


    /**
     * Sets the productSKU value for this VoucherProduct.
     * 
     * @param productSKU
     */
    public void setProductSKU(String productSKU) {
        this.productSKU = productSKU;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof VoucherProduct)) return false;
        VoucherProduct other = (VoucherProduct) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.productAmount==null && other.getProductAmount()==null) || 
             (this.productAmount!=null &&
              this.productAmount.equals(other.getProductAmount()))) &&
            ((this.productSKU==null && other.getProductSKU()==null) || 
             (this.productSKU!=null &&
              this.productSKU.equals(other.getProductSKU())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getProductAmount() != null) {
            _hashCode += getProductAmount().hashCode();
        }
        if (getProductSKU() != null) {
            _hashCode += getProductSKU().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VoucherProduct.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "voucherProduct"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productSKU");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productSKU"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
