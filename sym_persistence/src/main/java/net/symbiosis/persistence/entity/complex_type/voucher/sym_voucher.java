package net.symbiosis.persistence.entity.complex_type.voucher;

import net.symbiosis.persistence.entity.enumeration.sym_voucher_type;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/
@Entity
@AttributeOverride(name = "id", column = @Column(name = "voucher_id"))
public class sym_voucher extends sym_entity<sym_voucher> {

	private BigDecimal voucher_value;
	private boolean is_active;
	@ManyToOne(optional = false)
	@JoinColumn(name = "service_provider_id")
	private sym_service_provider service_provider;
	@ManyToOne(optional = false)
	@JoinColumn(name = "voucher_type_id")
	private  sym_voucher_type voucher_type;
	@ManyToOne(optional = false)
	@JoinColumn(name = "voucher_provider_id")
	private sym_voucher_provider voucher_provider;
	private double voucher_provider_discount;
	private String units;
	private boolean is_fixed;
	private boolean is_pin_based;

	public sym_voucher() {}

	public sym_voucher(BigDecimal voucher_value, boolean is_active,
            sym_service_provider service_provider, sym_voucher_type voucher_type,
            double voucher_provider_discount, String units,
            boolean is_fixed, boolean is_pin_based) {
		this.voucher_value = voucher_value;
		this.is_active = is_active;
		this.service_provider = service_provider;
		this.voucher_type = voucher_type;
		this.voucher_provider_discount = voucher_provider_discount;
		this.units = units;
		this.is_fixed = is_fixed;
		this.is_pin_based = is_pin_based;
	}

	public BigDecimal getVoucher_value() {
		return voucher_value;
	}

	public void setVoucher_value(BigDecimal voucher_value) {
		this.voucher_value = voucher_value;
	}

	public boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public sym_service_provider getService_provider() {
		return service_provider;
	}

	public void setService_provider(sym_service_provider service_provider) {
		this.service_provider = service_provider;
	}

	public sym_voucher_type getVoucher_type() {
		return voucher_type;
	}

	public void setVoucher_type(sym_voucher_type voucher_type) {
		this.voucher_type = voucher_type;
	}

	public sym_voucher_provider getVoucher_provider() {
		return voucher_provider;
	}

	public void setVoucher_provider(sym_voucher_provider voucher_provider) {
		this.voucher_provider = voucher_provider;
	}

	public double getVoucher_provider_discount() {
		return voucher_provider_discount;
	}

	public void setVoucher_provider_discount(double voucher_provider_discount) {
		this.voucher_provider_discount = voucher_provider_discount;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public boolean getIs_fixed() {
		return is_fixed;
	}

	public void setIs_fixed(boolean is_fixed) {
		this.is_fixed = is_fixed;
	}

	public boolean getIs_pin_based() {
		return is_pin_based;
	}

	public void setIs_pin_based(boolean is_pin_based) {
		this.is_pin_based = is_pin_based;
	}
}
