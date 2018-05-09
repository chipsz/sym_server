package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymFinancialInstitution;
import net.symbiosis.core_lib.enumeration.SymResponseCode;

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
public class SymFinancialInstitutionList extends DataContract<SymFinancialInstitutionList> implements Serializable {

    protected ArrayList<SymFinancialInstitution> financialInstitutionData;

    public SymFinancialInstitutionList() {
    }

    public SymFinancialInstitutionList(SymResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public SymFinancialInstitutionList(SymResponseCode symResponseCode, ArrayList<SymFinancialInstitution> financialInstitutionData) {
        super(symResponseCode);
        this.financialInstitutionData = financialInstitutionData;
    }

    public SymFinancialInstitutionList(SymResponseCode symResponseCode, SymFinancialInstitution financialInstitution) {
        super(symResponseCode);
        this.financialInstitutionData = new ArrayList<>();
        this.financialInstitutionData.add(financialInstitution);
    }

    public ArrayList<SymFinancialInstitution> getFinancialInstitutionData() {
        return financialInstitutionData;
    }

    public void setFinancialInstitutionData(ArrayList<SymFinancialInstitution> financialInstitutionData) {
        this.financialInstitutionData = financialInstitutionData;
    }

}
