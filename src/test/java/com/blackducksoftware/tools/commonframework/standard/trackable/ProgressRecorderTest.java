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
package com.blackducksoftware.tools.commonframework.standard.trackable;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder.LoggingProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgressRecorderTest.
 */
public class ProgressRecorderTest {

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
     * Test.
     */
    @Test
    public void test() {
	LoggingProgressRecorder recorder = new LoggingProgressRecorder();
	assertEquals(0, recorder.getPercentComplete());

	recorder.setTotalToComplete(20);
	recorder.setGranularity(5);

	recorder.incrementCompletedSoFar(1);
	assertEquals(5, recorder.getPercentComplete());

	recorder.setCompletedSoFar(10);
	assertEquals(50, recorder.getPercentComplete());

	recorder.incrementCompletedSoFar(5);
	assertEquals(75, recorder.getPercentComplete());
    }

}
