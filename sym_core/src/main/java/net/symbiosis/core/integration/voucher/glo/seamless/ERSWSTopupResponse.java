/**
 * ERSWSTopupResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ERSWSTopupResponse  extends ERSWSResponse  implements java.io.Serializable {
    private Amount requestedTopupAmount;

    private Principal senderPrincipal;

    private net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier topupAccountSpecifier;

    private Amount topupAmount;

    private Principal topupPrincipal;

    private String requestType;

    private String annonymousID;

    private String nativeResultCode;

    public ERSWSTopupResponse() {
    }

    public ERSWSTopupResponse(
           String ersReference,
           int resultCode,
           String resultDescription,
           Amount requestedTopupAmount,
           Principal senderPrincipal,
           net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier topupAccountSpecifier,
           Amount topupAmount,
           Principal topupPrincipal,
           String requestType,
           String annonymousID,
           String nativeResultCode) {
        super(
            ersReference,
            resultCode,
            resultDescription);
        this.requestedTopupAmount = requestedTopupAmount;
        this.senderPrincipal = senderPrincipal;
        this.topupAccountSpecifier = topupAccountSpecifier;
        this.topupAmount = topupAmount;
        this.topupPrincipal = topupPrincipal;
        this.requestType = requestType;
        this.annonymousID = annonymousID;
        this.nativeResultCode = nativeResultCode;
    }


    /**
     * Gets the requestedTopupAmount value for this ERSWSTopupResponse.
     * 
     * @return requestedTopupAmount
     */
    public Amount getRequestedTopupAmount() {
        return requestedTopupAmount;
    }


    /**
     * Sets the requestedTopupAmount value for this ERSWSTopupResponse.
     * 
     * @param requestedTopupAmount
     */
    public void setRequestedTopupAmount(Amount requestedTopupAmount) {
        this.requestedTopupAmount = requestedTopupAmount;
    }


    /**
     * Gets the senderPrincipal value for this ERSWSTopupResponse.
     * 
     * @return senderPrincipal
     */
    public Principal getSenderPrincipal() {
        return senderPrincipal;
    }


    /**
     * Sets the senderPrincipal value for this ERSWSTopupResponse.
     * 
     * @param senderPrincipal
     */
    public void setSenderPrincipal(Principal senderPrincipal) {
        this.senderPrincipal = senderPrincipal;
    }


    /**
     * Gets the topupAccountSpecifier value for this ERSWSTopupResponse.
     * 
     * @return topupAccountSpecifier
     */
    public net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier getTopupAccountSpecifier() {
        return topupAccountSpecifier;
    }


    /**
     * Sets the topupAccountSpecifier value for this ERSWSTopupResponse.
     * 
     * @param topupAccountSpecifier
     */
    public void setTopupAccountSpecifier(net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccountSpecifier topupAccountSpecifier) {
        this.topupAccountSpecifier = topupAccountSpecifier;
    }


    /**
     * Gets the topupAmount value for this ERSWSTopupResponse.
     * 
     * @return topupAmount
     */
    public Amount getTopupAmount() {
        return topupAmount;
    }


    /**
     * Sets the topupAmount value for this ERSWSTopupResponse.
     * 
     * @param topupAmount
     */
    public void setTopupAmount(Amount topupAmount) {
        this.topupAmount = topupAmount;
    }


    /**
     * Gets the topupPrincipal value for this ERSWSTopupResponse.
     * 
     * @return topupPrincipal
     */
    public Principal getTopupPrincipal() {
        return topupPrincipal;
    }


    /**
     * Sets the topupPrincipal value for this ERSWSTopupResponse.
     * 
     * @param topupPrincipal
     */
    public void setTopupPrincipal(Principal topupPrincipal) {
        this.topupPrincipal = topupPrincipal;
    }


    /**
     * Gets the requestType value for this ERSWSTopupResponse.
     * 
     * @return requestType
     */
    public String getRequestType() {
        return requestType;
    }


    /**
     * Sets the requestType value for this ERSWSTopupResponse.
     * 
     * @param requestType
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }


    /**
     * Gets the annonymousID value for this ERSWSTopupResponse.
     * 
     * @return annonymousID
     */
    public String getAnnonymousID() {
        return annonymousID;
    }


    /**
     * Sets the annonymousID value for this ERSWSTopupResponse.
     * 
     * @param annonymousID
     */
    public void setAnnonymousID(String annonymousID) {
        this.annonymousID = annonymousID;
    }


    /**
     * Gets the nativeResultCode value for this ERSWSTopupResponse.
     * 
     * @return nativeResultCode
     */
    public String getNativeResultCode() {
        return nativeResultCode;
    }


    /**
     * Sets the nativeResultCode value for this ERSWSTopupResponse.
     * 
     * @param nativeResultCode
     */
    public void setNativeResultCode(String nativeResultCode) {
        this.nativeResultCode = nativeResultCode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ERSWSTopupResponse)) return false;
        ERSWSTopupResponse other = (ERSWSTopupResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.requestedTopupAmount==null && other.getRequestedTopupAmount()==null) || 
             (this.requestedTopupAmount!=null &&
              this.requestedTopupAmount.equals(other.getRequestedTopupAmount()))) &&
            ((this.senderPrincipal==null && other.getSenderPrincipal()==null) || 
             (this.senderPrincipal!=null &&
              this.senderPrincipal.equals(other.getSenderPrincipal()))) &&
            ((this.topupAccountSpecifier==null && other.getTopupAccountSpecifier()==null) || 
             (this.topupAccountSpecifier!=null &&
              this.topupAccountSpecifier.equals(other.getTopupAccountSpecifier()))) &&
            ((this.topupAmount==null && other.getTopupAmount()==null) || 
             (this.topupAmount!=null &&
              this.topupAmount.equals(other.getTopupAmount()))) &&
            ((this.topupPrincipal==null && other.getTopupPrincipal()==null) || 
             (this.topupPrincipal!=null &&
              this.topupPrincipal.equals(other.getTopupPrincipal()))) &&
            ((this.requestType==null && other.getRequestType()==null) || 
             (this.requestType!=null &&
              this.requestType.equals(other.getRequestType()))) &&
            ((this.annonymousID==null && other.getAnnonymousID()==null) || 
             (this.annonymousID!=null &&
              this.annonymousID.equals(other.getAnnonymousID()))) &&
            ((this.nativeResultCode==null && other.getNativeResultCode()==null) || 
             (this.nativeResultCode!=null &&
              this.nativeResultCode.equals(other.getNativeResultCode())));
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
        if (getRequestedTopupAmount() != null) {
            _hashCode += getRequestedTopupAmount().hashCode();
        }
        if (getSenderPrincipal() != null) {
            _hashCode += getSenderPrincipal().hashCode();
        }
        if (getTopupAccountSpecifier() != null) {
            _hashCode += getTopupAccountSpecifier().hashCode();
        }
        if (getTopupAmount() != null) {
            _hashCode += getTopupAmount().hashCode();
        }
        if (getTopupPrincipal() != null) {
            _hashCode += getTopupPrincipal().hashCode();
        }
        if (getRequestType() != null) {
            _hashCode += getRequestType().hashCode();
        }
        if (getAnnonymousID() != null) {
            _hashCode += getAnnonymousID().hashCode();
        }
        if (getNativeResultCode() != null) {
            _hashCode += getNativeResultCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ERSWSTopupResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "ERSWSTopupResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestedTopupAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestedTopupAmount"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topupAccountSpecifier");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topupAccountSpecifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topupAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topupAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topupPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topupPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annonymousID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annonymousID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nativeResultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nativeResultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
