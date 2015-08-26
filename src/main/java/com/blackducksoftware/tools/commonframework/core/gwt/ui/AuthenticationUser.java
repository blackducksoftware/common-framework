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
package com.blackducksoftware.tools.commonframework.core.gwt.ui;

import com.blackducksoftware.tools.commonframework.core.config.user.CommonUser;

/**
 * A user login info object that also contains a flag indicating whether user
 * has been authenticated.
 *
 * @author sbillings
 *
 */
public interface AuthenticationUser extends CommonUser {

    /**
     * Checks if is authenticated.
     *
     * @return true, if is authenticated
     */
    boolean isAuthenticated();

    /**
     * Sets the authenticated.
     *
     * @param authenticated
     *            the new authenticated
     */
    void setAuthenticated(boolean authenticated);

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    String getErrorMessage();

    /**
     * Sets the error message.
     *
     * @param msg
     *            the new error message
     */
    void setErrorMessage(String msg);
}
