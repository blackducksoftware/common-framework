package soleng.framework.core.encryption;

import static org.junit.Assert.*;
import soleng.framework.core.encryption.Password;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EncryptionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws Exception {
		String password = "testpassword";
		String encryptedPassword = Password.encryptEncode(password);
		System.out.println("Password '" + password + "' encrypted: " + encryptedPassword);
		assertEquals("=MFOYfHA1mB<<u#:(TBIlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P", encryptedPassword);
		assertEquals(password, Password.decodeDecrypt(encryptedPassword));
	}
	
	@Test
	public void testEmpty() throws Exception {

		try {
			Password.encryptEncode("");
			fail("Expected exception on empty password");
		} catch (IllegalArgumentException e) {
			// expect this
		}
	}

	@Test
	public void testNull() throws Exception {
		String password = null;
		try {
			Password.encryptEncode(password);
			fail("Should have thrown an exception");
		} catch (IllegalArgumentException e) {
			// expect this
		}
	}
	
	@Test
	public void testValidation() {
		assertTrue(Password.isValidPassword("1234567890123456789012345678901234567890123456789012345678901234"));
		assertFalse(Password.isValidPassword("12345678901234567890123456789012345678901234567890123456789012345"));
		assertFalse(Password.isValidPassword(" abc"));
		assertFalse(Password.isValidPassword("abc "));
		assertFalse(Password.isValidPassword("abc\n"));
	}
	
}
