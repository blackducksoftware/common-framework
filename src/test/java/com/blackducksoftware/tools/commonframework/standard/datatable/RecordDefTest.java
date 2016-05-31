/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 *  under the License.
 *
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
