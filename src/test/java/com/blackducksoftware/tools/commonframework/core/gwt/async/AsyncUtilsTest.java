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
package com.blackducksoftware.tools.commonframework.core.gwt.async;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.gwt.async.AsyncUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class AsyncUtilsTest.
 */
public class AsyncUtilsTest {

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Test generate progress bar string50.
     */
    @Test
    public void testGenerateProgressBarString50() {
	String progressBar = AsyncUtils.generateProgressBarString(50);
	assertEquals("|>>>>>>>>>>__________|50%", progressBar);
    }

    /**
     * Test generate progress bar string200.
     */
    @Test
    public void testGenerateProgressBarString200() {
	String progressBar = AsyncUtils.generateProgressBarString(200);
	assertEquals("|>>>>>>>>>>>>>>>>>>>>|100%", progressBar);
    }

    /**
     * Test generate progress bar string minus50.
     */
    @Test
    public void testGenerateProgressBarStringMinus50() {
	String progressBar = AsyncUtils.generateProgressBarString(-50);
	assertEquals("|____________________|0%", progressBar);
    }

    /**
     * Test generate progress bar string1.
     */
    @Test
    public void testGenerateProgressBarString1() {
	String progressBar = AsyncUtils.generateProgressBarString(-50);
	assertEquals("|____________________|0%", progressBar);
    }
}
