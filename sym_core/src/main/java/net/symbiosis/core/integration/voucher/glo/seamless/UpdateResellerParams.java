/**
 * UpdateResellerParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class UpdateResellerParams  implements java.io.Serializable {
    private String resellerMsisdn;

    private AddressData address;

    private PersonData person;

    public UpdateResellerParams() {
    }

    public UpdateResellerParams(
           String resellerMsisdn,
           AddressData address,
           PersonData person) {
           this.resellerMsisdn = resellerMsisdn;
           this.address = address;
           this.person = person;
    }


    /**
     * Gets the resellerMsisdn value for this UpdateResellerParams.
     * 
     * @return resellerMsisdn
     */
    public String getResellerMsisdn() {
        return resellerMsisdn;
    }


    /**
     * Sets the resellerMsisdn value for this UpdateResellerParams.
     * 
     * @param resellerMsisdn
     */
    public void setResellerMsisdn(String resellerMsisdn) {
        this.resellerMsisdn = resellerMsisdn;
    }


    /**
     * Gets the address value for this UpdateResellerParams.
     * 
     * @return address
     */
    public AddressData getAddress() {
        return address;
    }


    /**
     * Sets the address value for this UpdateResellerParams.
     * 
     * @param address
     */
    public void setAddress(AddressData address) {
        this.address = address;
    }


    /**
     * Gets the person value for this UpdateResellerParams.
     * 
     * @return person
     */
    public PersonData getPerson() {
        return person;
    }


    /**
     * Sets the person value for this UpdateResellerParams.
     * 
     * @param person
     */
    public void setPerson(PersonData person) {
        this.person = person;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateResellerParams)) return false;
        UpdateResellerParams other = (UpdateResellerParams) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resellerMsisdn==null && other.getResellerMsisdn()==null) || 
             (this.resellerMsisdn!=null &&
              this.resellerMsisdn.equals(other.getResellerMsisdn()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.person==null && other.getPerson()==null) || 
             (this.person!=null &&
              this.person.equals(other.getPerson())));
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
        if (getResellerMsisdn() != null) {
            _hashCode += getResellerMsisdn().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getPerson() != null) {
            _hashCode += getPerson().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateResellerParams.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "updateResellerParams"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resellerMsisdn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resellerMsisdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "addressData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("person");
        elemField.setXmlName(new javax.xml.namespace.QName("", "person"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://external.interfaces.ers.seamless.com/", "personData"));
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
