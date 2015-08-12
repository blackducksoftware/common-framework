package soleng.framework.core.config;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

import soleng.framework.core.config.ConfigurationPassword.PasswordMetaProperties;

/**
 * Tests for the ConfigurationPassword class.
 * Ensures that passwords from config files (plain text, old base64-encoded, and new encrypted+Ascii85-encoded)
 * are interpreted correctly based on the hints (*.password.isplaintext and *.password.isencrypted properties)
 * provided.
 * @author sbillings
 *
 */
public class ConfigurationPasswordTest {
	
	@Test
	public void testLegacyProtexPasswordPlainTextIsplaintextNotSet() throws Exception {
		
		// Setup
		Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "protex");
		
		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testLegacyProtexPasswordPlainTextIsplaintextTrue() {
		// Setup
		Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");
		props.setProperty("protex.password.isplaintext", "true");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "protex");
		
		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.PLAIN_TEXT_TRUE, configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertFalse(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testLegacyProtexPasswordBase64IsplaintextNotSet() {
		// Setup
		Properties props = new Properties();
		props.setProperty("protex.password", "YmxhY2tkdWNr");

		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "protex");
		
		// Verify: We get this wrong
//		System.out.println("log should contain a warning about the possibly-base64-encoded password");
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("YmxhY2tkdWNr", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testLegacyProtexPasswordBase64IsplaintextFalse() {
		// Setup
		Properties props = new Properties();
		props.setProperty("protex.password", "YmxhY2tkdWNr");
		props.setProperty("protex.password.isplaintext", "false");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "protex");
		
		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.PLAIN_TEXT_FALSE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	

	
	@Test
	public void testLegacyScmPasswordPlainText() {
		// Setup
		Properties props = new Properties();
		props.setProperty("connector.0.password", "blackduck");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "connector.0");
		
		// Verify
		assertEquals("connector.0.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testLegacyScmPasswordBase64() {
		// Setup
		Properties props = new Properties();
		props.setProperty("connector.0.password", "YmxhY2tkdWNr");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "connector.0");
		
		// Verify
//		System.out.println("log should contain a warning about the possibly-base64-encoded password");
		assertEquals("connector.0.password", configurationPassword.getPropertyName());
		assertEquals("YmxhY2tkdWNr", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testModernProtexPasswordPlainTextIsEncryptedNotSet() {
		// Setup
		Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "protex");
		
		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testModernProtexPasswordPlainTextIsEncryptedFalse() {
		// Setup
		Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");
		props.setProperty("protex.password.isencrypted", "false");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "protex");
		
		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_FALSE, configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertFalse(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testModernProtexPasswordEncryptedIsEncryptedTrue() {
		// Setup
		Properties props = new Properties();
		props.setProperty("protex.password", "_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
		props.setProperty("protex.password.isencrypted", "true");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "protex");
		
		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_TRUE, configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testModernScmPasswordPlainTextIsEncryptedNotSet() {
		// Setup
		Properties props = new Properties();
		props.setProperty("connector.0.password", "blackduck");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "connector.0");
		
		// Verify
		assertEquals("connector.0.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testModernScmPasswordPlainTextIsEncryptedFalse() {
		// Setup
		Properties props = new Properties();
		props.setProperty("connector.0.password", "blackduck");
		props.setProperty("connector.0.password.isencrypted", "false");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "connector.0");
		
		// Verify
		assertEquals("connector.0.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_FALSE, configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertFalse(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testModernScmPasswordEncryptedIsEncryptedTrue() {
		// Setup
		Properties props = new Properties();
		props.setProperty("connector.0.password", "_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
		props.setProperty("connector.0.password.isencrypted", "true");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(props, "connector.0");
		
		// Verify
		assertEquals("connector.0.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_TRUE, configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testPasswordWithNoPrefix() {
		// Setup
		Properties props = new Properties();
		props.setProperty("password", "blackduck");
		
		// Execute code under test
		ConfigurationPassword configurationPassword = ConfigurationPassword.createFromPropertyNoPrefix(props);
		
		// Verify
		assertEquals("password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE, configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}
	
	@Test
	public void testMissingPassword() throws Exception {
		// Setup
		Properties props = new Properties();
		props.setProperty("typo.password", "blackduck");
		
		// Execute code under test
		ConfigurationPassword psw = ConfigurationPassword.createFromProperty(props, "protex");

		assertEquals(null, psw.getPlainText());
		assertEquals(null, psw.getEncrypted());
	}
	
	@Test
	public void testIncompatibleProperties() {
		// Setup
		Properties props = new Properties();
		props.setProperty("connector.0.password", "_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
		props.setProperty("connector.0.password.isencrypted", "true");
		props.setProperty("connector.0.password.isplaintext", "false");
		
		// Execute code under test
		try {
			ConfigurationPassword.createFromProperty(props, "connector.0");
			fail("Expected exception wasn't thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}
	
	@Test
	public void testNoPasswordProperties() throws Exception {
		// Setup
		Properties props = new Properties();

		// Execute code under test
		ConfigurationPassword psw = ConfigurationPassword.createFromProperty(props, "protex");

		assertEquals(null, psw.getPlainText());
		assertEquals(null, psw.getEncrypted());
	}
	
	@Test
	public void testEmptyPassword() throws Exception {
		// Setup
		Properties props = new Properties();
		props.setProperty("connector.0.password", "");
		props.setProperty("connector.0.password.isencrypted", "false");

		// Execute code under test
		ConfigurationPassword psw = ConfigurationPassword.createFromProperty(props, "protex");

		assertEquals(null, psw.getPlainText());
		assertEquals(null, psw.getEncrypted());
	}

}
