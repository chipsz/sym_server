package net.beyondtelecom.gopay.bt_core.helper;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_wallet;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.SUCCESS;
import static net.beyondtelecom.gopay.bt_core.WalletMan.updateWalletBalance;
import static org.testng.Assert.assertTrue;

/***************************************************************************
 * Created:     31 / 05 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Test
public class WalletManTest {
    @Test
    public void testUpdateWalletBalance() throws Exception {

        bt_wallet wallet = new bt_wallet(
            new BigDecimal(100.0), null, null, null
        ).save();

        BTResponseCode updateResponse = updateWalletBalance(wallet, new BigDecimal(10));

        assertTrue(updateResponse.equals(SUCCESS));
        assertTrue(wallet.getCurrent_balance().equals(new BigDecimal(110)));
    }

}