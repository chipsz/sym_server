package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTFinancialInstitution;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     22 / 01 / 2017                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class BTFinancialInstitutionList extends DataContract<BTFinancialInstitutionList> implements Serializable {

	protected ArrayList<BTFinancialInstitution> financialInstitutionData;

	public BTFinancialInstitutionList() {}

	public BTFinancialInstitutionList(BTResponseCode btResponseCode) { super(btResponseCode); }

	public BTFinancialInstitutionList(BTResponseCode btResponseCode, ArrayList<BTFinancialInstitution> financialInstitutionData) {
		super(btResponseCode);
		this.financialInstitutionData = financialInstitutionData;
	}

	public BTFinancialInstitutionList(BTResponseCode btResponseCode, BTFinancialInstitution financialInstitution) {
		super(btResponseCode);
		this.financialInstitutionData = new ArrayList<>();
		this.financialInstitutionData.add(financialInstitution);
	}

	public ArrayList<BTFinancialInstitution> getFinancialInstitutionData() { return financialInstitutionData; }

	public void setFinancialInstitutionData(ArrayList<BTFinancialInstitution> financialInstitutionData) {
	    this.financialInstitutionData = financialInstitutionData;
	}

}
