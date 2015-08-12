package soleng.framework.core.encryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;

import soleng.framework.core.config.ConfigConstants;
import soleng.framework.core.encoding.Ascii85Encoder;

/**
 * Password encryption / encoding methods.
 * Supports encrypting/encoding and decoding/decrypting of passwords that consist
 * only of ASCII characters Password.PASSWORD_MIN_CHAR_VALUE through Password.PASSWORD_MAX_CHAR_VALUE
 * and have a minimum length of Password.PASSWORD_MIN_LENGTH and a maximum length of Password.PASSWORD_MAX_LENGTH.
 * Original text passwords are encrypted using DES/ECB/NoPadding and then encoded in Ascii85
 * (aka Base85) encoding (for, say, storage in config files).
 * 
 * Encrypting and decrypting uses a KeyStore that resides on the classpath.
 * As of April 9, 2015, we're using the same KeyStore, and same KeyStore password as the integration team,
 * so KeyStore files (*.jceks) are interchangable between Black Duck integrations and Solution Engineering 
 * utilities.
 * 
 * The methods you'll most likely want to use are encryptEncode() and decodeDecrypt(). Optionally,
 * you can call isValidPassword() to test a password's validity, but encryptEncode() also calls it (and
 * throws an IllegalArgumentException if the password is invalid). You only need 
 * encryptStringToBinary() and decryptBinaryToString() if you want encryption without encoding.
 * 
 * @author sbillings
 *
 */
public class Password {
	private static Class thisClass = Password.class;
	
	// Public constants
	public static final char MIN_CHAR_VALUE = '!';
	public static final char MAX_CHAR_VALUE = '~';
	public static final int MIN_LENGTH = 1;
	public static final int MAX_LENGTH = 64;
	
	public static final Character[] PROPERTY_VALUE_PROBLEMATIC_CHARS = { '\\', '"', '#', '=', '|', '$', '(', '[', '{' };
	
	// Message describing password rules
	private static final String PASSWORD_RULES_MSG = 
			"Passwords must consist only of printable ASCII characters, excluding whitespace, \\, \", " +
			"#, =, |, $, (, [, and }. Passwords must have a minimum length of 1 and a maximum length of 64.";
	
	// Encryption algorithm details
	private static final String ENCRYPTION_ALGORITHM = "DES";
	private static final String ENCRYPTION_MODE = "ECB";
	private static final String ENCRYPTION_PADDING = "NoPadding";
	private static final String CIPHER_TRANSFORMATION = 
			ENCRYPTION_ALGORITHM + "/" + ENCRYPTION_MODE + "/" + ENCRYPTION_PADDING;
	
	// KeyStore details
	private static final String EMBEDDED_SUN_KEY_FILE = "/Sun-Key.jceks";
    private static final String EMBEDDED_IBM_KEY_FILE = "/IBM-Key.jceks";
	private static final String KEYSTORE_TYPE = "JCEKS";
	private static final String KEY_PASSWORD_STRING = "blackduck123Integration"; // 8 char min. Same as integrations team
	private static final String KEY_ALIAS = "keyStore";
	
	// Internal/convenience constants
	private static final char[] KEY_PASSWORD = KEY_PASSWORD_STRING.toCharArray();
	private static final Charset UTF8 = Charset.forName("UTF-8");
    
    
    private Password() {} // Hide the constructor; Static methods only
	
