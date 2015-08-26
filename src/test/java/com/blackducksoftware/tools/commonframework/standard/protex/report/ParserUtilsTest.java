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
package com.blackducksoftware.tools.commonframework.standard.protex.report;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.protex.report.ParserUtils;

public class ParserUtilsTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOneReplacement() {
	String expectedText = "Testing \"";
	String text = "Testing &quot;";

	String actualText = ParserUtils.decode(text);
	assertEquals(expectedText, actualText);
    }

    @Test
    public void testTwoReplacements() {
	String expectedText = "\" Testing \"";
	String text = "&quot; Testing &quot;";

	String actualText = ParserUtils.decode(text);
	assertEquals(expectedText, actualText);
    }

    @Test
    public void testMultipleReplacements() {
	String expectedText = "<p>&nbsp;Hello World\"\"\"</p>";
	String text = "<p>&nbsp;Hello World&quot;&quot;&quot;</p>";

	String actualText = ParserUtils.decode(text);
	assertEquals(expectedText, actualText);
    }

}
