package soleng.framework.core.encryption;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

public class PasswordEncrypterTool {
	private static final String PASSWORD = "]OqP/M!5V\\P3^XS4JsHZSr-A2!jWeRlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P";

	@Test
	public void decodeDecryptPassword() throws InvalidKeyException, UnrecoverableKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, KeyStoreException, CertificateException, IOException, DecoderException  {
		String plainTextPassword = null;
		plainTextPassword = Password.decodeDecrypt(PASSWORD);
		System.out.println("'" + PASSWORD + "' decoded/decrypted is: '" + plainTextPassword + "'\n" +
				"(The single quotes are not part of the password.)");
	}
}
