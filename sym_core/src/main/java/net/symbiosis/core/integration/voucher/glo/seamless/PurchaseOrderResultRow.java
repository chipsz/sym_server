/**
 * PurchaseOrderResultRow.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class PurchaseOrderResultRow  extends PurchaseOrderRow  implements java.io.Serializable {
    private ProductDetail[] productDetails;

    public PurchaseOrderResultRow() {
    }

    public PurchaseOrderResultRow(
           ProductSpecifier[] productSpecifier,
           int purchaseCount,
           ProductDetail[] productDetails) {
        super(
            productSpecifier,
            purchaseCount);
        this.productDetails = productDetails;
    }


    /**
     * Gets the productDetails value for this PurchaseOrderResultRow.
     * 
     * @return productDetails
     */
    public ProductDetail[] getProductDetails() {
        return productDetails;
    }


    /**
     * Sets the productDetails value for this PurchaseOrderResultRow.
     * 
     * @param productDetails
     */
    public void setProductDetails(ProductDetail[] productDetails) {
        this.productDetails = productDetails;
    }

    public ProductDetail getProductDetails(int i) {
        return this.productDetails[i];
    }

    public void setProductDetails(int i, ProductDetail _value) {
        this.productDetails[i] = _value;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof PurchaseOrderResultRow)) return false;
        PurchaseOrderResultRow other = (PurchaseOrderResultRow) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.productDetails==null && other.getProductDetails()==null) || 
             (this.productDetails!=null &&
              java.util.Arrays.equals(this.productDetails, other.getProductDetails())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getProductDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProductDetails());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getProductDetails(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PurchaseOrderResultRow.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "purchaseOrderResultRow"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "productDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
