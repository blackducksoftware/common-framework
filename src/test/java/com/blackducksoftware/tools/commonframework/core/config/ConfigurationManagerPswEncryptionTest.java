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

import java.util.Properties;

import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants.APPLICATION;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestCodeCenterConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;

public class ConfigurationManagerPswEncryptionTest {

    // Original tests, which test ONLY the correct interpretation of psw in
    // legacy files
    // (and not the modification of the config file).
    // TODO: There is some redundancy between this and
    // ConfigurationPasswordTest.

    APPLICATION protex = APPLICATION.PROTEX;

    APPLICATION cc = APPLICATION.CODECENTER;

    @Test
    public void testDefaultPlainTextStillOkProtex() {
        Properties props = new Properties();
        props.setProperty("protex.server.name", "myserver");
        props.setProperty("protex.user.name", "userName");
        props.setProperty("protex.password", "blackDuck");
        TestProtexConfigurationManager config = new TestProtexConfigurationManager(
                props);

        assertEquals("blackDuck", config.getServerBean(protex).getPassword());
    }

    @Test
    public void testDefaultPlainTextStillOkCodeCenter() {
        Properties props = new Properties();
        props.setProperty("cc.server.name", "myserver");
        props.setProperty("cc.user.name", "userName");
        props.setProperty("cc.password", "blackDuck");
        TestCodeCenterConfigurationManager config = new TestCodeCenterConfigurationManager(
                props);

        assertEquals("blackDuck", config.getServerBean(cc).getPassword());
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

        assertEquals("blackDuck", config.getServerBean(protex).getPassword());
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
        assertEquals("blackDuck", config.getServerBean(cc).getPassword());
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
        assertEquals("YmxhY2tEdWNr", config.getServerBean(protex).getPassword());
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
        assertEquals("YmxhY2tEdWNr", config.getServerBean(cc).getPassword());
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

        assertEquals("blackduck", config.getServerBean(protex).getPassword());
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

        assertEquals("blackduck", config.getServerBean(cc).getPassword());
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

        assertEquals("blackduck", config.getServerBean(protex).getPassword());
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

        assertEquals("blackduck", config.getServerBean(cc).getPassword());
    }

}
