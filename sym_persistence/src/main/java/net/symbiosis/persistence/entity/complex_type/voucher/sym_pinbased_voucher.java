package net.symbiosis.persistence.entity.complex_type.voucher;

import net.symbiosis.persistence.entity.complex_type.log.sym_import_batch;
import net.symbiosis.persistence.entity.enumeration.sym_voucher_status;
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
@AttributeOverride(name = "id", column = @Column(name = "pinbased_voucher_id"))
@Cacheable(false)
public class sym_pinbased_voucher extends sym_entity<sym_pinbased_voucher> {

	@ManyToOne
	@JoinColumn(name = "voucher_id")
	private sym_voucher voucher;
	private String voucher_pin;
	@ManyToOne(optional = false)
	@JoinColumn(name = "voucher_status_id")
	private sym_voucher_status voucher_status;
	@ManyToOne
	@JoinColumn(name = "import_batch_id")
	private sym_import_batch import_batch;
	@Column(unique = true)
	private String serial_number;
	private Date voucher_expiry_date;
	private Date distributed_date;
	private String pin_alias;

	public sym_pinbased_voucher() {}

	public sym_pinbased_voucher(sym_voucher voucher, String voucher_pin, sym_voucher_status voucher_status,
			sym_import_batch import_batch, String serial_number, Date voucher_expiry_date,
			Date distributed_date, String pin_alias) {
		this.voucher = voucher;
		this.voucher_pin = voucher_pin;
		this.voucher_status = voucher_status;
		this.import_batch = import_batch;
		this.serial_number = serial_number;
		this.voucher_expiry_date = voucher_expiry_date;
		this.distributed_date = distributed_date;
		this.pin_alias = pin_alias;
	}

	public sym_voucher getVoucher() { return voucher; }

	public void setVoucher(sym_voucher voucher) { this.voucher = voucher; }

	public String getVoucher_pin() {
		return voucher_pin;
	}

	public void setVoucher_pin(String voucher_pin) {
		this.voucher_pin = voucher_pin;
	}

	public sym_voucher_status getVoucher_status() {
		return voucher_status;
	}

	public sym_pinbased_voucher setVoucher_status(sym_voucher_status voucher_status) {
		this.voucher_status = voucher_status;
		return this;
	}

	public sym_import_batch getImport_batch() {
		return import_batch;
	}

	public void setImport_batch(sym_import_batch import_batch) {
		this.import_batch = import_batch;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public Date getVoucher_expiry_date() {
		return voucher_expiry_date;
	}

	public void setVoucher_expiry_date(Date voucher_expiry_date) {
		this.voucher_expiry_date = voucher_expiry_date;
	}

	public Date getDistributed_date() {
		return distributed_date;
	}

	public sym_pinbased_voucher setDistributed_date(Date distributed_date) {
		this.distributed_date = distributed_date;
		return this;
	}

	public String getPin_alias() {
		return pin_alias;
	}

	public void setPin_alias(String pin_alias) {
		this.pin_alias = pin_alias;
	}
}
