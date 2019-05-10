package net.symbiosis.core_lib.security;

import org.testng.annotations.Test;

import static java.lang.String.format;
import static net.symbiosis.core_lib.security.Security.decryptAES;
import static net.symbiosis.core_lib.security.Security.encryptAES;
import static org.testng.Assert.assertEquals;

/***************************************************************************
 *                                                                         *
 * Created:     08 / 05 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Test
public class SecurityTest {

    private static final String encryptionKey = "F@8^d1&/e(!~1D-n";
    private static final String initializationVector = "BYb7_72D-b4#c9*c";
    private static final String data = "empowertst0419";

    @Test
    public void testEncryptAES() throws Exception {
        System.out.println(format("Encrypting %s with key '%s' and init vector '%s'", data, encryptionKey, initializationVector));
        String encryptedResult = encryptAES(encryptionKey, initializationVector, data);
        assertEquals(encryptedResult,"2suZ0Dcv5iTLOPbqOyOC0A==");
        System.out.println(format("Result: %s", encryptedResult));
    }

    @Test
    public void testDecryptAES() throws Exception {
        System.out.println(format("Decrypting %s with key '%s' and init vector '%s'", "2suZ0Dcv5iTLOPbqOyOC0A==", encryptionKey, initializationVector));
        String decryptedResult = decryptAES(encryptionKey, initializationVector, "2suZ0Dcv5iTLOPbqOyOC0A==");
        assertEquals(decryptedResult, data);
        System.out.println(format("Result: %s", decryptedResult));
    }

}