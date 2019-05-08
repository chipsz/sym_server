package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@AttributeOverride(name = "id", column = @Column(name = "voucher_provider_payment_id"))
public class sym_voucher_provider_payment extends sym_entity<sym_voucher_provider_payment> {

	private BigDecimal payment_amount;
	private Date payment_time;

	public sym_voucher_provider_payment() {}

	public sym_voucher_provider_payment(BigDecimal payment_amount, Date payment_time) {
		this.payment_amount = payment_amount;
		this.payment_time = payment_time;
	}

	public BigDecimal getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(BigDecimal payment_amount) {
		this.payment_amount = payment_amount;
	}

	public Date getPayment_time() {
		return payment_time;
	}

	public void setPayment_time(Date payment_time) {
		this.payment_time = payment_time;
	}

}
