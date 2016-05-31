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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.CRC32;

import net.jmatrix.eproperties.EProperties;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants.APPLICATION;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.encoding.Ascii85EncoderTest;

public class ConfigurationFileTest {
    private static List<File> filesToDelete = new ArrayList<File>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        for (File fileToDelete : filesToDelete) {
            try {
                System.out
                        .println("Deleting " + fileToDelete.getAbsolutePath());
                fileToDelete.delete();
            } catch (Exception s) {
                System.out.println("Delete failed");
            }
        }
    }

    @Test
    public void testConfigFileRoundTrip() throws Exception {
        File configFile = File.createTempFile(
                "soleng_framework_core_config_ConfigurationFileTest",
                "testConfigFileRoundTrip");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();

        for (int i = 0; i < 1000; i++) {
            Properties props = new Properties();
            String psw = Ascii85EncoderTest.generateRandomPassword(64);
            System.out.println("psw: " + psw);
            props.setProperty("protex.server.name", "servername");
            props.setProperty("protex.user.name", "username");
            props.setProperty("protex.password", psw);

            // Write the config file with plain txt password
            configFile.delete();
            props.store(new FileOutputStream(configFile), "test");

            // First use will encrypt password
            ConfigurationManager config = new TestProtexConfigurationManager(
                    configFile.getAbsolutePath());
            assertEquals(psw, config.getServerBean(APPLICATION.PROTEX).getPassword());

            // Second use will read encrypted password
            config = new TestProtexConfigurationManager(
                    configFile.getAbsolutePath());
            assertEquals(psw, config.getServerBean(APPLICATION.PROTEX).getPassword());
        }

    }

    @Test
    public void testPropsLoading() {
        ConfigurationFile configFile = new ConfigurationFile(
                "src/test/resources/psw_encryption/legacy_plain_notset.properties");
        EProperties props = new EProperties();
        configFile.copyProperties(props);

        assertEquals(18, props.size());
        assertEquals("connector_password",
                props.getProperty("connector.0.password"));
    }

    @Test
    public void testLegacyPasswordPlainTextIsplaintextNotSet() throws Exception {
        File sourceConfigFile = new File(
                "src/test/resources/psw_encryption/legacy_plain_notset.properties");
        File configFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test1");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();
        FileUtils.copyFile(sourceConfigFile, configFile);
        ConfigurationFile cf = new ConfigurationFile(
                configFile.getAbsolutePath());
        List<String> origLines = cf.getLines();

        List<String> updatedLines = null;
        if (cf.isInNeedOfUpdate()) {
            updatedLines = cf.saveWithEncryptedPasswords();
        }

        assertTrue(updatedLines.size() > 0);
        Iterator<String> updatedLinesIter = updatedLines.iterator();
        for (String origLine : origLines) {
            if (!updatedLinesIter.hasNext()) {
                fail("Updated file has fewer lines than original");
            }
            String updatedLine = updatedLinesIter.next();

            // make sure obsolete properties didn't sneak in somehow
            assertFalse(updatedLine.matches("^.*\\.password\\.isplaintext=.*$"));

            // If this is a password, verify that it was encoded, and that the
            // isencrypted=true was inserted after it
            if (origLine.startsWith("cc.password=")) {
                assertEquals(
                        "cc.password=,\\(f9b^6ck-Sr-A2!jWeRlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("cc.password.isencrypted=true", updatedLine);
            } else if (origLine.startsWith("protex.password=")) {
                assertEquals(
                        "protex.password=DQp'L-+/0Fq0jsi2f'\\\\OlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("protex.password.isencrypted=true", updatedLine);
            } else if (origLine.startsWith("connector.0.password=")) {
                assertEquals(
                        "connector.0.password=6'ND2^gdVX/0\\$fYH7TeH04Sh8FAG<\\[lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("connector.0.password.isencrypted=true",
                        updatedLine);
            } else {
                assertEquals(origLine, updatedLine);
            }
        }

        File testGeneratedUpdatedFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test1_testGeneratedUpdatedFile");
        filesToDelete.add(testGeneratedUpdatedFile);
        testGeneratedUpdatedFile.deleteOnExit();
        FileUtils.writeLines(testGeneratedUpdatedFile, updatedLines);
        long csumTestGeneratedFile = FileUtils.checksum(
                testGeneratedUpdatedFile, new CRC32()).getValue();
        long csumActualFile = FileUtils.checksum(configFile, new CRC32())
                .getValue();
        assertEquals(csumTestGeneratedFile, csumActualFile);
    }

    /**
     * Test handling of legacy Passwords in plain text with
     * password.isplaintext=true Does not verify that non-password-related lines
     * survive as-is, but testLegacyPasswordPlainTextIsplaintextNotSet() does
     * that.
     * 
     * @throws Exception
     */
    @Test
    public void testLegacyPasswordPlainTextIsplaintextTrue() throws Exception {
        File sourceConfigFile = new File(
                "src/test/resources/psw_encryption/legacy_plain_set.properties");
        File configFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test2");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();
        FileUtils.copyFile(sourceConfigFile, configFile);
        ConfigurationFile cf = new ConfigurationFile(
                configFile.getAbsolutePath());

        List<String> updatedLines = null;
        if (cf.isInNeedOfUpdate()) {
            updatedLines = cf.saveWithEncryptedPasswords();
        }

        assertTrue(updatedLines.size() > 0);
        Iterator<String> updatedLinesIter = updatedLines.iterator();
        while (updatedLinesIter.hasNext()) {
            String updatedLine = updatedLinesIter.next();

            // make sure obsolete properties have been removed
            assertFalse(updatedLine.matches("^.*\\.password\\.isplaintext=.*$"));

            // If this is a password, verify that it was encoded, and that the
            // isencrypted=true was inserted after it
            if (updatedLine.startsWith("cc.password=")) {
                assertEquals("cc.password=cc_password", updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("cc.password.isencrypted=false", updatedLine);
            } else if (updatedLine.startsWith("protex.password=")) {
                assertEquals("protex.password=protex_password", updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("protex.password.isencrypted=false", updatedLine);
            } else if (updatedLine.startsWith("connector.0.password=")) {
                assertEquals("connector.0.password=connector_password",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("connector.0.password.isencrypted=false",
                        updatedLine);
            }
        }

        File testGeneratedUpdatedFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test2_testGeneratedUpdatedFile");
        filesToDelete.add(testGeneratedUpdatedFile);
        testGeneratedUpdatedFile.deleteOnExit();
        FileUtils.writeLines(testGeneratedUpdatedFile, updatedLines);
        long csumTestGeneratedFile = FileUtils.checksum(
                testGeneratedUpdatedFile, new CRC32()).getValue();
        long csumActualFile = FileUtils.checksum(configFile, new CRC32())
                .getValue();
        assertEquals(csumTestGeneratedFile, csumActualFile);
    }

    /**
     * We don't handle this scenario
     * 
     * @throws Exception
     */
    @Test
    public void testLegacyPasswordBase64IsplaintextNotSet() throws Exception {
        File sourceConfigFile = new File(
                "src/test/resources/psw_encryption/legacy_base64_notset.properties");
        File configFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test3");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();
        FileUtils.copyFile(sourceConfigFile, configFile);
        ConfigurationFile cf = new ConfigurationFile(
                configFile.getAbsolutePath());

        List<String> updatedLines = null;
        if (cf.isInNeedOfUpdate()) {
            updatedLines = cf.saveWithEncryptedPasswords();
        }

        // This warning has been disabled to avoid calling isBase64, which
        // causes problems for RGT
        // System.out.println("There should be 3 warnings in the log about possibly-base64-encoded passwords");

        assertTrue(updatedLines.size() > 0);
        Iterator<String> updatedLinesIter = updatedLines.iterator();
        while (updatedLinesIter.hasNext()) {
            String updatedLine = updatedLinesIter.next();

            // make sure obsolete properties have been removed
            assertFalse(updatedLine.matches("^.*\\.password\\.isplaintext=.*$"));

            // If this is a password, verify that it was encoded, and that the
            // isencrypted=true was inserted after it
            if (updatedLine.startsWith("cc.password=")) {
                // There's not much point in checking the value of password,
                // since it is wrong
                updatedLine = updatedLinesIter.next();
                assertEquals("cc.password.isencrypted=true", updatedLine);
            } else if (updatedLine.startsWith("protex.password=")) {
                // There's not much point in checking the value of password,
                // since it is wrong
                updatedLine = updatedLinesIter.next();
                assertEquals("protex.password.isencrypted=true", updatedLine);
            } else if (updatedLine.startsWith("connector.0.password=")) {
                // There's not much point in checking the value of password,
                // since it is wrong
                updatedLine = updatedLinesIter.next();
                assertEquals("connector.0.password.isencrypted=true",
                        updatedLine);
            }
        }

        File testGeneratedUpdatedFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test3_testGeneratedUpdatedFile");
        filesToDelete.add(testGeneratedUpdatedFile);
        testGeneratedUpdatedFile.deleteOnExit();
        FileUtils.writeLines(testGeneratedUpdatedFile, updatedLines);
        long csumTestGeneratedFile = FileUtils.checksum(
                testGeneratedUpdatedFile, new CRC32()).getValue();
        long csumActualFile = FileUtils.checksum(configFile, new CRC32())
                .getValue();
        assertEquals(csumTestGeneratedFile, csumActualFile);
    }

    @Test
    public void testLegacyPasswordBase64IsplaintextFalse() throws Exception {
        File sourceConfigFile = new File(
                "src/test/resources/psw_encryption/legacy_base64_set.properties");
        File configFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test4");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();
        FileUtils.copyFile(sourceConfigFile, configFile);
        ConfigurationFile cf = new ConfigurationFile(
                configFile.getAbsolutePath());

        List<String> updatedLines = null;
        if (cf.isInNeedOfUpdate()) {
            updatedLines = cf.saveWithEncryptedPasswords();
        }

        assertTrue(updatedLines.size() > 0);
        Iterator<String> updatedLinesIter = updatedLines.iterator();
        while (updatedLinesIter.hasNext()) {
            String updatedLine = updatedLinesIter.next();

            // make sure obsolete properties didn't sneak in somehow
            assertFalse(updatedLine.matches("^.*\\.password\\.isplaintext=.*$"));

            // If this is a password, verify that it was encoded, and that the
            // isencrypted=true was inserted after it
            if (updatedLine.startsWith("cc.password=")) {
                assertEquals(
                        "cc.password=,\\(f9b^6ck-Sr-A2!jWeRlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("cc.password.isencrypted=true", updatedLine);
            } else if (updatedLine.startsWith("protex.password=")) {
                assertEquals(
                        "protex.password=DQp'L-+/0Fq0jsi2f'\\\\OlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("protex.password.isencrypted=true", updatedLine);
            } else if (updatedLine.startsWith("connector.0.password=")) {
                assertEquals(
                        "connector.0.password=6'ND2^gdVX/0\\$fYH7TeH04Sh8FAG<\\[lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("connector.0.password.isencrypted=true",
                        updatedLine);
            }
        }

        File testGeneratedUpdatedFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test4_testGeneratedUpdatedFile");
        filesToDelete.add(testGeneratedUpdatedFile);
        testGeneratedUpdatedFile.deleteOnExit();
        FileUtils.writeLines(testGeneratedUpdatedFile, updatedLines);
        long csumTestGeneratedFile = FileUtils.checksum(
                testGeneratedUpdatedFile, new CRC32()).getValue();
        long csumActualFile = FileUtils.checksum(configFile, new CRC32())
                .getValue();
        assertEquals(csumTestGeneratedFile, csumActualFile);
    }

    @Test
    public void testModernPasswordPlainTextIsEncryptedNotSet() throws Exception {
        File sourceConfigFile = new File(
                "src/test/resources/psw_encryption/modern_plain_notset.properties");
        File configFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test5");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();
        FileUtils.copyFile(sourceConfigFile, configFile);
        ConfigurationFile cf = new ConfigurationFile(
                configFile.getAbsolutePath());

        List<String> updatedLines = null;
        if (cf.isInNeedOfUpdate()) {
            updatedLines = cf.saveWithEncryptedPasswords();
        }

        assertTrue(updatedLines.size() > 0);
        Iterator<String> updatedLinesIter = updatedLines.iterator();
        while (updatedLinesIter.hasNext()) {
            String updatedLine = updatedLinesIter.next();

            // make sure obsolete properties didn't sneak in somehow
            assertFalse(updatedLine.matches("^.*\\.password\\.isplaintext=.*$"));

            // If this is a password, verify that it was encoded, and that the
            // isencrypted=true was inserted after it
            if (updatedLine.startsWith("cc.password=")) {
                assertEquals(
                        "cc.password=,\\(f9b^6ck-Sr-A2!jWeRlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("cc.password.isencrypted=true", updatedLine);
            } else if (updatedLine.startsWith("protex.password=")) {
                assertEquals(
                        "protex.password=DQp'L-+/0Fq0jsi2f'\\\\OlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("protex.password.isencrypted=true", updatedLine);
            } else if (updatedLine.startsWith("connector.0.password=")) {
                assertEquals(
                        "connector.0.password=6'ND2^gdVX/0\\$fYH7TeH04Sh8FAG<\\[lI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
                        updatedLine);
                updatedLine = updatedLinesIter.next();
                assertEquals("connector.0.password.isencrypted=true",
                        updatedLine);
            }
        }

        File testGeneratedUpdatedFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test5_testGeneratedUpdatedFile");
        filesToDelete.add(testGeneratedUpdatedFile);
        testGeneratedUpdatedFile.deleteOnExit();
        FileUtils.writeLines(testGeneratedUpdatedFile, updatedLines);
        long csumTestGeneratedFile = FileUtils.checksum(
                testGeneratedUpdatedFile, new CRC32()).getValue();
        long csumActualFile = FileUtils.checksum(configFile, new CRC32())
                .getValue();
        assertEquals(csumTestGeneratedFile, csumActualFile);
    }

    @Test
    public void testModernPasswordPlainTextIsEncryptedFalse() throws Exception {
        File sourceConfigFile = new File(
                "src/test/resources/psw_encryption/modern_plain_false.properties");
        File configFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test6");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();
        FileUtils.copyFile(sourceConfigFile, configFile);
        ConfigurationFile cf = new ConfigurationFile(
                configFile.getAbsolutePath());

        assertFalse(cf.isInNeedOfUpdate());
    }

    @Test
    public void testInvalidBothPlainAndEncryptedProperties() throws IOException {
        File sourceConfigFile = new File(
                "src/test/resources/psw_encryption/invalid_both.properties");
        File configFile = File
                .createTempFile(
                        "com.blackducksoftware.tools.commonframework.core.config.ConfigurationFileTest",
                        "test7");
        filesToDelete.add(configFile);
        configFile.deleteOnExit();
        FileUtils.copyFile(sourceConfigFile, configFile);

        try {
            new ConfigurationFile(configFile.getAbsolutePath());
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }
}
