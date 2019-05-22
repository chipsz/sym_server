/**
 * ERSWSResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ERSWSResponse  implements java.io.Serializable {
    private String ersReference;
    private int resultCode;
    private String resultDescription;
    private String requestSoapXML;
    private String responseSoapXML;

    public ERSWSResponse() {
    }

    public ERSWSResponse(
           String ersReference,
           int resultCode,
           String resultDescription) {
           this.ersReference = ersReference;
           this.resultCode = resultCode;
           this.resultDescription = resultDescription;
    }


    /**
     * Gets the ersReference value for this ERSWSResponse.
     * 
     * @return ersReference
     */
    public String getErsReference() {
        return ersReference;
    }


    /**
     * Sets the ersReference value for this ERSWSResponse.
     * 
     * @param ersReference
     */
    public void setErsReference(String ersReference) {
        this.ersReference = ersReference;
    }


    /**
     * Gets the resultCode value for this ERSWSResponse.
     * 
     * @return resultCode
     */
    public int getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this ERSWSResponse.
     * 
     * @param resultCode
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the resultDescription value for this ERSWSResponse.
     * 
     * @return resultDescription
     */
    public String getResultDescription() {
        return resultDescription;
    }


    /**
     * Sets the resultDescription value for this ERSWSResponse.
     * 
     * @param resultDescription
     */
    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

	public String getRequestSoapXML() {
		return requestSoapXML;
	}

	public ERSWSResponse setRequestSoapXML(String requestSoapXML) {
		this.requestSoapXML = requestSoapXML;
		return this;
	}

	public String getResponseSoapXML() {
		return responseSoapXML;
	}

	public ERSWSResponse setResponseSoapXML(String responseSoapXML) {
		this.responseSoapXML = responseSoapXML;
		return this;
	}

	private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ERSWSResponse)) return false;
        ERSWSResponse other = (ERSWSResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ersReference==null && other.getErsReference()==null) || 
             (this.ersReference!=null &&
              this.ersReference.equals(other.getErsReference()))) &&
            this.resultCode == other.getResultCode() &&
            ((this.resultDescription==null && other.getResultDescription()==null) || 
             (this.resultDescription!=null &&
              this.resultDescription.equals(other.getResultDescription())));
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
        if (getErsReference() != null) {
            _hashCode += getErsReference().hashCode();
        }
        _hashCode += getResultCode();
        if (getResultDescription() != null) {
            _hashCode += getResultDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ERSWSResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "ERSWSResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ersReference");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ersReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultDescription"));
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
