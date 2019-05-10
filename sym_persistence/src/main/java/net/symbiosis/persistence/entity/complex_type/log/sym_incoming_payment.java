package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.sym_deposit_type;
import net.symbiosis.persistence.entity.enumeration.sym_financial_institution;
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
@AttributeOverride(name = "id", column = @Column(name = "incoming_payment_id"))
public class sym_incoming_payment extends sym_entity<sym_incoming_payment> {

	private BigDecimal payment_amount;
	@ManyToOne(optional = false)
	@JoinColumn(name = "deposit_type_id")
	private sym_deposit_type deposit_type;
	@ManyToOne(optional = false)
	@JoinColumn(name = "wallet_id")
	private sym_wallet wallet;
	private String depositor_reference;
	private Date payment_time;
    @Basic(optional = false)
    private Date time_loaded;
	@ManyToOne
	@JoinColumn(name = "institution_id")
	private sym_financial_institution financial_institution;
	private String bank_reference;
	private String bank_statement_id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "response_code_id")
    private sym_response_code response_code;

	public sym_incoming_payment() {}

	public sym_incoming_payment(BigDecimal payment_amount, sym_deposit_type deposit_type, sym_wallet wallet,
                                String depositor_reference, Date payment_time, Date time_loaded,
                                sym_financial_institution financial_institution, String bank_reference,
                                String bank_statement_id, sym_response_code response_code) {
		this.payment_amount = payment_amount;
		this.deposit_type = deposit_type;
		this.wallet = wallet;
		this.depositor_reference = depositor_reference;
		this.payment_time = payment_time;
		this.time_loaded = time_loaded;
		this.financial_institution = financial_institution;
		this.bank_reference = bank_reference;
		this.bank_statement_id = bank_statement_id;
        this.response_code = response_code;
    }

	public BigDecimal getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(BigDecimal payment_amount) {
		this.payment_amount = payment_amount;
	}

	public sym_deposit_type getDeposit_type() {
		return deposit_type;
	}

	public sym_incoming_payment setDeposit_type(sym_deposit_type deposit_type) {
		this.deposit_type = deposit_type;
		return this;
	}

	public sym_wallet getWallet() {
		return wallet;
	}

	public sym_incoming_payment setWallet(sym_wallet wallet) { this.wallet = wallet; return this; }

	public String getDepositor_reference() {
		return depositor_reference;
	}

	public void setDepositor_reference(String depositor_reference) {
		this.depositor_reference = depositor_reference;
	}

	public Date getPayment_time() {
		return payment_time;
	}

	public void setPayment_time(Date payment_time) {
		this.payment_time = payment_time;
	}

	public Date getTime_loaded() {
		return time_loaded;
	}

	public sym_incoming_payment setTime_loaded(Date time_loaded) {
		this.time_loaded = time_loaded;
		return this;
	}

	public sym_financial_institution getFinancial_institution() { return financial_institution; }

	public void setFinancial_institution(sym_financial_institution bank) { this.financial_institution = bank; }

	public String getBank_reference() {
		return bank_reference;
	}

	public void setBank_reference(String bank_reference) {
		this.bank_reference = bank_reference;
	}

	public String getBank_statement_id() {
		return bank_statement_id;
	}

	public void setBank_statement_id(String bank_statement_id) {
		this.bank_statement_id = bank_statement_id;
	}

    public sym_response_code getResponse_code() {
        return response_code;
    }

    public sym_incoming_payment setResponse_code(sym_response_code response_code) {
        this.response_code = response_code;
        return this;
    }
}
