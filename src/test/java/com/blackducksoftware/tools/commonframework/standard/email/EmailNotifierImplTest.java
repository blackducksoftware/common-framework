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

/**
 *
 */
package com.blackducksoftware.tools.commonframework.standard.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Properties;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestConfigurationManagerBean;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;
import com.blackducksoftware.tools.commonframework.standard.common.ProjectPojo;
import com.blackducksoftware.tools.commonframework.standard.protex.ProtexProjectPojo;

// import com.blackducksoftware.sdk.protex.project.Project;
// import com.blackducksoftware.sdk.protex.project.codetree.discovery.AnalysisInfo;

/**
 * @author Ari Kamen
 * @date Aug 29, 2014
 * 
 */
public class EmailNotifierImplTest {

    public static String emailProp = "email/email_test_config.properties";

    public static String fullLocationOfEmailConfig = null;

    public static String emailAuthProp = "email/email_test_auth_config.properties";

    public static String notificationFile = "email/scm_connector_notifications.xml";

    public static String fullLocationOfEmailTemplateFile = null;

    private static final String expectedEmailBodyScmConnectorLike = "<h3>Analysis Powered by: Black Duck&trade; Protex</h3>\n"
            + "    <p><b>Project Name:</b> test project </p>\n"
            + "    <p><b>Scan Start Time:</b> Sat Jan 01 00:00:00 EST 2000 </p>\n"
            + "    <p><b>Scan Finish Time:</b> Sat Jan 01 00:00:00 EST 2000 </p>\n"
            + "    <p><b>Scan Performed By:</b> analysis user </p>\n"
            + "    <p><b>Scan Type:</b> Initial Baseline </p>\n"
            + "    <p><b>Code Repository Type:</b> NOP (no operation) </p>\n"
            + "    <p><b>Code Repository Path:</b> <not applicable> </p>\n"
            + "    <p><b>Test custom value:</b> Test Custom Value </p>\n"
            + "    <table border=\"1\">\n"
            + "\t<tr>\n"
            + "\t<td></td>\n"
            + "\t<td>Total #</td>\n"
            + "\t<td>Total # As Part of Delta Scan</td>\n"
            + "\t</tr>\n"
            + "\t\n"
            + "\t<tr>\n"
            + "\t<td>Files Scanned</td>\n"
            + "\t<td>0</td>\n"
            + "\t<td>0</td>\n"
            + "\t</tr>\n"
            + "\t\n"
            + "\t<tr>\n"
            + "\t<td>Pending Identification</td>\n"
            + "\t<td>0</td>\n"
            + "\t<td>0</td>\n"
            + "\t</tr>\n"
            + "\t\n"
            + "\t<tr>\n"
            + "\t<td>Pending Code Match Identification</td>\n"
            + "\t<td>0</td>\n"
            + "\t<td>0</td>\n"
            + "\t</tr>\n"
            + "\t\n"
            + "\t<tr>\n"
            + "\t<td>Pending String Search Identification</td>\n"
            + "\t<td>0</td>\n"
            + "\t<td>0</td>\n"
            + "\t</tr>\n"
            + "\t\n"
            + "\t<tr>\n"
            + "\t<td>Pending Dependency Identification</td>\n"
            + "\t<td>0</td>\n"
            + "\t<td>0</td>\n"
            + "\t</tr>\n"
            + "\t\n"
            + "\t<tr>\n"
            + "\t<td>Pending File Pattern Identification</td>\n"
            + "\t<td>0</td>\n"
            + "\t<td>0</td>\n"
            + "\t</tr>\n"
            + "\t\n"
            + "\t</table>\n"
            + "\t<p><b>BOM URL for project:</b> http://www.blackducksoftware.com </p>\n"
            + "\t\n"
            + "\t<P>Complex Value: </p>\n"
            + "\tFirst line of information.\n" + "Second line of information.";

    // Our test config, which type is not relevant
    private static TestConfigurationManagerBean protexCM = null;

    private static TestConfigurationManagerBean protexAuthCM = null;

    private static ProtexProjectPojo project = new ProtexProjectPojo();

    // Our emailer
    private static CFEmailNotifier emailer = null;

