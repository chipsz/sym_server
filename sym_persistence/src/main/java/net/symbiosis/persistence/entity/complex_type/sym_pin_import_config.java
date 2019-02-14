package net.symbiosis.persistence.entity.complex_type;

import net.symbiosis.persistence.entity.complex_type.voucher.sym_service_provider;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.enumeration.sym_voucher_type;
import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.*;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@Table(name = "sym_pin_import_config")
@AttributeOverride(name = "id", column = @Column(name = "pin_import_config_id"))
public class sym_pin_import_config extends sym_enum_entity<sym_pin_import_config> {

	@ManyToOne
	@JoinColumn(name = "service_provider_id")
	private sym_service_provider service_provider;
	@ManyToOne
	@JoinColumn(name = "voucher_provider_id")
	private sym_voucher_provider voucher_provider;
	@ManyToOne
	@JoinColumn(name = "voucher_type_id")
	private sym_voucher_type voucher_type;
	private String filename_regex;
	@Column(nullable = false)
	private Integer pin_start_line;
	@Column(nullable = false)
	private String delimiter;
	@Column(nullable = false)
	private Integer pin_pos;
	@Column(nullable = false)
	private Integer serial_pos;
	@Column(nullable = false)
	private Integer pin_length;
	@Column(nullable = false)
	private Integer serial_length;

	@Column(nullable = false)
	private Boolean amount_in_contents;
	@Column(nullable = false)
	private Boolean amount_in_filename;
	@Column(nullable = false)
	private Integer divide_amount_by;
	private Integer amount_line_num;
	private Integer amount_pos;
	private String amount_regex;

	@Column(nullable = false)
	private Boolean batch_id_in_contents;
	@Column(nullable = false)
	private Boolean batch_id_in_filename;
	private Integer batch_id_line_num;
	private Integer batch_id_pos;
	private String batch_id_regex;

	@Column(nullable = false)
	private Boolean expiry_in_contents;
	@Column(nullable = false)
	private Boolean expiry_in_filename;
	private Integer expiry_line_num;
	private Integer expiry_pos;
	private String expiry_regex;
	private String expiry_date_format;

	@Column(nullable = false)
	private Boolean total_num_in_contents;
	@Column(nullable = false)
	private Boolean total_num_in_filename;
	private Integer total_num_line_num;
	private Integer total_num_pos;
	private String total_num_regex;

    private String pgp_private_key_file;
	private String pgp_key_pass;

	public sym_pin_import_config() {}

	public sym_service_provider getService_provider() { return service_provider; }

	public void setService_provider(sym_service_provider service_provider) { this.service_provider = service_provider; }

	public sym_voucher_provider getVoucher_provider() { return voucher_provider; }

	public void setVoucher_provider(sym_voucher_provider voucher_provider) { this.voucher_provider = voucher_provider; }

	public sym_voucher_type getVoucher_type() { return voucher_type; }

	public void setVoucher_type(sym_voucher_type voucher_type) { this.voucher_type = voucher_type; }

	public String getFilename_regex() { return filename_regex; }

	public void setFilename_regex(String filename_regex) { this.filename_regex = filename_regex; }

	public Integer getPin_start_line() { return pin_start_line; }

	public void setPin_start_line(Integer pin_start_line) { this.pin_start_line = pin_start_line; }

	public String getDelimiter() { return delimiter; }

	public void setDelimiter(String delimiter) { this.delimiter = delimiter; }

	public Integer getPin_pos() { return pin_pos; }

	public void setPin_pos(Integer pin_pos) { this.pin_pos = pin_pos; }

	public Integer getSerial_pos() { return serial_pos; }

	public void setSerial_pos(Integer serial_pos) { this.serial_pos = serial_pos; }

	public Integer getPin_length() { return pin_length; }

	public void setPin_length(Integer pin_length) { this.pin_length = pin_length; }

	public Integer getSerial_length() { return serial_length; }

	public void setSerial_length(Integer serial_length) { this.serial_length = serial_length; }

	public Boolean getAmount_in_contents() { return amount_in_contents; }

	public void setAmount_in_contents(Boolean amount_in_contents) { this.amount_in_contents = amount_in_contents; }

