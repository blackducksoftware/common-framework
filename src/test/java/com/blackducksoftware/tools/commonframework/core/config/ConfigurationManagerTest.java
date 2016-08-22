/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 *  under the License.
 *
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants.APPLICATION;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestConfigurationManagerBean;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;
import com.blackducksoftware.tools.commonframework.standard.email.EmailTriggerRule;

/**
 * The Class ConfigurationManagerTest.
 */
public class ConfigurationManagerTest {

	private final APPLICATION protex = APPLICATION.PROTEX;

	private final APPLICATION cc = APPLICATION.CODECENTER;

	private static ConfigurationManager configurationManager = null;

	/* Basic config files */
	public static String testFile = "test_config.properties";

	public static String testFileWithServerList = "test_config_with_serverlist.properties";

	public static String testFileWithOnlyServerList = "test_config_with_only_serverlist.properties";

	/** The test file no mappings. */
	public static String testFileNoMappings = "test_config_no_mappings.properties";

	/** The test file duplicate mappings. */
	public static String testFileDuplicateMappings = "test_config_duplicate_mappings.properties";

	/**
	 * Mappings with SSL information (for SSO testing)
	 */
	public static String testFileSSLMappings = "test_config_with_ssl.properties";

	/** Config mgr/file: base64-encoded password */
	// private static TestProtexConfigurationManager configMgrBase64Password =
	// null;
	// private static String configFilenameBase64Password =
	// "test_config_base64_psw.properties";

	/** Config msg/file: base64-encoded password overridden */
	// private static TestProtexConfigurationManager
	// configMgrBase64PswOverridden = null;
	// private static String configFilenameBase64PswOverridden =
	// "test_config_base64_psw_overridden.properties";

	/** The exception. */
	@Rule
	public ExpectedException exception = ExpectedException.none();

	/**
	 * Sets the up before class.
	 */
	@BeforeClass
	static public void setUpBeforeClass() {
		final String fullLocation = ClassLoader.getSystemResource(testFile).getFile();

		configurationManager = new TestConfigurationManagerBean(fullLocation);
	}

	/**
	 * Tests to make sure that the list is parsed and the server bean is correct
	 * The list specified is the /server/test_server_config.xml
	 */
	@Test
	public void testConfigFileWithServerListAndOldConfig() {
		final String fullLocationFileWithList = ClassLoader.getSystemResource(
				testFileWithServerList).getFile();

		final ConfigurationManager cm = new TestConfigurationManagerBean(
				fullLocationFileWithList);

		final ServerBean protexServerBean = cm.getServerBean(protex);
		final ServerBean ccServerBean = cm.getServerBean(cc);

		Assert.assertEquals("bla_protex", protexServerBean.getServerName());
		Assert.assertEquals("bla_codecenter", ccServerBean.getServerName());

	}

	@Test
	public void testConfigFileWithOnlyServerList() {
		final String fullLocationFileWithList = ClassLoader.getSystemResource(
				testFileWithOnlyServerList).getFile();

		final ConfigurationManager protexCM = new TestConfigurationManagerBean(
				fullLocationFileWithList);

		final ServerBean protexServerBean = protexCM.getServerBean(protex);
		Assert.assertEquals("bla_protex", protexServerBean.getServerName());

	}

	/**
	 * Tests configuration manager when a fully qualified path is provided.
	 */

	@Test
	public void testInitializerWithGoodFileAndGoodParams() {
		try {
			// Protex
			String server = configurationManager
					.getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
			String user = configurationManager
					.getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
			String password = configurationManager
					.getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);

			Assert.assertEquals("myserver", server);
			Assert.assertEquals("userName", user);
			Assert.assertEquals("blackDuck", password);

			// CC
			server = configurationManager.getProperty(ConfigConstants.CC_SERVER_NAME_PROPERTY);
			user = configurationManager.getProperty(ConfigConstants.CC_USER_NAME_PROPERTY);
			password = configurationManager.getProperty(ConfigConstants.CC_PASSWORD_PROPERTY);

			Assert.assertEquals("cc_server", server);
			Assert.assertEquals("cc_user", user);
			Assert.assertEquals("cc_password", password);

		} catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Tests that all the column mappings were properly parsed.
	 */
	@Test
	public void testMappings() {
		final Map<String, String> mappings = configurationManager.getMappings();
		final int size = mappings.size();

		Assert.assertEquals(5, size);

		final String columnOneValue = mappings.get("ColumnA");
		final String columnTwoValue = mappings.get("ColumnB");
		final String columnFourValue = mappings.get("ColumnC");
		final String columnOnePageTwoValue = mappings.get("ColumnA_Sheet2");
		final String columnTwoPageTwoValue = mappings.get("ColumnB_Sheet2");

		Assert.assertEquals("Value1", columnOneValue);
		Assert.assertEquals("Value2", columnTwoValue);
		Assert.assertEquals("Value4", columnFourValue);
		Assert.assertEquals("Value1Page2", columnOnePageTwoValue);
		Assert.assertEquals("Value2Page2", columnTwoPageTwoValue);
	}

