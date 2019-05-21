/**
 * ERSWSTransferResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ERSWSTransferResponse  extends ERSWSResponse  implements java.io.Serializable {
    private Amount receivedAmount;

    private Principal receiverPrincipal;

    private Amount requestedTransferAmount;

    private Principal senderPrincipal;

    public ERSWSTransferResponse() {
    }

    public ERSWSTransferResponse(
           String ersReference,
           int resultCode,
           String resultDescription,
           Amount receivedAmount,
           Principal receiverPrincipal,
           Amount requestedTransferAmount,
           Principal senderPrincipal) {
        super(
            ersReference,
            resultCode,
            resultDescription);
        this.receivedAmount = receivedAmount;
        this.receiverPrincipal = receiverPrincipal;
        this.requestedTransferAmount = requestedTransferAmount;
        this.senderPrincipal = senderPrincipal;
    }


    /**
     * Gets the receivedAmount value for this ERSWSTransferResponse.
     * 
     * @return receivedAmount
     */
    public Amount getReceivedAmount() {
        return receivedAmount;
    }


    /**
     * Sets the receivedAmount value for this ERSWSTransferResponse.
     * 
     * @param receivedAmount
     */
    public void setReceivedAmount(Amount receivedAmount) {
        this.receivedAmount = receivedAmount;
    }


    /**
     * Gets the receiverPrincipal value for this ERSWSTransferResponse.
     * 
     * @return receiverPrincipal
     */
    public Principal getReceiverPrincipal() {
        return receiverPrincipal;
    }


    /**
     * Sets the receiverPrincipal value for this ERSWSTransferResponse.
     * 
     * @param receiverPrincipal
     */
    public void setReceiverPrincipal(Principal receiverPrincipal) {
        this.receiverPrincipal = receiverPrincipal;
    }


    /**
     * Gets the requestedTransferAmount value for this ERSWSTransferResponse.
     * 
     * @return requestedTransferAmount
     */
    public Amount getRequestedTransferAmount() {
        return requestedTransferAmount;
    }


    /**
     * Sets the requestedTransferAmount value for this ERSWSTransferResponse.
     * 
     * @param requestedTransferAmount
     */
    public void setRequestedTransferAmount(Amount requestedTransferAmount) {
        this.requestedTransferAmount = requestedTransferAmount;
    }


    /**
     * Gets the senderPrincipal value for this ERSWSTransferResponse.
     * 
     * @return senderPrincipal
     */
    public Principal getSenderPrincipal() {
        return senderPrincipal;
    }


    /**
     * Sets the senderPrincipal value for this ERSWSTransferResponse.
     * 
     * @param senderPrincipal
     */
    public void setSenderPrincipal(Principal senderPrincipal) {
        this.senderPrincipal = senderPrincipal;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ERSWSTransferResponse)) return false;
        ERSWSTransferResponse other = (ERSWSTransferResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.receivedAmount==null && other.getReceivedAmount()==null) || 
             (this.receivedAmount!=null &&
              this.receivedAmount.equals(other.getReceivedAmount()))) &&
            ((this.receiverPrincipal==null && other.getReceiverPrincipal()==null) || 
             (this.receiverPrincipal!=null &&
              this.receiverPrincipal.equals(other.getReceiverPrincipal()))) &&
            ((this.requestedTransferAmount==null && other.getRequestedTransferAmount()==null) || 
             (this.requestedTransferAmount!=null &&
              this.requestedTransferAmount.equals(other.getRequestedTransferAmount()))) &&
            ((this.senderPrincipal==null && other.getSenderPrincipal()==null) || 
             (this.senderPrincipal!=null &&
              this.senderPrincipal.equals(other.getSenderPrincipal())));
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
        if (getReceivedAmount() != null) {
            _hashCode += getReceivedAmount().hashCode();
        }
        if (getReceiverPrincipal() != null) {
            _hashCode += getReceiverPrincipal().hashCode();
        }
        if (getRequestedTransferAmount() != null) {
            _hashCode += getRequestedTransferAmount().hashCode();
        }
        if (getSenderPrincipal() != null) {
            _hashCode += getSenderPrincipal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ERSWSTransferResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "ERSWSTransferResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receivedAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "receivedAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiverPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "receiverPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestedTransferAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestedTransferAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "senderPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principal"));
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
