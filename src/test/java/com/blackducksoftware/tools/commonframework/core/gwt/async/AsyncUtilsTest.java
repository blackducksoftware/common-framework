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
