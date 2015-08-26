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

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationManagerPropsTest.
 */
public class ConfigurationManagerPropsTest {

    /** The tcm. */
    private static TestProtexConfigurationManager tcm = null;

    /** The exception. */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    static public void setUpBeforeClass() throws Exception {
	Properties props = new Properties();
	props.setProperty("protex.server.name", "myserver");
	props.setProperty("protex.user.name", "userName");
	props.setProperty("protex.password", "blackDuck");

	tcm = new TestProtexConfigurationManager(props);
    }

    /**
     * Tests configuration manager when an input stream is provided.
     */
    @Test
    public void testInitializerWithGoodFileAndGoodParams() {
	try {
	    String server = tcm
		    .getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
	    String user = tcm
		    .getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
	    String password = tcm
		    .getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);

	    Assert.assertEquals("myserver", server);
	    Assert.assertEquals("userName", user);
	    Assert.assertEquals("blackDuck", password);

	} catch (Exception e) {
	    Assert.fail(e.getMessage());
	}
    }

    /**
     * Tests getProps() method.
     */
    @Test
    public void testGetProps() {
	try {
	    Properties props = tcm.getProps();

	    String server = props
		    .getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
	    String user = props
		    .getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
	    String password = props
		    .getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);

	    Assert.assertEquals("myserver", server);
	    Assert.assertEquals("userName", user);
	    Assert.assertEquals("blackDuck", password);

	} catch (Exception e) {
	    Assert.fail(e.getMessage());
	}
    }
}
