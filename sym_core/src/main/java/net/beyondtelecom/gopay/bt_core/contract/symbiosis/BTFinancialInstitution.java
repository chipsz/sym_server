package net.beyondtelecom.gopay.bt_core.contract.symbiosis;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     22 / 01 / 2017                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class BTFinancialInstitution implements Serializable {

	private Long institutionId;
	private String institutionName;
	private String institutionShortName;
	private String institutionType;
	private Boolean enabled;
	private String swiftCode;
	private String physicalAddress;
	private String contactPhone;
	private String contactFax;
	private String contactEmail;

	public BTFinancialInstitution() {}

	public BTFinancialInstitution(Long institutionId, String institutionName, String institutionShortName,
                                  String institutionType, Boolean enabled, String swiftCode, String physicalAddress,
                                  String contactPhone, String contactFax, String contactEmail) {
		this.institutionId = institutionId;
		this.institutionName = institutionName;
		this.institutionShortName = institutionShortName;
		this.institutionType = institutionType;
		this.enabled = enabled;
		this.swiftCode = swiftCode;
		this.physicalAddress = physicalAddress;
		this.contactPhone = contactPhone;
		this.contactFax = contactFax;
		this.contactEmail = contactEmail;
	}

	public Long getInstitutionId() { return institutionId; }

	public void setInstitutionId(Long institutionId) { this.institutionId = institutionId; }

	public String getInstitutionName() { return institutionName; }

	public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }

	public String getInstitutionShortName() { return institutionShortName; }

	public void setInstitutionShortName(String institutionShortName) { this.institutionShortName = institutionShortName; }

    public String getInstitutionType() { return institutionType; }

    public void setInstitutionType(String institutionType) { this.institutionType = institutionType; }

    public Boolean getEnabled() { return enabled; }

	public void setEnabled(Boolean enabled) { this.enabled = enabled; }

	public String getSwiftCode() { return swiftCode; }

	public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }

	public String getPhysicalAddress() { return physicalAddress; }

	public void setPhysicalAddress(String physicalAddress) { this.physicalAddress = physicalAddress; }

	public String getContactPhone() { return contactPhone; }

	public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

	public String getContactFax() { return contactFax; }

	public void setContactFax(String contactFax) { this.contactFax = contactFax; }

	public String getContactEmail() { return contactEmail; }

	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
