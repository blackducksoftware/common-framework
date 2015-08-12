package soleng.framework.core.encoding;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.Test;

import soleng.framework.core.config.ConfigurationManager;
import soleng.framework.core.encryption.Password;

/**
 * 
 * @author sbillings
 *
 */
public class Ascii85EncoderTest {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static Random rand = new Random(System.currentTimeMillis());
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws Exception {
		int numTests = 10000; // 10K is a good number
		
		for (int i=0; i < numTests; i++) {
			String psw = generateRandomPassword(64);
			testPassword(psw);
		}
	}
	
	private void testPassword(String origString) throws Exception {
		byte[] encryptedBinary = Password.encryptStringToBinary(origString);
		byte[] encodedBytes = Ascii85Encoder.encode(encryptedBinary);
		assertTrue(isAscii(encodedBytes));
		
		String encodedString = new String(encodedBytes, UTF8);
		byte[] decodedBytes = Ascii85Encoder.decode(encodedString.getBytes());
		String decryptedString = Password.decryptBinaryToString(decodedBytes);
		assertEquals(origString, decryptedString);
	}
	
	public static String generateRandomPassword(int maxLen) {
		List<Character> badChars = Arrays.asList(Password.PROPERTY_VALUE_PROBLEMATIC_CHARS);
		
		int len = 0;
		while (len < Password.MIN_LENGTH) {
			len = rand.nextInt() % maxLen;
		}
		byte[] outputBuffer = new byte[len];

		int numPossibleCharValues = Password.MAX_CHAR_VALUE - Password.MIN_CHAR_VALUE + 1;
		for (int i=0; i < len; i++) {
			
			int charValue = -1;
			do {
				int charValueOffset = -1;
				while (charValueOffset < 0) {
					charValueOffset = rand.nextInt();
				}
				charValue = Password.MIN_CHAR_VALUE + charValueOffset % numPossibleCharValues;	
			} while (badChars.contains(new Character((char) charValue)));
			
			outputBuffer[i] = (byte)charValue;
		}
		String randomString = new String(outputBuffer, UTF8);
		return randomString;
	}

	private boolean isAscii(byte[] bytes) {
		String stringToTest = new String(bytes, UTF8);
		return StringUtils.isAsciiPrintable(stringToTest);
	}

}
