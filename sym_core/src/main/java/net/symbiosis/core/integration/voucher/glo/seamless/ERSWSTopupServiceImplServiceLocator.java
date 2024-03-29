/**
 * ERSWSTopupServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.symbiosis.core.integration.voucher.glo.seamless;

public class ERSWSTopupServiceImplServiceLocator extends org.apache.axis.client.Service implements ERSWSTopupServiceImplService {

    public ERSWSTopupServiceImplServiceLocator() {
    }


    public ERSWSTopupServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ERSWSTopupServiceImplServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ERSWSTopupServiceImplPort
    private String ERSWSTopupServiceImplPort_address = "http://197.220.173.135:8913/topupservice/service";

    public String getERSWSTopupServiceImplPortAddress() {
        return ERSWSTopupServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String ERSWSTopupServiceImplPortWSDDServiceName = "ERSWSTopupServiceImplPort";

    public String getERSWSTopupServiceImplPortWSDDServiceName() {
        return ERSWSTopupServiceImplPortWSDDServiceName;
    }

    public void setERSWSTopupServiceImplPortWSDDServiceName(String name) {
        ERSWSTopupServiceImplPortWSDDServiceName = name;
    }

    public ERSTopupService getERSWSTopupServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ERSWSTopupServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getERSWSTopupServiceImplPort(endpoint);
    }

    public ERSTopupService getERSWSTopupServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            net.symbiosis.core.integration.voucher.glo.seamless.ERSWSTopupServiceImplServiceSoapBindingStub _stub = new net.symbiosis.core.integration.voucher.glo.seamless.ERSWSTopupServiceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getERSWSTopupServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setERSWSTopupServiceImplPortEndpointAddress(String address) {
        ERSWSTopupServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ERSTopupService.class.isAssignableFrom(serviceEndpointInterface)) {
                net.symbiosis.core.integration.voucher.glo.seamless.ERSWSTopupServiceImplServiceSoapBindingStub _stub = new net.symbiosis.core.integration.voucher.glo.seamless.ERSWSTopupServiceImplServiceSoapBindingStub(new java.net.URL(ERSWSTopupServiceImplPort_address), this);
                _stub.setPortName(getERSWSTopupServiceImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ERSWSTopupServiceImplPort".equals(inputPortName)) {
            return getERSWSTopupServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://clientservice.topupservice.client.ers.seamless.com/", "ERSWSTopupServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://clientservice.topupservice.client.ers.seamless.com/", "ERSWSTopupServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("ERSWSTopupServiceImplPort".equals(portName)) {
            setERSWSTopupServiceImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
