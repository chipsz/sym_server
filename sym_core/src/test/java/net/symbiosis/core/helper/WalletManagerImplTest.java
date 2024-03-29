package net.symbiosis.core.helper;

import net.symbiosis.core.service.WalletManager;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_wallet_transaction;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.helper.SymEnumHelper;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

import static net.symbiosis.core_lib.enumeration.SymEventType.WALLET_LOAD;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static org.testng.Assert.assertTrue;

/***************************************************************************
 * Created:     31 / 05 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Test
public class WalletManagerImplTest {

    private WalletManager walletManager;

    @BeforeClass
    public void setUp() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test-sym_core-spring-context.xml");
        walletManager = (WalletManager) context.getBean("walletManager");
    }

    @Test
    public void testUpdateWalletBalance() {

        sym_wallet wallet = new sym_wallet(
            new BigDecimal(100.0), null, null, null
        ).save();

        SymResponseObject<sym_wallet> updateResponse = walletManager.updateWalletBalance(
            new sym_wallet_transaction(
                wallet, SymEnumHelper.fromEnum(WALLET_LOAD), new BigDecimal(10),
                "Test wallet load", 0L, new Date()
            )
        );

        assertTrue(updateResponse.getResponseCode().equals(SUCCESS));
        assertTrue(wallet.getCurrent_balance().equals(new BigDecimal(110)));
    }

}