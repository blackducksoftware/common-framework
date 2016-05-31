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
package com.blackducksoftware.tools.commonframework.core.config.server;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// TODO: Auto-generated Javadoc
/**
 * Tests the parsing of various types of configuration files. The basic
 * configuration will test the number of individual sever configurations to
 * determine success fail.
 * 
 * We expect 2,3,4 types of configs for yaml, json, xml respectively.
 * 
 * @author akamen
 *
 */
public class ServerConfigurationParserTest {

    /** The test yaml file. */
    public static String testYAMLFile = "server/test_server_config.yaml";

    /** The test json file. */
    public static String testJSONFile = "server/test_server_config.json";

    /** The test xml file. */
    public static String testXMLFile = "server/test_server_config.xml";

    /** The parser. */
    private ServerConfigurationParser parser = null;

    /** The exception. */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Sets the up before class.
     */
    @BeforeClass
    static public void setUpBeforeClass() {

    }

    /**
     * Test basic yaml configuration file.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testBasicYamlConfigurationFile() throws Exception {
	String fullLocation = ClassLoader.getSystemResource(testYAMLFile)
		.getFile();
	parser = new ServerConfigurationParser(fullLocation);
	List<ServerBean> serverList = parser.processServerConfiguration();
	
	assertEquals(2, serverList.size());
    }

    /**
     * Test basic json configuration file.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testBasicJSONConfigurationFile() throws Exception {
	String fullLocation = ClassLoader.getSystemResource(testJSONFile)
		.getFile();
	parser = new ServerConfigurationParser(fullLocation);
	List<ServerBean> serverList = parser.processServerConfiguration();
	assertEquals(3, serverList.size());
    }

    /**
     * Test basic xml configuration file.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testBasicXMLConfigurationFile() throws Exception {
	String fullLocation = ClassLoader.getSystemResource(testXMLFile)
		.getFile();
	parser = new ServerConfigurationParser(fullLocation);
	List<ServerBean> serverList = parser.processServerConfiguration();
	assertEquals(4, serverList.size());
    }

}
