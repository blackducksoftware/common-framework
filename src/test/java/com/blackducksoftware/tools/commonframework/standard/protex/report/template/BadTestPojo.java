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
package com.blackducksoftware.tools.commonframework.standard.protex.report.template;

import com.blackducksoftware.tools.commonframework.standard.protex.report.model.TemplatePojo;

// TODO: Auto-generated Javadoc
/**
 * Bad pojo that has unmapped methods.
 *
 * @author akamen
 */
public class BadTestPojo implements TemplatePojo {

    /** The un mapped value. */
    private String unMappedValue = "";

    /**
     * Gets the un mapped value.
     *
     * @return the un mapped value
     */
    public String getUnMappedValue() {
	return unMappedValue;
    }

    /**
     * Sets the un mapped value.
     *
     * @param unMappedValue
     *            the new un mapped value
     */
    public void setUnMappedValue(String unMappedValue) {
	this.unMappedValue = unMappedValue;
    }

}
