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
package com.blackducksoftware.tools.commonframework.core.config.testbeans;

import java.util.Properties;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;

/**
 * The Class TestCodeCenterConfigurationManager.
 */
public class TestCodeCenterConfigurationManager extends ConfigurationManager {

    /**
     * Instantiates a new test code center configuration manager.
     * 
     * @param configFileLocation
     *            the config file location
     */
    public TestCodeCenterConfigurationManager(String configFileLocation) {
        super(configFileLocation, APPLICATION.CODECENTER);
    }

    /**
     * Instantiates a new test code center configuration manager.
     * 
     * @param props
     *            the props
     */
    public TestCodeCenterConfigurationManager(Properties props) {
        super(props, APPLICATION.CODECENTER);
    }
}
