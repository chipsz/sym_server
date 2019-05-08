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
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "voucher_provider_id")),
		@AttributeOverride(name = "name", column = @Column(name = "voucher_provider_name", unique = true))
})
@Cacheable(false)
public class sym_voucher_provider extends sym_enum_entity<sym_voucher_provider> {

	@OneToMany
	@JoinColumn(name = "voucher_provider_id")
	private List<sym_voucher> vouchers;
	@Column
	private BigDecimal current_balance;
    @Column
	private String integration_id;

	public sym_voucher_provider() {}
	public sym_voucher_provider(String voucher_provider_name, Boolean enabled, String integration_id) {
		super(voucher_provider_name, enabled);
        this.integration_id = integration_id;
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

    public String getIntegration_id() { return integration_id; }

    public void setIntegration_id(String integration_id) { this.integration_id = integration_id; }
}
