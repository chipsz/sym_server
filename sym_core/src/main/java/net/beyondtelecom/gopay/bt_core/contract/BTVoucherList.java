package net.beyondtelecom.gopay.bt_core.contract;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.base.DataContract;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTVoucher;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class BTVoucherList extends DataContract<BTVoucherList> implements Serializable {

	protected ArrayList<BTVoucher> voucherData;

	public BTVoucherList() {}

	public BTVoucherList(BTResponseCode btResponseCode) {
		this.btResponse = new BTResponse(btResponseCode);
	}

	public BTVoucherList(BTResponseCode btResponseCode, ArrayList<BTVoucher> voucherData) {
		this.btResponse = new BTResponse(btResponseCode);
		this.voucherData = voucherData;
	}

	public BTVoucherList(BTResponseCode btResponseCode, BTVoucher voucher) {
		this.btResponse = new BTResponse(btResponseCode);
		this.voucherData = new ArrayList<>();
		this.voucherData.add(voucher);
	}

	public ArrayList<BTVoucher> getVoucherData() {
		return voucherData;
	}

	public void setVoucherData(ArrayList<BTVoucher> voucherData) {
		this.voucherData = voucherData;
	}

}
