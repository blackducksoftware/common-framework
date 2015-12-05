/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.config;

import java.io.File;
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

    /** The Protex config manager. */
    private static ConfigurationManager protexCM = null;

    /** The Code Center config manager. */
    private static ConfigurationManager ccCM = null;

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
        String fullLocation = ClassLoader.getSystemResource(testFile).getFile();

        protexCM = new TestConfigurationManagerBean(fullLocation,
                APPLICATION.PROTEX);
        ccCM = new TestConfigurationManagerBean(fullLocation,
                APPLICATION.CODECENTER);
    }

    /**
     * Tests to make sure that the list is parsed and the server bean is correct
     * The list specified is the /server/test_server_config.xml
     */
    @Test
    public void testConfigFileWithServerListAndOldConfig() {
        String fullLocationFileWithList = ClassLoader.getSystemResource(
                testFileWithServerList).getFile();

        ConfigurationManager protexCM = new TestConfigurationManagerBean(
                fullLocationFileWithList, APPLICATION.PROTEX);

        ConfigurationManager ccCM = new TestConfigurationManagerBean(
                fullLocationFileWithList, APPLICATION.CODECENTER);

        ServerBean protexServerBean = protexCM.getServerBean();
        ServerBean ccServerBean = ccCM.getServerBean();

        Assert.assertEquals("bla_protex", protexServerBean.getServerName());
        Assert.assertEquals("bla_codecenter", ccServerBean.getServerName());

    }

    @Test
    public void testConfigFileWithOnlyServerList() {
        String fullLocationFileWithList = ClassLoader.getSystemResource(
                testFileWithOnlyServerList).getFile();

        ConfigurationManager protexCM = new TestConfigurationManagerBean(
                fullLocationFileWithList, APPLICATION.PROTEX);

        ServerBean protexServerBean = protexCM.getServerBean();
        Assert.assertEquals("bla_protex", protexServerBean.getServerName());

    }

    /**
     * Tests configuration manager when a fully qualified path is provided.
     */

    @Test
    public void testInitializerWithGoodFileAndGoodParams() {
        try {
            // Protex
            String server = protexCM
                    .getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
            String user = protexCM
                    .getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
            String password = protexCM
                    .getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);

            Assert.assertEquals("myserver", server);
            Assert.assertEquals("userName", user);
            Assert.assertEquals("blackDuck", password);

            // CC
            server = ccCM.getProperty(ConfigConstants.CC_SERVER_NAME_PROPERTY);
            user = ccCM.getProperty(ConfigConstants.CC_USER_NAME_PROPERTY);
            password = ccCM.getProperty(ConfigConstants.CC_PASSWORD_PROPERTY);

            Assert.assertEquals("cc_server", server);
            Assert.assertEquals("cc_user", user);
            Assert.assertEquals("cc_password", password);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Tests that all the column mappings were properly parsed.
     */
    @Test
    public void testMappings() {
        Map<String, String> mappings = protexCM.getMappings();
        int size = mappings.size();

        Assert.assertEquals(5, size);

        String columnOneValue = mappings.get("ColumnA");
        String columnTwoValue = mappings.get("ColumnB");
        String columnFourValue = mappings.get("ColumnC");
        String columnOnePageTwoValue = mappings.get("ColumnA_Sheet2");
        String columnTwoPageTwoValue = mappings.get("ColumnB_Sheet2");

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

        String fullLocation = ClassLoader.getSystemResource(
                testFileDuplicateMappings).getFile();

        // Use a throw away CM to not dirty up other tests.
        TestProtexConfigurationManager protexCM = new TestProtexConfigurationManager(
                fullLocation);
        protexCM.getMappings();
    }

    /**
     * Test initializer with good file and missing key.
     */
    @Test
    public void testInitializerWithGoodFileAndMissingKey() {
        String bogusKey = "BOGUS KEY";
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Property key DNE: " + bogusKey);
        protexCM.getProperty(bogusKey);
    }

    /**
     * Test initializer with good file and missing value.
     */
    @Test
    public void testInitializerWithGoodFileAndMissingValue() {
        String emptyKey = "empty.key";
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Value DNE for key: " + emptyKey);
        protexCM.getProperty(emptyKey);
    }

    /**
     * Test to make sure that missing optional keys do not cause a malfunction.
     */
    @Test
    public void testInitializerWithOptionalKeys() {
        String emptyKey = "empty.key";
        String emptyValue = protexCM.getOptionalProperty(emptyKey);
        Assert.assertEquals("", emptyValue);

        String bogusKey = "bogus.key";
        String nullValue = protexCM.getOptionalProperty(bogusKey);
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
        String protexServerName = protexCM.getServerBean().getServerName();
        String protexUser = protexCM.getServerBean().getUserName();
        String protexPass = protexCM.getServerBean().getPassword();

        String ccServerName = ccCM.getServerBean().getServerName();
        String ccUser = ccCM.getServerBean().getUserName();
        String ccPass = ccCM.getServerBean().getPassword();

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
        File workingConfigFile = File.createTempFile(
                "ConfigurationManagerTest.testEmailPropertiesDifficult",
                "config");
        FileUtils.copyFile(new File(
                "src/test/resources/email/email_test_config.properties"),
                workingConfigFile);
        ConfigurationManager emailCM = new TestConfigurationManagerBean(
                workingConfigFile.getAbsolutePath(), APPLICATION.PROTEX);

        Assert.assertEquals("email_auth_password", emailCM
                .getEmailConfiguration().getAuthPassword());
    }

    /**
     * Test double use of config file.
     */
    @Test
    public void testEmailPropertiesEasy() {
        Assert.assertEquals("email_smtp_address", protexCM
                .getEmailConfiguration().getSmtpAddress());
        Assert.assertEquals("email_auth_password", protexCM
                .getEmailConfiguration().getAuthPassword());
    }

    @Test
    public void testEmailRules() {
        List<EmailTriggerRule> emailRules = protexCM
                .getNotificationRulesConfiguration().getRules();

        Assert.assertEquals(2, emailRules.size());

        String ruleName = emailRules.get(0).getRuleName();
        Assert.assertEquals("MYRULE", ruleName);
        String anotherRule = emailRules.get(1).getRuleName();
        Assert.assertEquals("ARULE", anotherRule);
    }

    /**
     * Tests that all the getters for common required information.
     */
    @Test
    public void testGettersForCommonOptionalProps() {
        String proxyPort = protexCM.getProxyPort();
        Assert.assertEquals("80", proxyPort);

        String proxyServer = protexCM.getProxyServer();
        Assert.assertEquals("proxy.server", proxyServer);

        String proxyServerHtpps = protexCM.getProxyServerHttps();
        Assert.assertEquals("proxy.https.server", proxyServerHtpps);

        String proxyPortHttps = protexCM.getProxyPortHttps();
        Assert.assertEquals("8080", proxyPortHttps);
    }

    @Test
    public void testSSLSettings()
    {
        URL resourceUrl = Thread.currentThread().getContextClassLoader()
                .getResource(testFileSSLMappings);
        Path resourcePath = null;
        try {
            resourcePath = Paths.get(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }

        ConfigurationManager sslCM = new TestConfigurationManagerBean(
                resourcePath.toFile().getAbsolutePath(), APPLICATION.PROTEX);

        SSOBean ssoBean = sslCM.getSsoBean();

        Assert.assertEquals("keystore_path", ssoBean.getKeyStorePath());
        Assert.assertEquals("key_pass", ssoBean.getKeyStorePassword());
        Assert.assertEquals("jks", ssoBean.getKeyStoreType());
        Assert.assertEquals("truststore_path", ssoBean.getTrustStorePath());
        Assert.assertEquals("trust_pass", ssoBean.getTrustStorePassword());
        Assert.assertEquals("pk12", ssoBean.getTrustStoreType());

    }

}
