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
package com.blackducksoftware.tools.commonframework.standard.datatable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.datatable.FieldDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldType;
import com.blackducksoftware.tools.commonframework.standard.datatable.RecordDef;

public class RecordDefTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void test() {

	// Create the record definition
	List<FieldDef> fieldDefs = new ArrayList<FieldDef>();
	fieldDefs.add(new FieldDef("applicationName", FieldType.STRING,
		"Application Name"));
	fieldDefs.add(new FieldDef("applicationVersion", FieldType.STRING,
		"Application Version"));
	fieldDefs.add(new FieldDef("componentName", FieldType.STRING,
		"Component Name"));
	fieldDefs.add(new FieldDef("componentVersion", FieldType.STRING,
		"Component Version"));
	fieldDefs.add(new FieldDef("cveName", FieldType.STRING, "CVE Name"));
	fieldDefs.add(new FieldDef("vulnerabilityDescription",
		FieldType.STRING, "Vulnerability Description"));
	fieldDefs.add(new FieldDef("link", FieldType.HYPERLINK, "Link"));
	RecordDef recordDef = new RecordDef(fieldDefs);
	assertEquals(7, recordDef.size());
	assertEquals("applicationName", recordDef.getFieldDef(0).getName());

	// Get an iterator that will let us iterate thru the record's FieldDefs
	Iterator<FieldDef> fieldDefIter = recordDef.iterator();
	assertTrue(fieldDefIter.hasNext());
	assertEquals("applicationName", fieldDefIter.next().getName());
	assertEquals("applicationVersion", fieldDefIter.next().getName());
	assertEquals("componentName", fieldDefIter.next().getName());
	assertEquals("componentVersion", fieldDefIter.next().getName());
	assertEquals("cveName", fieldDefIter.next().getName());
	assertEquals("vulnerabilityDescription", fieldDefIter.next().getName());
	assertTrue(fieldDefIter.hasNext());
	assertEquals("link", fieldDefIter.next().getName());
	assertFalse(fieldDefIter.hasNext());
    }

    @Test
    public void testDateField() {

	// Create the record definition
	List<FieldDef> fieldDefs = new ArrayList<FieldDef>();
	fieldDefs.add(new FieldDef("applicationName", FieldType.STRING,
		"Application Name"));
	fieldDefs.add(new FieldDef("publishedDate", FieldType.DATE,
		"Published Date", "yyyy-MM-dd HH:mm:ss.0"));
	RecordDef recordDef = new RecordDef(fieldDefs);
	assertEquals(2, recordDef.size());
	assertEquals("applicationName", recordDef.getFieldDef(0).getName());
	assertEquals("publishedDate", recordDef.getFieldDef(1).getName());

	// Get an iterator that will let us iterate thru the record's FieldDefs
	Iterator<FieldDef> fieldDefIter = recordDef.iterator();
	assertTrue(fieldDefIter.hasNext());
	assertEquals("applicationName", fieldDefIter.next().getName());
	assertEquals("publishedDate", fieldDefIter.next().getName());
	assertFalse(fieldDefIter.hasNext());
    }

}
