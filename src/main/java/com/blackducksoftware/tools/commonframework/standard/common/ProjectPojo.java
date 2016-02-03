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

/**
 * A generic and simple object that represents a project.
 * 
 * @author akamen
 *
 */
public abstract class ProjectPojo {

    /**
     * Instantiates a new project pojo.
     */
    public ProjectPojo() {

    }

    /**
     * Gets the project name.
     *
     * @return the project name
     */
    public abstract String getProjectName();

    /**
     * Gets the project key.
     *
     * @return the project key
     */
    public abstract String getProjectKey();

    /**
     * When was this project last created/analyzed in the BDS system?
     * 
     * @return Pretty format date MM/dd/yyyy
     */
    public abstract String getAnalyzedDate();
}