    /**
     * Encrypt and encode a password and return the result.
     * First checks that the argument confirms to the password rules: 
     * 
     * @param password
     * @return
     * @throws IOException if it can't open the key file.
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyStoreException 
     * @throws UnrecoverableKeyException 
     * @throws NoSuchPaddingException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws InvalidKeyException 
     * @throws IllegalArgumentExceptionException if password violates the password rules
     */
	public static String encryptEncode(String password) throws UnrecoverableKeyException, KeyStoreException, 
				NoSuchAlgorithmException, CertificateException, IOException, InvalidKeyException, 
				IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		
		if (!isValidPassword(password)) {
			throw new IllegalArgumentException(PASSWORD_RULES_MSG);
		}
		String encryptedPassword;
		byte[] encryptedPasswordBinary = encryptStringToBinary(password);
        byte[] encryptedPasswordAscii = Ascii85Encoder.encode(encryptedPasswordBinary); // encode binary to ascii
        encryptedPassword = new String(encryptedPasswordAscii, UTF8).trim(); // convert to String
        return encryptedPassword;
	}
	
	/**
	 * Test to see whether this password adheres to the password rules.
	 * 		Ascii chars '!' through '~' only (no spaces). 
	 * 		Min len: 1 char
	 * 		Max len: 64 chars
	 * 
	 * @param password
	 * @return
	 */
	public static boolean isValidPassword(String password) {
		if (password == null)
			return false;
		if (password.length() < MIN_LENGTH)
			return false;
		if (password.length() > MAX_LENGTH)
			return false;
		
		List<Character> badChars = Arrays.asList(Password.PROPERTY_VALUE_PROBLEMATIC_CHARS);
		byte[] passwordBytes = password.getBytes();
		for (int i=0; i < password.length(); i++) {
			byte passwordByte = passwordBytes[i];
			if (passwordByte < MIN_CHAR_VALUE)
				return false;
			if (passwordByte > MAX_CHAR_VALUE)
				return false;
			
			if (badChars.contains(new Character((char) passwordByte))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Encrypt the given password, but do not encode it.
	 * 
	 * @param password
	 * @return the encrypted password (binary bytes)
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 */
	public static byte[] encryptStringToBinary(String password)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, UnrecoverableKeyException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchPaddingException {
		
		Key key = getKey(KEY_PASSWORD);
        byte[] encryptedPasswordBinary = encrypt(key, password); // encrypt
		return encryptedPasswordBinary;
	}
	
	/**
	 * Decode and decrypt a password and return the result.
	 * 
	 * @param encryptedPassword
	 * @return the original (decoded/decrypted) password
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws KeyStoreException 
	 * @throws UnrecoverableKeyException 
	 * @throws IllegalArgumentException if the argument is empty or null.
	 */
	public static String decodeDecrypt(String encryptedPassword) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
					NoSuchAlgorithmException, NoSuchPaddingException, UnrecoverableKeyException, KeyStoreException, CertificateException, IOException,
					DecoderException {
		if (encryptedPassword == null || (encryptedPassword.length() == 0)) {
			throw new IllegalArgumentException("The password to decrypt is empty or null");
        }
		
		byte[] encryptedPasswordAsciiBytes = encryptedPassword.getBytes(UTF8); // convert from String to byte array
		
		byte[] encryptedPasswordBinary;
		try {
			encryptedPasswordBinary = Ascii85Encoder.decode(encryptedPasswordAsciiBytes); // decode ascii back to binary
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid encrypted password: " + e.getMessage());
		}
		
        String reconstitutedString = decryptBinaryToString(encryptedPasswordBinary);
        return reconstitutedString;
	}

	/**
	 * Decrypt the given binary/encrypted password, and return the original text password.
	 * The argument should have been encrypted using a valid (according to isValidPassword() password
	 * encrypted by the encryptStringToBinary() method.
	 * 
	 * @param encryptedPasswordBinary
	 * @return the original text password
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decryptBinaryToString(byte[] encryptedPasswordBinary)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, UnrecoverableKeyException,
			InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		
		// get key
        Key key = getKey(KEY_PASSWORD);
        if (key == null) {
            throw new InvalidKeyException("The password decryption key is null");
        }
        
        // decrypt
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(encryptedPasswordBinary); 
        decryptedBytes = Arrays.copyOf(decryptedBytes, MAX_LENGTH);

        // convert byte array to String
        String reconstitutedString = new String(decryptedBytes, UTF8).trim();
		return reconstitutedString;
	}
	
	/**
     * Encrypts the given password using the given key. Returns the encrypted version.
     * 
     * @param keyToUse
     *            Key to use for the encryption
     * @param password
     *            String to be encrypted
     * 
     * @return String encrypted password.
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
     */
    private static byte[] encrypt(Key keyToUse, String password) throws IllegalBlockSizeException, BadPaddingException, 
    					InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        
        
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        byte[] bytes = password.getBytes(UTF8);
        bytes = Arrays.copyOf(bytes, MAX_LENGTH); // There's no way this should exceed 64 at this point, but just in case
        
        cipher.init(Cipher.ENCRYPT_MODE, keyToUse);
        byte[] buffer = cipher.doFinal(bytes);

        return buffer;
    }

    /**
     * Retrieves the cipher Key using the given KeyStore password.
     * Attempts to read the Sun KeyStore, if that doesn't work then it tries to read the IBM KeyStore.
     * When running on the standard JVM, the read of the Sun KeyStore should work. On an IBM JVM,
     * the read of the IBM KeyStore should work.
     * 
     * @param instream
     *            InputStream to get the Key from. If null it will use the default cipher keys provided.
     * @param keypass
     *            char[] with the key password that will gain access to the key
     *            (currently hard coded in)
     * @return Key
     * @throws KeyStoreException
     * @throws IOException 
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws CertificateException
     */
    private static Key getKey(char[] keypass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, 
    							IOException, UnrecoverableKeyException {
        Key key = null;
        try {
            key = getKeyFromStore(EMBEDDED_SUN_KEY_FILE, keypass);
        } catch (Exception e) {
        	key = getKeyFromStore(EMBEDDED_IBM_KEY_FILE, keypass);
        }
        return key;
    }
    
    /**
     * Get the key from the KeyStore.
     * @param keyStoreFilename
     * @param keypass
     * @throws IOException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    private static Key getKeyFromStore(String keyStoreFilename, char[] keypass) throws IOException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
    	InputStream keyStoreInputStream = thisClass.getResourceAsStream(keyStoreFilename); // Try the Sun KeyStore first
        if (keyStoreInputStream == null) {
        	throw new IOException("Unable to locate key store file " + keyStoreFilename); // File should always be found
        }
        Key key = null;
        try {
            KeyStore keystore = KeyStore.getInstance(KEYSTORE_TYPE);
            keystore.load(keyStoreInputStream, keypass);
            key = keystore.getKey(KEY_ALIAS, keypass);
        } finally {
            if (keyStoreInputStream != null) {
                keyStoreInputStream.close();
            }
        }
        return key;
    }

    /**
     * Generates a new key. Should be used manually and only when creating a new key is necessary.
     * WARNING: If the keys in the KeyStore files are replaced then we will not be able to decrypt
     * passwords that were encrypted with the old keys.
     * 
     * @param keypass
     *            char[] with the keypass that will gain access to the key
     *            (currently hard coded in)
     * @throws IOException
     */
    @SuppressWarnings("unused")
    private static Key setKey(char[] keypass, File keyFile) throws Exception {

        Key key = null;
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(keyFile.getCanonicalPath());
            key = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM).generateKey();
            KeyStore keystore = KeyStore.getInstance(KEYSTORE_TYPE);
            keystore.load(null, null);
            keystore.setKeyEntry(KEY_ALIAS, key, keypass, null);
            keystore.store(output, keypass);
        } finally {
            if (output != null) {
                output.close();
            }
        }
        return key;
    }
}
