package net.symbiosis.core.contract.symbiosis;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/
@XmlRootElement
public class SymServiceProvider implements Serializable {
    private Long serviceProviderId;
    private String serviceProviderName;

    public SymServiceProvider() {
    }

    public SymServiceProvider(Long serviceProviderId, String serviceProviderName) {
        this.serviceProviderId = serviceProviderId;
        this.serviceProviderName = serviceProviderName;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }
}
