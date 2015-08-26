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

package com.blackducksoftware.tools.commonframework.standard.common;

import java.util.Map;

import com.blackducksoftware.tools.commonframework.core.exception.CommonFrameworkException;

public interface ProjectOrApp {
    // Clone
    ProjectOrApp clone(String newName) throws CommonFrameworkException;

    /**
     * Clone the app and it's project assoc (if any), and change some app custom
     * attributes in the clone The final two args are ignored if its a Protex
     * project
     *
     * @param newName
     * @param appAttrUpdates
     * @param associatedProjectId
     * @return
     * @throws CommonFrameworkException
     */
    ProjectOrApp clone(String newName, Map<String, String> appAttrUpdates,
	    String associatedProjectId) throws CommonFrameworkException;

    void rename(String newName) throws CommonFrameworkException;

    void lock() throws CommonFrameworkException;

    String getName();

    String getId();

    /**
     * Get the ID of a different project/app, or null if it doesn't exist. For
     * Code Center apps, the version used is the same as this app's version
     *
     * @param name
     * @return
     */
    String getId(String name);
}
