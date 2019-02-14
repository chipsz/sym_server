package net.symbiosis.core.contract;

import net.symbiosis.core.contract.base.DataContract;
import net.symbiosis.core.contract.symbiosis.SymImportBatch;
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
public class SymImportBatchList extends DataContract<SymImportBatchList> implements Serializable {

    protected ArrayList<SymImportBatch> importBatchData;

    public SymImportBatchList() {
    }

    public SymImportBatchList(SymResponseCode symResponseCode) {
        this.symResponse = new SymResponse(symResponseCode);
    }

    public SymImportBatchList(SymResponseCode symResponseCode, ArrayList<SymImportBatch> importBatchData) {
        this.symResponse = new SymResponse(symResponseCode);
        this.importBatchData = importBatchData;
    }

    public SymImportBatchList(SymResponseCode symResponseCode, SymImportBatch importBatch) {
        this.symResponse = new SymResponse(symResponseCode);
        this.importBatchData = new ArrayList<>();
        this.importBatchData.add(importBatch);
    }

    public ArrayList<SymImportBatch> getImportBatchData() {
        return importBatchData;
    }

    public void setImportBatchData(ArrayList<SymImportBatch> importBatchData) {
        this.importBatchData = importBatchData;
    }

}
