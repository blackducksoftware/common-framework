/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.tools.commonframework.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationPassword.PasswordMetaProperties;

/**
 * Tests for the ConfigurationPassword class. Ensures that passwords from config
 * files (plain text, old base64-encoded, and new encrypted+Ascii85-encoded) are
 * interpreted correctly based on the hints (*.password.isplaintext and
 * *.password.isencrypted properties) provided.
 *
 * @author sbillings
 *
 */
public class ConfigurationPasswordTest {

	@Test
	public void testLegacyProtexPasswordPlainTextIsplaintextNotSet()
			throws Exception {

		// Setup
		final Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "protex");

		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testLegacyProtexPasswordPlainTextIsplaintextTrue() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");
		props.setProperty("protex.password.isplaintext", "true");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "protex");

		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.PLAIN_TEXT_TRUE,
				configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertFalse(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testLegacyProtexPasswordBase64IsplaintextNotSet() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("protex.password", "YmxhY2tkdWNr");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "protex");

		// Verify: We get this wrong
		// System.out.println("log should contain a warning about the possibly-base64-encoded password");
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("YmxhY2tkdWNr", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testLegacyProtexPasswordBase64IsplaintextFalse() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("protex.password", "YmxhY2tkdWNr");
		props.setProperty("protex.password.isplaintext", "false");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "protex");

		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.PLAIN_TEXT_FALSE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testLegacyScmPasswordPlainText() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("connector.0.password", "blackduck");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "connector.0");

		// Verify
		assertEquals("connector.0.password",
				configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testLegacyScmPasswordBase64() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("connector.0.password", "YmxhY2tkdWNr");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "connector.0");

		// Verify
		// System.out.println("log should contain a warning about the possibly-base64-encoded password");
		assertEquals("connector.0.password",
				configurationPassword.getPropertyName());
		assertEquals("YmxhY2tkdWNr", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testModernProtexPasswordPlainTextIsEncryptedNotSet() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "protex");

		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testModernProtexPasswordPlainTextIsEncryptedFalse() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("protex.password", "blackduck");
		props.setProperty("protex.password.isencrypted", "false");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "protex");

		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_FALSE,
				configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertFalse(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testModernProtexPasswordEncryptedIsEncryptedTrue() {
		// Setup
		final Properties props = new Properties();
		props.setProperty(
				"protex.password",
				"_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
		props.setProperty("protex.password.isencrypted", "true");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "protex");

		// Verify
		assertEquals("protex.password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_TRUE,
				configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testModernScmPasswordPlainTextIsEncryptedNotSet() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("connector.0.password", "blackduck");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "connector.0");

		// Verify
		assertEquals("connector.0.password",
				configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testModernScmPasswordPlainTextIsEncryptedFalse() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("connector.0.password", "blackduck");
		props.setProperty("connector.0.password.isencrypted", "false");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "connector.0");

		// Verify
		assertEquals("connector.0.password",
				configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_FALSE,
				configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertFalse(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testModernScmPasswordEncryptedIsEncryptedTrue() {
		// Setup
		final Properties props = new Properties();
		props.setProperty(
				"connector.0.password",
				"_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
		props.setProperty("connector.0.password.isencrypted", "true");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromProperty(props, "connector.0");

		// Verify
		assertEquals("connector.0.password",
				configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.ENCRYPTED_TRUE,
				configurationPassword.getPasswordMetaProperties());
		assertFalse(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertFalse(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testPasswordWithNoPrefix() {
		// Setup
		final Properties props = new Properties();
		props.setProperty("password", "blackduck");

		// Execute code under test
		final ConfigurationPassword configurationPassword = ConfigurationPassword
				.createFromPropertyNoPrefix(props);

		// Verify
		assertEquals("password", configurationPassword.getPropertyName());
		assertEquals("blackduck", configurationPassword.getPlainText());
		assertEquals(PasswordMetaProperties.NONE,
				configurationPassword.getPasswordMetaProperties());
		assertTrue(configurationPassword.isInNeedOfEncryption());
		assertTrue(configurationPassword.isEncryptedAfterUpgrade());
		assertTrue(configurationPassword.isInNeedOfNewEncryptionProperty());
	}

	@Test
	public void testMissingPassword() throws Exception {
		// Setup
		final Properties props = new Properties();
		props.setProperty("typo.password", "blackduck");

		// Execute code under test
		final ConfigurationPassword psw = ConfigurationPassword.createFromProperty(
				props, "protex");

		assertEquals(null, psw.getPlainText());
		assertEquals(null, psw.getEncrypted());
	}

	@Test
	public void testIncompatibleProperties() {
		// Setup
		final Properties props = new Properties();
		props.setProperty(
				"connector.0.password",
				"_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
		props.setProperty("connector.0.password.isencrypted", "true");
		props.setProperty("connector.0.password.isplaintext", "false");

		// Execute code under test
		try {
			ConfigurationPassword.createFromProperty(props, "connector.0");
			fail("Expected exception wasn't thrown");
		} catch (final IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void testNoPasswordProperties() throws Exception {
		// Setup
		final Properties props = new Properties();

		// Execute code under test
		final ConfigurationPassword psw = ConfigurationPassword.createFromProperty(
				props, "protex");

		assertEquals(null, psw.getPlainText());
		assertEquals(null, psw.getEncrypted());
	}

	@Test
	public void testEmptyPassword() throws Exception {
		// Setup
		final Properties props = new Properties();
		props.setProperty("connector.0.password", "");
		props.setProperty("connector.0.password.isencrypted", "false");

		// Execute code under test
		final ConfigurationPassword psw = ConfigurationPassword.createFromProperty(
				props, "protex");

		assertEquals(null, psw.getPlainText());
		assertEquals(null, psw.getEncrypted());
	}

}
