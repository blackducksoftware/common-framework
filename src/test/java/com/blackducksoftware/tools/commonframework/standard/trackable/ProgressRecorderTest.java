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
