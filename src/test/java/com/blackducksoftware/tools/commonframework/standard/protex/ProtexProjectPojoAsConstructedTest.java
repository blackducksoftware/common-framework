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
package com.blackducksoftware.tools.commonframework.standard.protex;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.protex.ProtexProjectPojo;

// TODO: Auto-generated Javadoc
/**
 * The Class ProtexProjectPojoAsConstructedTest.
 */
public class ProtexProjectPojoAsConstructedTest {

    /** The pojo. */
    private static ProtexProjectPojo pojo;

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	pojo = new ProtexProjectPojo("testprojectkey", "testprojectname");
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
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test get project name.
     */
    @Test
    public void testGetProjectName() {
	assertEquals("testprojectname", pojo.getProjectName());
    }

    /**
     * Test get project key.
     */
    @Test
    public void testGetProjectKey() {
	assertEquals("testprojectkey", pojo.getProjectKey());
    }

    /**
     * Test get date.
     */
    @Test
    public void testGetDate() {
	assertEquals(null, pojo.getDate());
    }

    /**
     * Test get download path.
     */
    @Test
    public void testGetDownloadPath() {
	assertEquals(null, pojo.getDownloadPath());
    }

    /**
     * Test get status.
     */
    @Test
    public void testGetStatus() {
	assertEquals(null, pojo.getStatus());
    }

    /**
     * Test get uploaded mbe.
     */
    @Test
    public void testGetUploadedMBE() {
	assertEquals(null, pojo.getUploadedMBE());
    }

    /**
     * Test get uploaded app a.
     */
    @Test
    public void testGetUploadedAppA() {
	assertEquals(null, pojo.getUploadedAppA());
    }

    /**
     * Test get view path.
     */
    @Test
    public void testGetViewPath() {
	assertEquals(null, pojo.getViewPath());
    }
}
