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
package com.blackducksoftware.tools.commonframework.test;

import java.io.InputStream;
import java.util.Properties;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.user.CommonUser;

/**
 * Extended test config class for testing purposes only. Since we cannot test an
 * abstract class, we will test its inherited class.
 * 
 * This tests the Protex Config Manager only
 * 
 * @author akamen
 *
 */
public class TestProtexConfigurationManager extends ConfigurationManager {

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param configFileLocation
     *            the config file location
     */
    public TestProtexConfigurationManager(String configFileLocation) {
	super(configFileLocation, APPLICATION.PROTEX);
    }

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param user
     *            the user
     */
    public TestProtexConfigurationManager(CommonUser user) {
	super(user, APPLICATION.PROTEX);
    }

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param is
     *            the is
     */
    public TestProtexConfigurationManager(InputStream is) {
	super(is, APPLICATION.PROTEX);
    }

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param props
     *            the props
     */
    public TestProtexConfigurationManager(Properties props) {
	super(props, APPLICATION.PROTEX);
    }
}
