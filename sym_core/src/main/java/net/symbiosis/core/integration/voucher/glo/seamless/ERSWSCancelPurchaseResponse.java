/**
 * ERSWSCancelPurchaseResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ERSWSCancelPurchaseResponse  extends ERSWSResponse  implements java.io.Serializable {
    private net.symbiosis.core.integration.voucher.glo.seamless.CancelPurchaseOrderResultRow[] cancelPurchaseOrderResult;

    public ERSWSCancelPurchaseResponse() {
    }

    public ERSWSCancelPurchaseResponse(
           String ersReference,
           int resultCode,
           String resultDescription,
           net.symbiosis.core.integration.voucher.glo.seamless.CancelPurchaseOrderResultRow[] cancelPurchaseOrderResult) {
        super(
            ersReference,
            resultCode,
            resultDescription);
        this.cancelPurchaseOrderResult = cancelPurchaseOrderResult;
    }


    /**
     * Gets the cancelPurchaseOrderResult value for this ERSWSCancelPurchaseResponse.
     * 
     * @return cancelPurchaseOrderResult
     */
    public net.symbiosis.core.integration.voucher.glo.seamless.CancelPurchaseOrderResultRow[] getCancelPurchaseOrderResult() {
        return cancelPurchaseOrderResult;
    }


    /**
     * Sets the cancelPurchaseOrderResult value for this ERSWSCancelPurchaseResponse.
     * 
     * @param cancelPurchaseOrderResult
     */
    public void setCancelPurchaseOrderResult(net.symbiosis.core.integration.voucher.glo.seamless.CancelPurchaseOrderResultRow[] cancelPurchaseOrderResult) {
        this.cancelPurchaseOrderResult = cancelPurchaseOrderResult;
    }

    public net.symbiosis.core.integration.voucher.glo.seamless.CancelPurchaseOrderResultRow getCancelPurchaseOrderResult(int i) {
        return this.cancelPurchaseOrderResult[i];
    }

    public void setCancelPurchaseOrderResult(int i, net.symbiosis.core.integration.voucher.glo.seamless.CancelPurchaseOrderResultRow _value) {
        this.cancelPurchaseOrderResult[i] = _value;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ERSWSCancelPurchaseResponse)) return false;
        ERSWSCancelPurchaseResponse other = (ERSWSCancelPurchaseResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cancelPurchaseOrderResult==null && other.getCancelPurchaseOrderResult()==null) || 
             (this.cancelPurchaseOrderResult!=null &&
              java.util.Arrays.equals(this.cancelPurchaseOrderResult, other.getCancelPurchaseOrderResult())));
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
        if (getCancelPurchaseOrderResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCancelPurchaseOrderResult());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getCancelPurchaseOrderResult(), i);
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
        new org.apache.axis.description.TypeDesc(ERSWSCancelPurchaseResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "ERSWSCancelPurchaseResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancelPurchaseOrderResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cancelPurchaseOrderResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "cancelPurchaseOrderResultRow"));
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
