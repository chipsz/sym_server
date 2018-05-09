package net.beyondtelecom.gopay.bt_persistence.entity.complex_type;

import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

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
@Table(name = "bt_wallet")
@AttributeOverride(name = "id", column = @Column(name = "wallet_id"))
public class bt_wallet extends bt_entity<bt_wallet> {

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "wallet_admin_user_id")
	private bt_user wallet_admin_user;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "wallet_group_id")
	private bt_wallet_group wallet_group;
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private bt_company company;
	private BigDecimal current_balance;

	public bt_wallet() {}

	public bt_wallet(BigDecimal current_balance, bt_user wallet_admin_user,
                            bt_wallet_group wallet_group, bt_company company) {
		this.current_balance = current_balance;
		this.wallet_admin_user = wallet_admin_user;
		this.wallet_group = wallet_group;
		this.company = company;
	}

	public BigDecimal getCurrent_balance() {
		return current_balance;
	}

	public bt_wallet setCurrent_balance(BigDecimal current_balance) {
		this.current_balance = current_balance;
		return this;
	}

	public bt_user getWallet_admin_user() { return wallet_admin_user; }

	public bt_wallet setWallet_admin_user(bt_user wallet_admin_user) {
		this.wallet_admin_user = wallet_admin_user;
		return this;
	}

	public bt_wallet_group getWallet_group() {
		return wallet_group;
	}

	public void setWallet_group(bt_wallet_group wallet_group) {
		this.wallet_group = wallet_group;
	}

    public bt_company getCompany() { return company; }

    public bt_wallet setCompany(bt_company company) { this.company = company; return this; }
}