    /** The exception. */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    static public void setUpBeforeClass() throws Exception {
        fullLocationOfEmailConfig = ClassLoader.getSystemResource(emailProp)
                .getFile();
        fullLocationOfEmailTemplateFile = ClassLoader.getSystemResource(
                notificationFile).getFile();
        String fullLocationAuth = ClassLoader.getSystemResource(emailAuthProp)
                .getFile();

        protexCM = new TestConfigurationManagerBean(fullLocationOfEmailConfig);
        protexAuthCM = new TestConfigurationManagerBean(fullLocationAuth);
        project.setProjectName("EmailerProject");

    }

    @Test
    public void testBasicEmailConfig() {
        try {

            emailer = new CFEmailNotifier(protexCM);
            boolean isReady = emailer.isConfigured();

            Assert.assertEquals(true, isReady);

        } catch (Base64DecodingException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthConfig() {
        try {

            emailer = new CFEmailNotifier(protexAuthCM);
            boolean isReady = emailer.isConfigured();

            Assert.assertEquals(true, isReady);

        } catch (Base64DecodingException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Testing dynamic context loading via Velocity
     */
    @Test
    public void testBasicContext() {
        try {
            emailer = new CFEmailNotifier(protexCM);
        } catch (Base64DecodingException e) {
            Assert.fail(e.getMessage());
        }
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("someValue", "Test1");
        values.put("someOtherValue", "Test2");
        // TODO: Skipping test for now
        // emailer.sendNotification(project, notificationFile, values);
    }

    /**
     * Make sure we can generate an email like the SCM Connector does, in
     * advance of pointing SCM Connector to Common Framework for
     * EmailNotifierImpl.
     * 
     * @throws Exception
     */
    @Test
    public void testScmConnectorLikeEmail() throws Exception {
        ProjectPojo projectPojo = new ProtexProjectPojo();

        Properties props = new Properties();
        props.setProperty("protex.server.name", "https://se-px01.dc1.lan");
        props.setProperty("protex.user.name",
                "unitTester@blackducksoftware.com");
        props.setProperty("protex.password", "blackduck");

        ConfigurationManager config = new TestProtexConfigurationManager(
                fullLocationOfEmailConfig);

        CFEmailNotifier notif = new CFEmailNotifier(config);
        EmailContentMap values = notif
                .configureContentMap(fullLocationOfEmailTemplateFile);

        values.put("projectName", "test project");
        values.put("analysisStartDate", "Sat Jan 01 00:00:00 EST 2000");
        values.put("analysisFinishDate", "Sat Jan 01 00:00:00 EST 2000");
        values.put("analyzedBy", "analysis user");
        values.put("scanType", "Initial Baseline"); // ***** TODO: the "info."
        // prefix breaks this for
        // some reason
        values.put("connectorName", "NOP (no operation)");
        values.put("connectorRepositoryPath", "<not applicable>");
        values.put("fileCount", "0");
        values.put("deltaFileCount", "0");
        values.put("pendingIdFileCount", "0");
        values.put("deltaIdFileCount", "0");
        values.put("pendingCodeMatchIdFileCount", "0");
        values.put("deltaCodeMatchIdFileCount", "0");
        values.put("pendingStringSearchIdFileCount", "0");
        values.put("deltaStringSearchIdFileCount", "0");
        values.put("pendingDependenciesIdFileCount", "0");
        values.put("deltaDependenciesIdFileCount", "0");
        values.put("pendingFileDiscoveryPatternIdFileCount", "0");
        values.put("deltaFileDiscoveryPatternIdFileCount", "0");
        values.put("bomUrl", "http://www.blackducksoftware.com");
        values.put("customValue1", "Test Custom Value");
        // Complex value
        StringBuilder sb = new StringBuilder();
        sb.append("First line of information.");
        sb.append("\n");
        sb.append("Second line of information.");
        values.put("complexValue", sb.toString());

        // This realistically only tests whether the keys are being mapped, not
        // the actual sending.
        // No rules
        EmailTemplate email = notif.sendEmail(projectPojo, values, null);

        String emailBody = email.getBody();

        assertEquals(expectedEmailBodyScmConnectorLike, emailBody);
    }

    @Test
    public void testRulesEngine() throws Exception {
        ConfigurationManager config = new TestProtexConfigurationManager(
                fullLocationOfEmailConfig);

        CFEmailNotifier notif = new CFEmailNotifier(config);
        EmailContentMap values = notif
                .configureContentMap(fullLocationOfEmailTemplateFile);
        if (values == null) {
            fail();
        }
    }

}
