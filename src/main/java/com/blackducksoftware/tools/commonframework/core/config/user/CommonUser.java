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
package com.blackducksoftware.tools.commonframework.core.config.user;

import java.io.Serializable;

/**
 * The Interface CommonUser.
 */
public interface CommonUser extends Serializable {

    /**
     * Sets the server.
     *
     * @param servername
     *            the new server
     */
    void setServer(String servername);

    /**
     * Sets the user name.
     *
     * @param username
     *            the new user name
     */
    void setUserName(String username);

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    void setPassword(String password);

    /**
     * Gets the server.
     *
     * @return the server
     */
    String getServer();

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    String getUserName();

    /**
     * Gets the password.
     *
     * @return the password
     */
    String getPassword();
}