	public Boolean getAmount_in_filename() { return amount_in_filename; }

	public void setAmount_in_filename(Boolean amount_in_filename) { this.amount_in_filename = amount_in_filename; }

	public Integer getDivide_amount_by() { return divide_amount_by; }

	public void setDivide_amount_by(Integer amount_in_cents) { this.divide_amount_by = amount_in_cents; }

	public Integer getAmount_line_num() { return amount_line_num; }

	public void setAmount_line_num(Integer amount_line_num) { this.amount_line_num = amount_line_num; }

	public Integer getAmount_pos() { return amount_pos; }

	public void setAmount_pos(Integer amount_pos) { this.amount_pos = amount_pos; }

	public String getAmount_regex() { return amount_regex; }

	public void setAmount_regex(String amount_regex) { this.amount_regex = amount_regex; }

	public Boolean getBatch_id_in_contents() { return batch_id_in_contents; }

	public void setBatch_id_in_contents(Boolean batch_id_in_contents) { this.batch_id_in_contents = batch_id_in_contents; }

	public Boolean getBatch_id_in_filename() { return batch_id_in_filename; }

	public void setBatch_id_in_filename(Boolean batch_id_in_filename) { this.batch_id_in_filename = batch_id_in_filename; }

	public Integer getBatch_id_line_num() { return batch_id_line_num; }

	public void setBatch_id_line_num(Integer batch_id_line_num) { this.batch_id_line_num = batch_id_line_num; }

	public Integer getBatch_id_pos() { return batch_id_pos; }

	public void setBatch_id_pos(Integer batch_id_pos) { this.batch_id_pos = batch_id_pos; }

	public String getBatch_id_regex() { return batch_id_regex; }

	public void setBatch_id_regex(String batch_id_regex) { this.batch_id_regex = batch_id_regex; }

	public Boolean getExpiry_in_contents() { return expiry_in_contents; }

	public void setExpiry_in_contents(Boolean expiry_in_contents) { this.expiry_in_contents = expiry_in_contents; }

	public Boolean getExpiry_in_filename() { return expiry_in_filename; }

	public void setExpiry_in_filename(Boolean expiry_in_filename) { this.expiry_in_filename = expiry_in_filename; }

	public Integer getExpiry_line_num() { return expiry_line_num; }

	public void setExpiry_line_num(Integer expiry_line_num) { this.expiry_line_num = expiry_line_num; }

	public Integer getExpiry_pos() { return expiry_pos; }

	public void setExpiry_pos(Integer expiry_pos) { this.expiry_pos = expiry_pos; }

	public String getExpiry_regex() { return expiry_regex; }

	public void setExpiry_regex(String expiry_regex) { this.expiry_regex = expiry_regex; }

	public String getExpiry_date_format() { return expiry_date_format; }

	public void setExpiry_date_format(String expiry_date_format) { this.expiry_date_format = expiry_date_format; }

	public Boolean getTotal_num_in_contents() { return total_num_in_contents; }

	public void setTotal_num_in_contents(Boolean total_num_in_contents) {
		this.total_num_in_contents = total_num_in_contents;
	}

	public Boolean getTotal_num_in_filename() { return total_num_in_filename; }

	public void setTotal_num_in_filename(Boolean total_num_in_filename) {
		this.total_num_in_filename = total_num_in_filename;
	}

	public Integer getTotal_num_line_num() { return total_num_line_num; }

	public void setTotal_num_line_num(Integer total_num_line_num) { this.total_num_line_num = total_num_line_num; }

	public Integer getTotal_num_pos() { return total_num_pos; }

	public void setTotal_num_pos(Integer total_num_pos) { this.total_num_pos = total_num_pos; }

	public String getTotal_num_regex() { return total_num_regex; }

	public void setTotal_num_regex(String total_num_regex) { this.total_num_regex = total_num_regex; }

    public String getPgp_private_key_file() { return pgp_private_key_file; }

    public void setPgp_private_key_file(String pgp_private_key_file) { this.pgp_private_key_file = pgp_private_key_file; }

    public String getPgp_key_pass() { return pgp_key_pass; }

    public void setPgp_key_pass(String pgp_public_key_pass) { this.pgp_key_pass = pgp_public_key_pass; }
}
