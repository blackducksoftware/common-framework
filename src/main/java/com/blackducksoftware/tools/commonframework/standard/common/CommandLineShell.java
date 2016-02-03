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
package com.blackducksoftware.tools.commonframework.standard.common;

import java.util.List;

import com.blackducksoftware.tools.commonframework.standard.protex.ProtexProjectPojo;

/**
 * The Interface for classes that can launch an interactive command line window
 * that asks the user to select a project from a list.
 */
public interface CommandLineShell {

    /**
     * Launches an interactive command line window Asks the user to select the
     * project and returns the selection.
     * 
     * User
     * {@link com.blackducksoftware.tools.commonframework.protex.ProtexWrapper }
     * to retrieve the list of projects.
     * 
     * @param projectList
     *            the project list
     * @return the project pojo
     * @throws Exception
     *             the exception
     */
    ProtexProjectPojo runProjectSelector(List<ProtexProjectPojo> projectList)
	    throws Exception;

}
