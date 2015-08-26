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
package com.blackducksoftware.tools.commonframework.core.config;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationManagerTimeoutTest.
 */
public class ConfigurationManagerTimeoutTest {

    /** The tcm. */
    private static TestProtexConfigurationManager tcm = null;

    /** The test file. */
    private static String testFile = "src/test/resources/test_config_timeout.properties";

    /** The exception. */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Sets the up before class.
     */
    @BeforeClass
    static public void setUpBeforeClass() {

	tcm = new TestProtexConfigurationManager(testFile);
    }

    /**
     * Tests sdk timeout.
     */
    @Test
    public void testTimeout() {
	try {

	    String timeOutString = tcm.getProperty("protex" + "."
		    + ConfigConstants.SDK_TIMEOUT_SUFFIX);
	    Long timeOut = tcm.getSdkTimeOut();

	    Assert.assertEquals("123", timeOutString);
	    Assert.assertEquals(123L, timeOut.longValue());

	} catch (Exception e) {
	    Assert.fail(e.getMessage());
	}
    }

}