	/**
	 * Make sure the consumer of the mappings is aware that mappings same
	 * columns to different methods will not work.
	 */
	@Test
	public void testMappingUniqueness() {
		exception.expect(IllegalArgumentException.class);
		exception
		.expectMessage("ColumnA is mapped more than once to non-unique methods.");

		final String fullLocation = ClassLoader.getSystemResource(
				testFileDuplicateMappings).getFile();

		// Use a throw away CM to not dirty up other tests.
		final TestProtexConfigurationManager protexCM = new TestProtexConfigurationManager(
				fullLocation);
		protexCM.getMappings();
	}

	/**
	 * Test initializer with good file and missing key.
	 */
	@Test
	public void testInitializerWithGoodFileAndMissingKey() {
		final String bogusKey = "BOGUS KEY";
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Property key DNE: " + bogusKey);
		configurationManager.getProperty(bogusKey);
	}

	/**
	 * Test initializer with good file and missing value.
	 */
	@Test
	public void testInitializerWithGoodFileAndMissingValue() {
		final String emptyKey = "empty.key";
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Value DNE for key: " + emptyKey);
		configurationManager.getProperty(emptyKey);
	}

	/**
	 * Test to make sure that missing optional keys do not cause a malfunction.
	 */
	@Test
	public void testInitializerWithOptionalKeys() {
		final String emptyKey = "empty.key";
		final String emptyValue = configurationManager.getOptionalProperty(emptyKey);
		Assert.assertEquals("", emptyValue);

		final String bogusKey = "bogus.key";
		final String nullValue = configurationManager.getOptionalProperty(bogusKey);
		Assert.assertNull(nullValue);
	}

	/**
	 * Tests the configuration manager when a bad path is provided.
	 */

