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
package com.blackducksoftware.tools.commonframework.core.config.testbeans;

import java.io.InputStream;
import java.util.Properties;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.user.CommonUser;

// TODO: Auto-generated Javadoc
/**
 * Extended test config class for testing purposes only. Since we cannot test an
 * abstract class, we will test its inherited class.
 * 
 * This tests the Protex Config Manager only
 * 
 * @author akamen
 *
 */
public class TestProtexConfigurationManager extends ConfigurationManager {

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param configFileLocation
     *            the config file location
     */
    public TestProtexConfigurationManager(String configFileLocation) {
	super(configFileLocation, APPLICATION.PROTEX);
    }

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param user
     *            the user
     */
    public TestProtexConfigurationManager(CommonUser user) {
	super(user, APPLICATION.PROTEX);
    }

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param is
     *            the is
     */
    public TestProtexConfigurationManager(InputStream is) {
	super(is, APPLICATION.PROTEX);
    }

    /**
     * Instantiates a new test protex configuration manager.
     *
     * @param props
     *            the props
     */
    public TestProtexConfigurationManager(Properties props) {
	super(props, APPLICATION.PROTEX);
    }
}
