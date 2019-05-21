/**
 * ERSWSPrincipalInformationResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ERSWSPrincipalInformationResponse  extends ERSWSResponse  implements java.io.Serializable {
    private Principal requestedPrincipal;

    private String accountClass;

    private String languageCode;

    private String language;

    private String DA5Balance;

    private String userId;

    private String nativeResultCode;

    public ERSWSPrincipalInformationResponse() {
    }

    public ERSWSPrincipalInformationResponse(
           String ersReference,
           int resultCode,
           String resultDescription,
           Principal requestedPrincipal,
           String accountClass,
           String languageCode,
           String language,
           String DA5Balance,
           String userId,
           String nativeResultCode) {
        super(
            ersReference,
            resultCode,
            resultDescription);
        this.requestedPrincipal = requestedPrincipal;
        this.accountClass = accountClass;
        this.languageCode = languageCode;
        this.language = language;
        this.DA5Balance = DA5Balance;
        this.userId = userId;
        this.nativeResultCode = nativeResultCode;
    }


    /**
     * Gets the requestedPrincipal value for this ERSWSPrincipalInformationResponse.
     * 
     * @return requestedPrincipal
     */
    public Principal getRequestedPrincipal() {
        return requestedPrincipal;
    }


    /**
     * Sets the requestedPrincipal value for this ERSWSPrincipalInformationResponse.
     * 
     * @param requestedPrincipal
     */
    public void setRequestedPrincipal(Principal requestedPrincipal) {
        this.requestedPrincipal = requestedPrincipal;
    }


    /**
     * Gets the accountClass value for this ERSWSPrincipalInformationResponse.
     * 
     * @return accountClass
     */
    public String getAccountClass() {
        return accountClass;
    }


    /**
     * Sets the accountClass value for this ERSWSPrincipalInformationResponse.
     * 
     * @param accountClass
     */
    public void setAccountClass(String accountClass) {
        this.accountClass = accountClass;
    }


    /**
     * Gets the languageCode value for this ERSWSPrincipalInformationResponse.
     * 
     * @return languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }


    /**
     * Sets the languageCode value for this ERSWSPrincipalInformationResponse.
     * 
     * @param languageCode
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }


    /**
     * Gets the language value for this ERSWSPrincipalInformationResponse.
     * 
     * @return language
     */
    public String getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this ERSWSPrincipalInformationResponse.
     * 
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }


    /**
     * Gets the DA5Balance value for this ERSWSPrincipalInformationResponse.
     * 
     * @return DA5Balance
     */
    public String getDA5Balance() {
        return DA5Balance;
    }


    /**
     * Sets the DA5Balance value for this ERSWSPrincipalInformationResponse.
     * 
     * @param DA5Balance
     */
    public void setDA5Balance(String DA5Balance) {
        this.DA5Balance = DA5Balance;
    }


    /**
     * Gets the userId value for this ERSWSPrincipalInformationResponse.
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ERSWSPrincipalInformationResponse.
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * Gets the nativeResultCode value for this ERSWSPrincipalInformationResponse.
     * 
     * @return nativeResultCode
     */
    public String getNativeResultCode() {
        return nativeResultCode;
    }


    /**
     * Sets the nativeResultCode value for this ERSWSPrincipalInformationResponse.
     * 
     * @param nativeResultCode
     */
    public void setNativeResultCode(String nativeResultCode) {
        this.nativeResultCode = nativeResultCode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ERSWSPrincipalInformationResponse)) return false;
        ERSWSPrincipalInformationResponse other = (ERSWSPrincipalInformationResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.requestedPrincipal==null && other.getRequestedPrincipal()==null) || 
             (this.requestedPrincipal!=null &&
              this.requestedPrincipal.equals(other.getRequestedPrincipal()))) &&
            ((this.accountClass==null && other.getAccountClass()==null) || 
             (this.accountClass!=null &&
              this.accountClass.equals(other.getAccountClass()))) &&
            ((this.languageCode==null && other.getLanguageCode()==null) || 
             (this.languageCode!=null &&
              this.languageCode.equals(other.getLanguageCode()))) &&
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              this.language.equals(other.getLanguage()))) &&
            ((this.DA5Balance==null && other.getDA5Balance()==null) || 
             (this.DA5Balance!=null &&
              this.DA5Balance.equals(other.getDA5Balance()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
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
        if (getRequestedPrincipal() != null) {
            _hashCode += getRequestedPrincipal().hashCode();
        }
        if (getAccountClass() != null) {
            _hashCode += getAccountClass().hashCode();
        }
        if (getLanguageCode() != null) {
            _hashCode += getLanguageCode().hashCode();
        }
        if (getLanguage() != null) {
            _hashCode += getLanguage().hashCode();
        }
        if (getDA5Balance() != null) {
            _hashCode += getDA5Balance().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getNativeResultCode() != null) {
            _hashCode += getNativeResultCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ERSWSPrincipalInformationResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "ERSWSPrincipalInformationResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestedPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestedPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountClass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "languageCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("", "language"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DA5Balance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DA5Balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
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
