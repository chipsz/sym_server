package net.symbiosis.web_ui.data;

import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;

import java.io.Serializable;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

public class VoucherStockData implements Serializable {

    private sym_voucher voucher;

    private Integer availableVouchers;

    public VoucherStockData(sym_voucher voucher, Integer availableVouchers) {
        this.voucher = voucher;
        this.availableVouchers = availableVouchers;
    }

    public sym_voucher getVoucher() { return voucher; }

    public void setVoucher(sym_voucher voucher) { this.voucher = voucher; }

    public Integer getAvailableVouchers() { return availableVouchers; }

    public void setAvailableVouchers(Integer availableVouchers) { this.availableVouchers = availableVouchers; }
}
