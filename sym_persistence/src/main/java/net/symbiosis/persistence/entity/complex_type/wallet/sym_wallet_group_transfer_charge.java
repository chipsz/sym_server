package net.symbiosis.persistence.entity.complex_type.wallet;

import net.symbiosis.persistence.entity.super_class.sym_enum_entity;

import javax.persistence.*;
import java.math.BigDecimal;

/***************************************************************************
 *                                                                         *
 * Created:     28 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "transfer_charge_id")),
    @AttributeOverride(name = "name", column = @Column(name = "transfer_charge_name"))
})
@Cacheable(false)
public class sym_wallet_group_transfer_charge extends sym_enum_entity<sym_wallet_group_transfer_charge> {

    @ManyToOne
    @JoinColumn(name = "wallet_group_id")
    private sym_wallet_group wallet_group;
    @Column(nullable = false)
    private BigDecimal starting_value;
    private BigDecimal ending_value;
    private Double wallet_charge;

    public sym_wallet_group_transfer_charge() {}

    public sym_wallet_group_transfer_charge(sym_wallet_group wallet_group, BigDecimal starting_value,
                                            BigDecimal ending_value, Double wallet_charge) {
        this.wallet_group = wallet_group;
        this.starting_value = starting_value;
        this.ending_value = ending_value;
        this.wallet_charge = wallet_charge;
    }

    public sym_wallet_group getWallet_group() { return wallet_group; }

    public void setWallet_group(sym_wallet_group wallet_group) { this.wallet_group = wallet_group; }

    public BigDecimal getStarting_value() { return starting_value; }

    public void setStarting_value(BigDecimal starting_value) { this.starting_value = starting_value; }

    public BigDecimal getEnding_value() { return ending_value; }

    public void setEnding_value(BigDecimal ending_value) { this.ending_value = ending_value; }

    public Double getWallet_charge() { return wallet_charge; }

    public void setWallet_charge(Double merchant_discount) { this.wallet_charge = merchant_discount; }

}