	@Test
	public void testInitializerWithBadFile() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("File DNE @: test_config.properties");
		new TestProtexConfigurationManager(testFile);

	}

	/**
	 * Test getters for common required props.
	 */
	@Test
	public void testGettersForCommonRequiredProps() {
		final ServerBean protexBean = configurationManager.getServerBean(protex);
		final String protexServerName = protexBean.getServerName();
		final String protexUser = protexBean.getUserName();
		final String protexPass = protexBean.getPassword();

		final ServerBean ccBean = configurationManager.getServerBean(cc);
		final String ccServerName = ccBean.getServerName();
		final String ccUser = ccBean.getUserName();
		final String ccPass = ccBean.getPassword();

		Assert.assertEquals("myserver", protexServerName);
		Assert.assertEquals("userName", protexUser);
		Assert.assertEquals("blackDuck", protexPass);

		Assert.assertEquals("cc_server", ccServerName);
		Assert.assertEquals("cc_user", ccUser);
		Assert.assertEquals("cc_password", ccPass);
	}

	/**
	 * Test single use of config file.
	 *
	 * @throws IOException
	 */
	@Test
	public void testEmailPropertiesSingleUse() throws IOException {
		final File workingConfigFile = File.createTempFile(
				"ConfigurationManagerTest.testEmailPropertiesDifficult",
				"config");
		FileUtils.copyFile(new File(
				"src/test/resources/email/email_test_config.properties"),
				workingConfigFile);
		final ConfigurationManager emailCM = new TestConfigurationManagerBean(
				workingConfigFile.getAbsolutePath());

		Assert.assertEquals("email_auth_password", emailCM
				.getEmailConfiguration().getAuthPassword());
	}

	/**
	 * Test double use of config file.
	 */
	@Test
	public void testEmailPropertiesEasy() {
		Assert.assertEquals("email_smtp_address", configurationManager
				.getEmailConfiguration().getSmtpAddress());
		Assert.assertEquals("email_auth_password", configurationManager
				.getEmailConfiguration().getAuthPassword());
	}

	@Test
	public void testEmailRules() {
		final List<EmailTriggerRule> emailRules = configurationManager
				.getNotificationRulesConfiguration().getRules();

		Assert.assertEquals(2, emailRules.size());

		final String ruleName = emailRules.get(0).getRuleName();
		Assert.assertEquals("MYRULE", ruleName);
		final String anotherRule = emailRules.get(1).getRuleName();
		Assert.assertEquals("ARULE", anotherRule);
	}

	/**
	 * Tests that all the getters for common required information.
	 */
	@Test
	public void testGettersForCommonOptionalProps() {
		final ProxyBean proxy = configurationManager.getProxyBean();
		final String proxyPort = proxy.getProxyPort();
		Assert.assertEquals("80", proxyPort);

		final String proxyServer = proxy.getProxyServer();
		Assert.assertEquals("proxy.server", proxyServer);

		final String proxyServerHtpps = proxy.getProxyServerHttps();
		Assert.assertEquals("proxy.https.server", proxyServerHtpps);

		final String proxyPortHttps = proxy.getProxyPortHttps();
		Assert.assertEquals("8080", proxyPortHttps);
	}

	@Test
	public void testSSLSettings()
	{
		final URL resourceUrl = Thread.currentThread().getContextClassLoader()
				.getResource(testFileSSLMappings);
		Path resourcePath = null;
		try {
			resourcePath = Paths.get(resourceUrl.toURI());
		} catch (final URISyntaxException e) {
			Assert.fail(e.getMessage());
		}

		final ConfigurationManager sslCM = new TestConfigurationManagerBean(
				resourcePath.toFile().getAbsolutePath());

		final SSOBean ssoBean = sslCM.getSsoBean();

		Assert.assertEquals("keystore_path", ssoBean.getKeyStorePath());
		Assert.assertEquals("key_pass", ssoBean.getKeyStorePassword());
		Assert.assertEquals("jks", ssoBean.getKeyStoreType());
		Assert.assertEquals("truststore_path", ssoBean.getTrustStorePath());
		Assert.assertEquals("trust_pass", ssoBean.getTrustStorePassword());
		Assert.assertEquals("pk12", ssoBean.getTrustStoreType());

	}

	@Test
	public void testConstructorConsistency() throws FileNotFoundException {
		final File configFile = new File("src/test/resources/appedit.properties");
		final TestProtexConfigurationManager configByFile = new TestProtexConfigurationManager(configFile);
		final TestProtexConfigurationManager configByPath = new TestProtexConfigurationManager(
				configFile.getAbsolutePath());
		final TestProtexConfigurationManager configByIs = new TestProtexConfigurationManager(new FileInputStream(
				configFile));

		assertEquals("blackduck", configByFile.getServerBean(APPLICATION.CODECENTER).getPassword(),
				configByPath.getServerBean(APPLICATION.CODECENTER).getPassword(),
				configByIs.getServerBean(APPLICATION.CODECENTER).getPassword());
		assertEquals("(a test)", configByFile.getServerBean(APPLICATION.PROTEX).getPassword(), configByPath
				.getServerBean(APPLICATION.PROTEX).getPassword(), configByIs.getServerBean(APPLICATION.PROTEX)
				.getPassword());
		assertEquals("[A-Za-z0-9@_.-]+", configByFile.getFieldInputValidationRegexUsername(),
				configByPath.getFieldInputValidationRegexUsername(), configByIs.getFieldInputValidationRegexUsername());
		assertEquals("(a test)", configByFile.getProperty("unescape.test"), configByPath.getProperty("unescape.test"),
				configByIs.getProperty("unescape.test"));
		assertEquals("(a test)", configByFile.getUnEscapeTestValue(), configByPath.getUnEscapeTestValue(),
				configByIs.getUnEscapeTestValue());
		assertEquals("(this is in unescaped parens)", configByFile.getProperty("paren.test"),
				configByPath.getProperty("paren.test"), configByIs.getProperty("paren.test"));
		assertEquals("\\", configByFile.getProperty("backslash.test"), configByPath.getProperty("backslash.test"),
				configByIs.getProperty("backslash.test"));
		assertEquals("[]", configByFile.getProperty("bracket.test"), configByPath.getProperty("bracket.test"),
				configByIs.getProperty("bracket.test"));
		assertEquals("$", configByFile.getProperty("dollar.test"), configByPath.getProperty("dollar.test"),
				configByIs.getProperty("dollar.test"));
		assertEquals("=", configByFile.getProperty("equals.test"), configByPath.getProperty("equals.test"),
				configByIs.getProperty("equals.test"));
		assertEquals("([=$])", configByFile.getProperty("without.escape.test"),
				configByPath.getProperty("without.escape.test"), configByIs.getProperty("without.escape.test"));
	}

	private void assertEquals(final String s1, final String s2, final String s3, final String s4) {
		Assert.assertEquals(s1, s2);
		Assert.assertEquals(s2, s3);
		Assert.assertEquals(s3, s4);
	}

}
