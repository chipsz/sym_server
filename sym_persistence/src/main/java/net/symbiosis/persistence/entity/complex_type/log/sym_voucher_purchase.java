package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_pinbased_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.sym_distribution_channel;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/
@Entity
@AttributeOverride(name = "id", column = @Column(name = "voucher_purchase_id"))
@Cacheable(false)
public class sym_voucher_purchase extends sym_entity<sym_voucher_purchase> {

	@ManyToOne(optional = false)
	@JoinColumn(name = "voucher_id")
	private sym_voucher voucher;
	@ManyToOne
	@JoinColumn(name = "pinbased_voucher_id")
	private sym_pinbased_voucher pinbased_voucher;
	@Column
	private String voucher_provider_reference;
	@ManyToOne(optional = false)
	@JoinColumn(name = "wallet_id")
	private sym_wallet wallet;
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "sym_user_id")
//	private sym_user system_user;
	@ManyToOne(optional = false)
	@JoinColumn(name = "auth_user_id")
	private sym_auth_user auth_user;
	@ManyToOne(optional = false)
	@JoinColumn(name = "response_code_id")
	private sym_response_code response_code;
	private BigDecimal voucher_value;
	private BigDecimal voucher_provider_value;
	private BigDecimal wallet_cost;
	private Date transaction_time;
	private Boolean is_transaction_reconciled;
	private String recipient;
	private String cashier_name;
	@ManyToOne(optional = false)
	@JoinColumn(name = "distribution_channel_id")
	private sym_distribution_channel distribution_channel;

	public sym_voucher_purchase() {}

	public sym_voucher_purchase(sym_voucher voucher, sym_pinbased_voucher pinbased_voucher, String voucher_provider_reference, sym_wallet wallet,
	                            sym_auth_user auth_user, sym_response_code response_code, BigDecimal voucher_value,
                                BigDecimal voucher_provider_value, BigDecimal wallet_cost, Date transaction_time,
                                Boolean is_transaction_reconciled, String recipient, String cashier_name,
                                sym_distribution_channel distribution_channel) {
		this.voucher = voucher;
		this.pinbased_voucher = pinbased_voucher;
        this.voucher_provider_reference = voucher_provider_reference;
        this.wallet = wallet;
		this.auth_user = auth_user;
		this.response_code = response_code;
		this.voucher_value = voucher_value;
		this.voucher_provider_value = voucher_provider_value;
		this.wallet_cost = wallet_cost;
		this.transaction_time = transaction_time;
		this.is_transaction_reconciled = is_transaction_reconciled;
		this.recipient = recipient;
		this.cashier_name = cashier_name;
		this.distribution_channel = distribution_channel;
	}

	public sym_voucher getVoucher() { return voucher; }

	public void setVoucher(sym_voucher voucher) { this.voucher = voucher; }

	public sym_pinbased_voucher getPinbased_voucher() { return pinbased_voucher; }

	public sym_voucher_purchase setPinbased_voucher(sym_pinbased_voucher pinbased_voucher) {
		this.pinbased_voucher = pinbased_voucher;
		return this;
	}

    public String getVoucher_provider_reference() { return voucher_provider_reference; }

    public sym_voucher_purchase setVoucher_provider_reference(String voucher_provider_reference) {
        this.voucher_provider_reference = voucher_provider_reference;
        return this;
    }

    public sym_wallet getWallet() { return wallet; }

	public void setWallet(sym_wallet merchant) { this.wallet = merchant; }

	public sym_auth_user getAuth_user() { return auth_user; }

	public void setAuth_user(sym_auth_user system_auth_user) { this.auth_user = system_auth_user; }

	public sym_response_code getResponse_code() { return response_code; }

	public sym_voucher_purchase setResponse_code(sym_response_code response_code) {
		this.response_code = response_code;
		return this;
	}

	public BigDecimal getVoucher_value() { return voucher_value; }

	public void setVoucher_value(BigDecimal voucher_value) { this.voucher_value = voucher_value; }

	public BigDecimal getVoucher_provider_value() { return voucher_provider_value; }

	public sym_voucher_purchase setVoucher_provider_value(BigDecimal voucher_provider_value) {
		this.voucher_provider_value = voucher_provider_value;
		return this;
	}

	public BigDecimal getWallet_cost() { return wallet_cost; }

	public sym_voucher_purchase setWallet_cost(BigDecimal merchant_cost) {
		this.wallet_cost = merchant_cost;
		return this;
	}

	public Date getTransaction_time() { return transaction_time; }

	public void setTransaction_time(Date transaction_time) { this.transaction_time = transaction_time; }

	public Boolean getIs_transaction_reconciled() { return is_transaction_reconciled; }

	public void setIs_transaction_reconciled(Boolean is_transaction_reconciled) {
		this.is_transaction_reconciled = is_transaction_reconciled;
	}

	public String getRecipient() { return recipient; }

	public void setRecipient(String recipient) { this.recipient = recipient; }

	public String getCashier_name() { return cashier_name; }

	public void setCashier_name(String cashier_name) { this.cashier_name = cashier_name; }

	public sym_distribution_channel getDistribution_channel() { return distribution_channel; }

	public void setDistribution_channel(sym_distribution_channel distribution_channel) {
		this.distribution_channel = distribution_channel;
	}
}
