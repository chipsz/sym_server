/**
 * ERSCommitTopupResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ERSCommitTopupResponse  extends ERSWSResponse  implements java.io.Serializable {
    private String referredTransactionErsReference;

    private Principal senderPrincipal;

    private Principal topupPrincipal;

    private net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier senderAccountSpecifier;

    private net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier topupAccountSpecifier;

    private Amount topupAmount;

    private String requestedType;

    private Amount subscriberBalanceBefore;

    private Amount subscriberBalanceAfter;

    public ERSCommitTopupResponse() {
    }

    public ERSCommitTopupResponse(
           String ersReference,
           int resultCode,
           String resultDescription,
           String referredTransactionErsReference,
           Principal senderPrincipal,
           Principal topupPrincipal,
           net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier senderAccountSpecifier,
           net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier topupAccountSpecifier,
           Amount topupAmount,
           String requestedType,
           Amount subscriberBalanceBefore,
           Amount subscriberBalanceAfter) {
        super(
            ersReference,
            resultCode,
            resultDescription);
        this.referredTransactionErsReference = referredTransactionErsReference;
        this.senderPrincipal = senderPrincipal;
        this.topupPrincipal = topupPrincipal;
        this.senderAccountSpecifier = senderAccountSpecifier;
        this.topupAccountSpecifier = topupAccountSpecifier;
        this.topupAmount = topupAmount;
        this.requestedType = requestedType;
        this.subscriberBalanceBefore = subscriberBalanceBefore;
        this.subscriberBalanceAfter = subscriberBalanceAfter;
    }


    /**
     * Gets the referredTransactionErsReference value for this ERSCommitTopupResponse.
     * 
     * @return referredTransactionErsReference
     */
    public String getReferredTransactionErsReference() {
        return referredTransactionErsReference;
    }


    /**
     * Sets the referredTransactionErsReference value for this ERSCommitTopupResponse.
     * 
     * @param referredTransactionErsReference
     */
    public void setReferredTransactionErsReference(String referredTransactionErsReference) {
        this.referredTransactionErsReference = referredTransactionErsReference;
    }


    /**
     * Gets the senderPrincipal value for this ERSCommitTopupResponse.
     * 
     * @return senderPrincipal
     */
    public Principal getSenderPrincipal() {
        return senderPrincipal;
    }


    /**
     * Sets the senderPrincipal value for this ERSCommitTopupResponse.
     * 
     * @param senderPrincipal
     */
    public void setSenderPrincipal(Principal senderPrincipal) {
        this.senderPrincipal = senderPrincipal;
    }


    /**
     * Gets the topupPrincipal value for this ERSCommitTopupResponse.
     * 
     * @return topupPrincipal
     */
    public Principal getTopupPrincipal() {
        return topupPrincipal;
    }


    /**
     * Sets the topupPrincipal value for this ERSCommitTopupResponse.
     * 
     * @param topupPrincipal
     */
    public void setTopupPrincipal(Principal topupPrincipal) {
        this.topupPrincipal = topupPrincipal;
    }


    /**
     * Gets the senderAccountSpecifier value for this ERSCommitTopupResponse.
     * 
     * @return senderAccountSpecifier
     */
    public net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier getSenderAccountSpecifier() {
        return senderAccountSpecifier;
    }


    /**
     * Sets the senderAccountSpecifier value for this ERSCommitTopupResponse.
     * 
     * @param senderAccountSpecifier
     */
    public void setSenderAccountSpecifier(net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier senderAccountSpecifier) {
        this.senderAccountSpecifier = senderAccountSpecifier;
    }


    /**
     * Gets the topupAccountSpecifier value for this ERSCommitTopupResponse.
     * 
     * @return topupAccountSpecifier
     */
    public net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier getTopupAccountSpecifier() {
        return topupAccountSpecifier;
    }


    /**
     * Sets the topupAccountSpecifier value for this ERSCommitTopupResponse.
     * 
     * @param topupAccountSpecifier
     */
    public void setTopupAccountSpecifier(net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier topupAccountSpecifier) {
        this.topupAccountSpecifier = topupAccountSpecifier;
    }


    /**
     * Gets the topupAmount value for this ERSCommitTopupResponse.
     * 
     * @return topupAmount
     */
    public Amount getTopupAmount() {
        return topupAmount;
    }


    /**
     * Sets the topupAmount value for this ERSCommitTopupResponse.
     * 
     * @param topupAmount
     */
    public void setTopupAmount(Amount topupAmount) {
        this.topupAmount = topupAmount;
    }


    /**
     * Gets the requestedType value for this ERSCommitTopupResponse.
     * 
     * @return requestedType
     */
    public String getRequestedType() {
        return requestedType;
    }


    /**
     * Sets the requestedType value for this ERSCommitTopupResponse.
     * 
     * @param requestedType
     */
    public void setRequestedType(String requestedType) {
        this.requestedType = requestedType;
    }


    /**
     * Gets the subscriberBalanceBefore value for this ERSCommitTopupResponse.
     * 
     * @return subscriberBalanceBefore
     */
    public Amount getSubscriberBalanceBefore() {
        return subscriberBalanceBefore;
    }


    /**
     * Sets the subscriberBalanceBefore value for this ERSCommitTopupResponse.
     * 
     * @param subscriberBalanceBefore
     */
    public void setSubscriberBalanceBefore(Amount subscriberBalanceBefore) {
        this.subscriberBalanceBefore = subscriberBalanceBefore;
    }


    /**
     * Gets the subscriberBalanceAfter value for this ERSCommitTopupResponse.
     * 
     * @return subscriberBalanceAfter
     */
    public Amount getSubscriberBalanceAfter() {
        return subscriberBalanceAfter;
    }


    /**
     * Sets the subscriberBalanceAfter value for this ERSCommitTopupResponse.
     * 
     * @param subscriberBalanceAfter
     */
    public void setSubscriberBalanceAfter(Amount subscriberBalanceAfter) {
        this.subscriberBalanceAfter = subscriberBalanceAfter;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ERSCommitTopupResponse)) return false;
        ERSCommitTopupResponse other = (ERSCommitTopupResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.referredTransactionErsReference==null && other.getReferredTransactionErsReference()==null) || 
             (this.referredTransactionErsReference!=null &&
              this.referredTransactionErsReference.equals(other.getReferredTransactionErsReference()))) &&
            ((this.senderPrincipal==null && other.getSenderPrincipal()==null) || 
             (this.senderPrincipal!=null &&
              this.senderPrincipal.equals(other.getSenderPrincipal()))) &&
            ((this.topupPrincipal==null && other.getTopupPrincipal()==null) || 
             (this.topupPrincipal!=null &&
              this.topupPrincipal.equals(other.getTopupPrincipal()))) &&
            ((this.senderAccountSpecifier==null && other.getSenderAccountSpecifier()==null) || 
             (this.senderAccountSpecifier!=null &&
              this.senderAccountSpecifier.equals(other.getSenderAccountSpecifier()))) &&
            ((this.topupAccountSpecifier==null && other.getTopupAccountSpecifier()==null) || 
             (this.topupAccountSpecifier!=null &&
              this.topupAccountSpecifier.equals(other.getTopupAccountSpecifier()))) &&
            ((this.topupAmount==null && other.getTopupAmount()==null) || 
             (this.topupAmount!=null &&
              this.topupAmount.equals(other.getTopupAmount()))) &&
            ((this.requestedType==null && other.getRequestedType()==null) || 
             (this.requestedType!=null &&
              this.requestedType.equals(other.getRequestedType()))) &&
            ((this.subscriberBalanceBefore==null && other.getSubscriberBalanceBefore()==null) || 
             (this.subscriberBalanceBefore!=null &&
              this.subscriberBalanceBefore.equals(other.getSubscriberBalanceBefore()))) &&
            ((this.subscriberBalanceAfter==null && other.getSubscriberBalanceAfter()==null) || 
             (this.subscriberBalanceAfter!=null &&
              this.subscriberBalanceAfter.equals(other.getSubscriberBalanceAfter())));
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
        if (getReferredTransactionErsReference() != null) {
            _hashCode += getReferredTransactionErsReference().hashCode();
        }
        if (getSenderPrincipal() != null) {
            _hashCode += getSenderPrincipal().hashCode();
        }
        if (getTopupPrincipal() != null) {
            _hashCode += getTopupPrincipal().hashCode();
        }
        if (getSenderAccountSpecifier() != null) {
            _hashCode += getSenderAccountSpecifier().hashCode();
        }
        if (getTopupAccountSpecifier() != null) {
            _hashCode += getTopupAccountSpecifier().hashCode();
        }
        if (getTopupAmount() != null) {
            _hashCode += getTopupAmount().hashCode();
        }
        if (getRequestedType() != null) {
            _hashCode += getRequestedType().hashCode();
        }
        if (getSubscriberBalanceBefore() != null) {
            _hashCode += getSubscriberBalanceBefore().hashCode();
        }
        if (getSubscriberBalanceAfter() != null) {
            _hashCode += getSubscriberBalanceAfter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ERSCommitTopupResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "ERSCommitTopupResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referredTransactionErsReference");
        elemField.setXmlName(new javax.xml.namespace.QName("", "referredTransactionErsReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "senderPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topupPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topupPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderAccountSpecifier");
        elemField.setXmlName(new javax.xml.namespace.QName("", "senderAccountSpecifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topupAccountSpecifier");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topupAccountSpecifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topupAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topupAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "amount"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestedType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestedType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriberBalanceBefore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subscriberBalanceBefore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "amount"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriberBalanceAfter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subscriberBalanceAfter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "amount"));
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
