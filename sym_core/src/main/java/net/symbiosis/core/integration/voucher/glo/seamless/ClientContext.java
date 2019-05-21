/**
 * ClientContext.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ClientContext  implements java.io.Serializable {
    private String channel;

    private String clientComment;

    private String clientId;

    private Boolean prepareOnly;

    private String clientReference;

    private long clientRequestTimeout;

    private PrincipalId initiatorPrincipalId;

    private String password;

    private Parameter[] transactionProperties;

    public ClientContext() {
    }

    public ClientContext(
           String channel,
           String clientComment,
           String clientId,
           Boolean prepareOnly,
           String clientReference,
           long clientRequestTimeout,
           PrincipalId initiatorPrincipalId,
           String password,
           Parameter[] transactionProperties) {
           this.channel = channel;
           this.clientComment = clientComment;
           this.clientId = clientId;
           this.prepareOnly = prepareOnly;
           this.clientReference = clientReference;
           this.clientRequestTimeout = clientRequestTimeout;
           this.initiatorPrincipalId = initiatorPrincipalId;
           this.password = password;
           this.transactionProperties = transactionProperties;
    }


    /**
     * Gets the channel value for this ClientContext.
     * 
     * @return channel
     */
    public String getChannel() {
        return channel;
    }


    /**
     * Sets the channel value for this ClientContext.
     * 
     * @param channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }


    /**
     * Gets the clientComment value for this ClientContext.
     * 
     * @return clientComment
     */
    public String getClientComment() {
        return clientComment;
    }


    /**
     * Sets the clientComment value for this ClientContext.
     * 
     * @param clientComment
     */
    public void setClientComment(String clientComment) {
        this.clientComment = clientComment;
    }


    /**
     * Gets the clientId value for this ClientContext.
     * 
     * @return clientId
     */
    public String getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this ClientContext.
     * 
     * @param clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    /**
     * Gets the prepareOnly value for this ClientContext.
     * 
     * @return prepareOnly
     */
    public Boolean getPrepareOnly() {
        return prepareOnly;
    }


    /**
     * Sets the prepareOnly value for this ClientContext.
     * 
     * @param prepareOnly
     */
    public void setPrepareOnly(Boolean prepareOnly) {
        this.prepareOnly = prepareOnly;
    }


    /**
     * Gets the clientReference value for this ClientContext.
     * 
     * @return clientReference
     */
    public String getClientReference() {
        return clientReference;
    }


    /**
     * Sets the clientReference value for this ClientContext.
     * 
     * @param clientReference
     */
    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }


    /**
     * Gets the clientRequestTimeout value for this ClientContext.
     * 
     * @return clientRequestTimeout
     */
    public long getClientRequestTimeout() {
        return clientRequestTimeout;
    }


    /**
     * Sets the clientRequestTimeout value for this ClientContext.
     * 
     * @param clientRequestTimeout
     */
    public void setClientRequestTimeout(long clientRequestTimeout) {
        this.clientRequestTimeout = clientRequestTimeout;
    }


    /**
     * Gets the initiatorPrincipalId value for this ClientContext.
     * 
     * @return initiatorPrincipalId
     */
    public PrincipalId getInitiatorPrincipalId() {
        return initiatorPrincipalId;
    }


    /**
     * Sets the initiatorPrincipalId value for this ClientContext.
     * 
     * @param initiatorPrincipalId
     */
    public void setInitiatorPrincipalId(PrincipalId initiatorPrincipalId) {
        this.initiatorPrincipalId = initiatorPrincipalId;
    }


    /**
     * Gets the password value for this ClientContext.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this ClientContext.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Gets the transactionProperties value for this ClientContext.
     * 
     * @return transactionProperties
     */
    public Parameter[] getTransactionProperties() {
        return transactionProperties;
    }


    /**
     * Sets the transactionProperties value for this ClientContext.
     * 
     * @param transactionProperties
     */
    public void setTransactionProperties(Parameter[] transactionProperties) {
        this.transactionProperties = transactionProperties;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ClientContext)) return false;
        ClientContext other = (ClientContext) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.channel==null && other.getChannel()==null) || 
             (this.channel!=null &&
              this.channel.equals(other.getChannel()))) &&
            ((this.clientComment==null && other.getClientComment()==null) || 
             (this.clientComment!=null &&
              this.clientComment.equals(other.getClientComment()))) &&
            ((this.clientId==null && other.getClientId()==null) || 
             (this.clientId!=null &&
              this.clientId.equals(other.getClientId()))) &&
            ((this.prepareOnly==null && other.getPrepareOnly()==null) || 
             (this.prepareOnly!=null &&
              this.prepareOnly.equals(other.getPrepareOnly()))) &&
            ((this.clientReference==null && other.getClientReference()==null) || 
             (this.clientReference!=null &&
              this.clientReference.equals(other.getClientReference()))) &&
            this.clientRequestTimeout == other.getClientRequestTimeout() &&
            ((this.initiatorPrincipalId==null && other.getInitiatorPrincipalId()==null) || 
             (this.initiatorPrincipalId!=null &&
              this.initiatorPrincipalId.equals(other.getInitiatorPrincipalId()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.transactionProperties==null && other.getTransactionProperties()==null) || 
             (this.transactionProperties!=null &&
              java.util.Arrays.equals(this.transactionProperties, other.getTransactionProperties())));
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
        if (getChannel() != null) {
            _hashCode += getChannel().hashCode();
        }
        if (getClientComment() != null) {
            _hashCode += getClientComment().hashCode();
        }
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getPrepareOnly() != null) {
            _hashCode += getPrepareOnly().hashCode();
        }
        if (getClientReference() != null) {
            _hashCode += getClientReference().hashCode();
        }
        _hashCode += new Long(getClientRequestTimeout()).hashCode();
        if (getInitiatorPrincipalId() != null) {
            _hashCode += getInitiatorPrincipalId().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getTransactionProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransactionProperties());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getTransactionProperties(), i);
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
        new org.apache.axis.description.TypeDesc(ClientContext.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "clientContext"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientComment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientComment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prepareOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prepareOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientReference");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientRequestTimeout");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientRequestTimeout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initiatorPrincipalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "initiatorPrincipalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "principalId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionProperties");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transactionProperties"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "parameter"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
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
