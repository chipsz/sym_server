/**
 * Principal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class Principal  implements java.io.Serializable {
    private net.symbiosis.core.integration.voucher.glo.seamless.PrincipalId principalId;

    private String principalName;

    private net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccount[] accounts;

    private String status;

    private String msisdn;

    public Principal() {
    }

    public Principal(
           net.symbiosis.core.integration.voucher.glo.seamless.PrincipalId principalId,
           String principalName,
           net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccount[] accounts,
           String status,
           String msisdn) {
           this.principalId = principalId;
           this.principalName = principalName;
           this.accounts = accounts;
           this.status = status;
           this.msisdn = msisdn;
    }


    /**
     * Gets the principalId value for this Principal.
     * 
     * @return principalId
     */
    public net.symbiosis.core.integration.voucher.glo.seamless.PrincipalId getPrincipalId() {
        return principalId;
    }


    /**
     * Sets the principalId value for this Principal.
     * 
     * @param principalId
     */
    public void setPrincipalId(net.symbiosis.core.integration.voucher.glo.seamless.PrincipalId principalId) {
        this.principalId = principalId;
    }


    /**
     * Gets the principalName value for this Principal.
     * 
     * @return principalName
     */
    public String getPrincipalName() {
        return principalName;
    }


    /**
     * Sets the principalName value for this Principal.
     * 
     * @param principalName
     */
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }


    /**
     * Gets the accounts value for this Principal.
     * 
     * @return accounts
     */
    public net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccount[] getAccounts() {
        return accounts;
    }


    /**
     * Sets the accounts value for this Principal.
     * 
     * @param accounts
     */
    public void setAccounts(net.symbiosis.core.integration.voucher.glo.seamless.PrincipalAccount[] accounts) {
        this.accounts = accounts;
    }


    /**
     * Gets the status value for this Principal.
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Principal.
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Gets the msisdn value for this Principal.
     * 
     * @return msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }


    /**
     * Sets the msisdn value for this Principal.
     * 
     * @param msisdn
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Principal)) return false;
        Principal other = (Principal) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.principalId==null && other.getPrincipalId()==null) || 
             (this.principalId!=null &&
              this.principalId.equals(other.getPrincipalId()))) &&
            ((this.principalName==null && other.getPrincipalName()==null) || 
             (this.principalName!=null &&
              this.principalName.equals(other.getPrincipalName()))) &&
            ((this.accounts==null && other.getAccounts()==null) || 
             (this.accounts!=null &&
              java.util.Arrays.equals(this.accounts, other.getAccounts()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.msisdn==null && other.getMsisdn()==null) || 
             (this.msisdn!=null &&
              this.msisdn.equals(other.getMsisdn())));
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
        if (getPrincipalId() != null) {
            _hashCode += getPrincipalId().hashCode();
        }
        if (getPrincipalName() != null) {
            _hashCode += getPrincipalName().hashCode();
        }
        if (getAccounts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccounts());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getAccounts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getMsisdn() != null) {
            _hashCode += getMsisdn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Principal.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principal"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("principalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "principalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principalId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("principalName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "principalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accounts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principalAccount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "account"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msisdn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "msisdn"));
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
