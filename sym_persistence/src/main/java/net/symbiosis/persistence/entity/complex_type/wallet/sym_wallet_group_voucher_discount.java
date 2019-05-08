package net.symbiosis.persistence.entity.complex_type.wallet;

import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "wallet_group_voucher_id"))
@Cacheable(false)
public class sym_wallet_group_voucher_discount extends sym_entity<sym_wallet_group_voucher_discount> {

	@ManyToOne
	@JoinColumn(name = "wallet_group_id")
	private sym_wallet_group wallet_group;
	@ManyToOne
	@JoinColumn(name = "voucher_id")
	private sym_voucher voucher;
	Double wallet_discount;

	public sym_wallet_group_voucher_discount() {}

	public sym_wallet_group_voucher_discount(sym_wallet_group wallet_group, sym_voucher voucher, Double wallet_discount) {
		this.wallet_group = wallet_group;
		this.voucher = voucher;
		this.wallet_discount = wallet_discount;
	}

	public sym_wallet_group getWallet_group() { return wallet_group; }

	public void setWallet_group(sym_wallet_group wallet_group) { this.wallet_group = wallet_group; }

	public sym_voucher getVoucher() { return voucher; }

	public void setVoucher(sym_voucher voucher) { this.voucher = voucher; }

	public Double getWallet_discount() { return wallet_discount; }

	public void setWallet_discount(Double merchant_discount) { this.wallet_discount = merchant_discount; }

}
