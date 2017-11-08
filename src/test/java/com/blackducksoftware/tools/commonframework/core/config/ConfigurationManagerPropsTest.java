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

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationManagerPropsTest.
 */
public class ConfigurationManagerPropsTest {

    /** The tcm. */
    private static TestProtexConfigurationManager tcm = null;

    /** The exception. */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    static public void setUpBeforeClass() throws Exception {
	Properties props = new Properties();
	props.setProperty("protex.server.name", "myserver");
	props.setProperty("protex.user.name", "userName");
	props.setProperty("protex.password", "blackDuck");

	tcm = new TestProtexConfigurationManager(props);
    }

    /**
     * Tests configuration manager when an input stream is provided.
     */
    @Test
    public void testInitializerWithGoodFileAndGoodParams() {
	try {
	    String server = tcm
		    .getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
	    String user = tcm
		    .getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
	    String password = tcm
		    .getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);

	    Assert.assertEquals("myserver", server);
	    Assert.assertEquals("userName", user);
	    Assert.assertEquals("blackDuck", password);

	} catch (Exception e) {
	    Assert.fail(e.getMessage());
	}
    }

    /**
     * Tests getProps() method.
     */
    @Test
    public void testGetProps() {
	try {
	    Properties props = tcm.getProps();

	    String server = props
		    .getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
	    String user = props
		    .getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
	    String password = props
		    .getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);

	    Assert.assertEquals("myserver", server);
	    Assert.assertEquals("userName", user);
	    Assert.assertEquals("blackDuck", password);

	} catch (Exception e) {
	    Assert.fail(e.getMessage());
	}
    }
}
