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

import com.blackducksoftware.tools.commonframework.core.config.user.CommonUser;

/**
 * The Class TestCommonUser.
 */
public class TestCommonUser implements CommonUser {

    /** The server. */
    private String server;

    /** The user name. */
    private String userName;

    /** The password. */
    private String password;

    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #getServer()
     */
    public String getServer() {
	return server;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #setServer(java.lang.String)
     */
    public void setServer(String server) {
	this.server = server;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #getUserName()
     */
    public String getUserName() {
	return userName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #setUserName(java.lang.String)
     */
    public void setUserName(String user) {
	this.userName = user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #getPassword()
     */
    public String getPassword() {
	return password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #setPassword(java.lang.String)
     */
    public void setPassword(String password) {
	this.password = password;
    }

}
