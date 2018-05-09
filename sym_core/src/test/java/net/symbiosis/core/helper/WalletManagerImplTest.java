package net.symbiosis.core.helper;

import net.symbiosis.core.implementation.WalletManagerImpl;
import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.persistence.entity.complex_type.sym_wallet;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static org.testng.Assert.assertTrue;

/***************************************************************************
 * Created:     31 / 05 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Test
public class WalletManagerImplTest {
    @Test
    public void testUpdateWalletBalance() {

        sym_wallet wallet = new sym_wallet(
                new BigDecimal(100.0), null, null, null
        ).save();

        SymResponseCode updateResponse = WalletManagerImpl.updateWalletBalance(wallet, new BigDecimal(10));

        assertTrue(updateResponse.equals(SUCCESS));
        assertTrue(wallet.getCurrent_balance().equals(new BigDecimal(110)));
    }

}