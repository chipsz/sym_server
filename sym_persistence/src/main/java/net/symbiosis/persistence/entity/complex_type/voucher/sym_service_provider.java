package net.symbiosis.persistence.entity.complex_type.voucher;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.*;
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
		@AttributeOverride(name = "id", column = @Column(name = "service_provider_id")),
		@AttributeOverride(name = "name", column = @Column(name = "service_provider_name", unique = true))
} )
public class sym_service_provider extends sym_enum_entity<sym_service_provider> {

	@OneToMany
	@JoinColumn(name = "service_provider_id")
	private List<sym_voucher> vouchers;

	public sym_service_provider() {}
	public sym_service_provider(String service_provider_name, Boolean enabled) { super(service_provider_name, enabled); }

	public List<sym_voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<sym_voucher> vouchers) {
		this.vouchers = vouchers;
	}

}
