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
package com.blackducksoftware.tools.commonframework.standard.protex.report.template;

import com.blackducksoftware.tools.commonframework.standard.protex.report.model.TemplatePojo;

/**
 * Test pojo used to test out the reflection mappings defined in the
 * test_config.properties
 * 
 * @author akamen
 *
 */
public class TestPojo implements TemplatePojo {

    /** The value1. */
    private String value1;

    /** The value2. */
    private String value2;

    /** The value4. */
    private String value4;

    /**
     * Gets the value1.
     *
     * @return the value1
     */
    public String getValue1() {
	return value1;
    }

    /**
     * Sets the value1.
     *
     * @param value1
     *            the new value1
     */
    public void setValue1(String value1) {
	this.value1 = value1;
    }

    /**
     * Gets the value2.
     *
     * @return the value2
     */
    public String getValue2() {
	return value2;
    }

    /**
     * Sets the value2.
     *
     * @param value2
     *            the new value2
     */
    public void setValue2(String value2) {
	this.value2 = value2;
    }

    /**
     * Gets the value4.
     *
     * @return the value4
     */
    public String getValue4() {
	return value4;
    }

    /**
     * Sets the value4.
     *
     * @param value4
     *            the new value4
     */
    public void setValue4(String value4) {
	this.value4 = value4;
    }
}
