/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
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
