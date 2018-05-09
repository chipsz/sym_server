package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymServiceProvider;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class SymServiceProviderList extends DataContract<SymServiceProviderList> implements Serializable {

    protected ArrayList<SymServiceProvider> serviceProviderData;

    public SymServiceProviderList() {
    }

    public SymServiceProviderList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymServiceProviderList(SymResponseCode symResponseCode, ArrayList<SymServiceProvider> serviceProviderData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.serviceProviderData = serviceProviderData;
    }

    public SymServiceProviderList(SymResponseCode symResponseCode, SymServiceProvider serviceProvider) {
        this.symResponse = new SymResponse(symResponseCode);
        this.serviceProviderData = new ArrayList<>();
        this.serviceProviderData.add(serviceProvider);
    }

    public ArrayList<SymServiceProvider> getServiceProviderData() {
        return serviceProviderData;
    }

    public void setServiceProviderData(ArrayList<SymServiceProvider> serviceProviderData) {
        this.serviceProviderData = serviceProviderData;
    }
}
