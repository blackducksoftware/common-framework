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

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestCodeCenterConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;

public class ConfigurationManagerPswEncryptionTest {

    // Original tests, which test ONLY the correct interpretation of psw in
    // legacy files
    // (and not the modification of the config file).
    // TODO: There is some redundancy between this and
    // ConfigurationPasswordTest.

    @Test
    public void testDefaultPlainTextStillOkProtex() {
	Properties props = new Properties();
	props.setProperty("protex.server.name", "myserver");
	props.setProperty("protex.user.name", "userName");
	props.setProperty("protex.password", "blackDuck");
	TestProtexConfigurationManager config = new TestProtexConfigurationManager(
		props);

	assertEquals("blackDuck", config.getServerBean().getPassword());
    }

    @Test
    public void testDefaultPlainTextStillOkCodeCenter() {
	Properties props = new Properties();
	props.setProperty("cc.server.name", "myserver");
	props.setProperty("cc.user.name", "userName");
	props.setProperty("cc.password", "blackDuck");
	TestCodeCenterConfigurationManager config = new TestCodeCenterConfigurationManager(
		props);

	assertEquals("blackDuck", config.getServerBean().getPassword());
    }

    @Test
    public void testWithIsPlainTextPropertyProtex() {
	Properties props = new Properties();
	props.setProperty("protex.server.name", "myserver");
	props.setProperty("protex.user.name", "userName");
	props.setProperty("protex.password", "blackDuck");
	props.setProperty("protex.password.isplaintext", "true");
	TestProtexConfigurationManager config = new TestProtexConfigurationManager(
		props);

	assertEquals("blackDuck", config.getServerBean().getPassword());
    }

    @Test
    public void testWithIsPlainTextPropertyCodeCenter() {
	Properties props = new Properties();
	props.setProperty("cc.server.name", "myserver");
	props.setProperty("cc.user.name", "userName");
	props.setProperty("cc.password", "blackDuck");
	props.setProperty("cc.password.isplaintext", "true");

	TestCodeCenterConfigurationManager config = new TestCodeCenterConfigurationManager(
		props);
	assertEquals("blackDuck", config.getServerBean().getPassword());
    }

    /**
     * Base64 encoded passwords withOUT the *.password.isplaintext=false will be
     * treated (INcorrectly) as plain text.
     */
    @Test
    public void testBase64EncodedPasswordProtex() {
	Properties props = new Properties();
	props.setProperty("protex.server.name", "myserver");
	props.setProperty("protex.user.name", "userName");
	props.setProperty("protex.password", "YmxhY2tEdWNr");
	TestProtexConfigurationManager config = new TestProtexConfigurationManager(
		props);

	// System.out.println("Log should contain a warning about a possibly-base64-encoded password");
	assertEquals("YmxhY2tEdWNr", config.getServerBean().getPassword());
    }

    /**
     * Base64 encoded passwords withOUT the *.password.isplaintext=false will be
     * treated (INcorrectly) as plain text.
     */
    @Test
    public void testBase64EncodedPasswordCodeCenter() {
	Properties props = new Properties();
	props.setProperty("cc.server.name", "myserver");
	props.setProperty("cc.user.name", "userName");
	props.setProperty("cc.password", "YmxhY2tEdWNr");
	TestCodeCenterConfigurationManager config = new TestCodeCenterConfigurationManager(
		props);

	// System.out.println("Log should contain a warning about a possibly-base64-encoded password");
	assertEquals("YmxhY2tEdWNr", config.getServerBean().getPassword());
    }

    /**
     * Base64 encoded passwords WITH the *.password.isplaintext=false will be
     * (correctly) decoded.
     */
    @Test
    public void testBase64EncodedPasswordWithIsPlainTextFalseProtex() {
	Properties props = new Properties();
	props.setProperty("protex.server.name", "myserver");
	props.setProperty("protex.user.name", "userName");
	props.setProperty("protex.password", "YmxhY2tkdWNr");
	props.setProperty("protex.password.isplaintext", "false");
	TestProtexConfigurationManager config = new TestProtexConfigurationManager(
		props);

	assertEquals("blackduck", config.getServerBean().getPassword());
    }

    /**
     * Base64 encoded passwords WITH the *.password.isplaintext=false will be
     * (correctly) decoded.
     */
    @Test
    public void testBase64EncodedPasswordWithIsPlainTextFalseCodeCenter() {
	Properties props = new Properties();
	props.setProperty("cc.server.name", "myserver");
	props.setProperty("cc.user.name", "userName");
	props.setProperty("cc.password", "YmxhY2tkdWNr");
	props.setProperty("cc.password.isplaintext", "false");
	TestCodeCenterConfigurationManager config = new TestCodeCenterConfigurationManager(
		props);

	assertEquals("blackduck", config.getServerBean().getPassword());
    }

    @Test
    public void testEncryptedPasswordProtex() {
	Properties props = new Properties();
	props.setProperty("protex.server.name", "myserver");
	props.setProperty("protex.user.name", "userName");
	props.setProperty(
		"protex.password",
		"_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
	props.setProperty("protex.password.isencrypted", "true");
	TestProtexConfigurationManager config = new TestProtexConfigurationManager(
		props);

	assertEquals("blackduck", config.getServerBean().getPassword());
    }

    @Test
    public void testEncryptedPasswordCodeCenter() {
	Properties props = new Properties();
	props.setProperty("cc.server.name", "myserver");
	props.setProperty("cc.user.name", "userName");
	props.setProperty(
		"cc.password",
		"_=ZTu,6$3,7u>Ji3SHP(lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P");
	props.setProperty("cc.password.isencrypted", "true");
	TestCodeCenterConfigurationManager config = new TestCodeCenterConfigurationManager(
		props);

	assertEquals("blackduck", config.getServerBean().getPassword());
    }

}
