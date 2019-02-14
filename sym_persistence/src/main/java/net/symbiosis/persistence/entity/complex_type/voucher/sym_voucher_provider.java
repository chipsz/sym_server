package net.symbiosis.persistence.entity.complex_type.voucher;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "voucher_provider_id")),
		@AttributeOverride(name = "name", column = @Column(name = "voucher_provider_name", unique = true))
} )
public class sym_voucher_provider extends sym_enum_entity<sym_voucher_provider> {

	@OneToMany
	@JoinColumn(name = "voucher_provider_id")
	private List<sym_voucher> vouchers;
	private BigDecimal current_balance;

	public sym_voucher_provider() {}
	public sym_voucher_provider(String voucher_provider_name, Boolean enabled) {
		super(voucher_provider_name, enabled);
	}

	public List<sym_voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<sym_voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public BigDecimal getCurrent_balance() {
		return current_balance;
	}

	public sym_voucher_provider setCurrent_balance(BigDecimal current_balance) {
		this.current_balance = current_balance;
		return this;
	}

}
