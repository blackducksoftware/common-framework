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

/**
 * Test pojo used to test out the reflection mappings defined in the
 * test_config.properties
 * 
 * @author akamen
 *
 */
public class TestPojo implements TemplatePojo {

    /** The value1. */
    private String value1;

    /** The value2. */
    private String value2;

    /** The value4. */
    private String value4;

    /**
     * Gets the value1.
     *
     * @return the value1
     */
    public String getValue1() {
	return value1;
    }

    /**
     * Sets the value1.
     *
     * @param value1
     *            the new value1
     */
    public void setValue1(String value1) {
	this.value1 = value1;
    }

    /**
     * Gets the value2.
     *
     * @return the value2
     */
    public String getValue2() {
	return value2;
    }

    /**
     * Sets the value2.
     *
     * @param value2
     *            the new value2
     */
    public void setValue2(String value2) {
	this.value2 = value2;
    }

    /**
     * Gets the value4.
     *
     * @return the value4
     */
    public String getValue4() {
	return value4;
    }

    /**
     * Sets the value4.
     *
     * @param value4
     *            the new value4
     */
    public void setValue4(String value4) {
	this.value4 = value4;
    }
}
