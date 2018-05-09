package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTImportBatch;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTImportBatchList extends DataContract<BTImportBatchList> implements Serializable {

	protected ArrayList<BTImportBatch> importBatchData;

	public BTImportBatchList() {}

	public BTImportBatchList(BTResponseCode btResponseCode) {
		this.btResponse = new BTResponse(btResponseCode);
	}

	public BTImportBatchList(BTResponseCode btResponseCode, ArrayList<BTImportBatch> importBatchData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.importBatchData = importBatchData;
	}

	public BTImportBatchList(BTResponseCode btResponseCode, BTImportBatch importBatch) {
		this.btResponse = new BTResponse(btResponseCode);
		this.importBatchData = new ArrayList<>();
		this.importBatchData.add(importBatch);
	}

	public ArrayList<BTImportBatch> getImportBatchData() {
		return importBatchData;
	}

	public void setImportBatchData(ArrayList<BTImportBatch> importBatchData) {
		this.importBatchData = importBatchData;
	}

}
