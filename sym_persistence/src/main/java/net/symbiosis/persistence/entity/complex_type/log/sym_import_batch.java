package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.util.Date;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "import_batch_id"))
public class sym_import_batch extends sym_entity<sym_import_batch> {

	private String voucher_provider_batch_id;
	@ManyToOne
	@JoinColumn(name = "voucher_id")
	private sym_voucher voucher;
	private String filename;
	private Date import_date_time;
	private Long number_of_vouchers;

	public sym_import_batch(){}

	public sym_import_batch(String voucher_provider_batch_id, sym_voucher voucher, String filename,
							 Date import_date_time, Long number_of_vouchers) {
		this.voucher_provider_batch_id = voucher_provider_batch_id;
		this.voucher = voucher;
		this.filename = filename;
		this.import_date_time = import_date_time;
		this.number_of_vouchers = number_of_vouchers;
	}

	public String getVoucher_provider_batch_id() { return voucher_provider_batch_id; }

	public void setVoucher_provider_batch_id(String voucher_provider_batch_id) {
		this.voucher_provider_batch_id = voucher_provider_batch_id;
	}

	public sym_voucher getVoucher() { return voucher; }

	public void setVoucher(sym_voucher voucher) { this.voucher = voucher; }

	public String getFilename() { return filename; }

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getNumber_of_vouchers() {
		return number_of_vouchers;
	}

	public sym_import_batch setNumber_of_vouchers(Long number_of_vouchers) {
		this.number_of_vouchers = number_of_vouchers;
		return this;
	}

	public Date getImport_date_time() {
		return import_date_time;
	}

	public void setImport_date_time(Date import_date_time) {
		this.import_date_time = import_date_time;
	}


}
