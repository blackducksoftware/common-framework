/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.exception;

import java.io.Serializable;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;

/**
 * Exception thrown by CommonFramework classes.
 *
 * @author sbillings
 *
 */
public class CommonFrameworkException extends Exception implements Serializable {

    private static final long serialVersionUID = -638853136815473675L;

    private ConfigurationManager cf;

    public CommonFrameworkException(String message) {
	super(message);
    }

    public CommonFrameworkException(ConfigurationManager cf, String message) {
	super(message);
	this.cf = cf;
    }

    /**
     * Get a string containing the server and username, if the thrower provided
     * it.
     *
     * @return
     */
    public String getConfigurationInformation() {
	if (cf == null) {
	    return "<server and user information not available>";
	}
	StringBuilder sb = new StringBuilder();

	ServerBean server = cf.getServerBean();
	sb.append("Server: " + server.getServerName());
	sb.append("\n");
	sb.append("User: " + server.getUserName());

	return sb.toString();
    }
}
