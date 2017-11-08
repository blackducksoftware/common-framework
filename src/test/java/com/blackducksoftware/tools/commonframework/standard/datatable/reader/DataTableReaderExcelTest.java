/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.tools.commonframework.standard.datatable.reader;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.datatable.DataTable;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldType;
import com.blackducksoftware.tools.commonframework.standard.datatable.Record;
import com.blackducksoftware.tools.commonframework.standard.datatable.RecordDef;

public class DataTableReaderExcelTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {

	

	DataTableReader reader = new DataTableReaderExcel(
		"src/test/resources/simple.xlsx");

	List<FieldDef> fields = new ArrayList<FieldDef>();

	fields.add(new FieldDef("applicationName", FieldType.STRING,
		"Application Name"));
	fields.add(new FieldDef("componentName", FieldType.STRING,
		"Application Name"));
	fields.add(new FieldDef("componentVersion", FieldType.STRING,
		"Application Name"));
	fields.add(new FieldDef("vulnerabilityName", FieldType.STRING,
		"Application Name"));
	fields.add(new FieldDef("targetRemediationDate", FieldType.DATE,
		"Application Name", "MM-dd-yyyy"));
	fields.add(new FieldDef("actualRemediationDate", FieldType.DATE,
		"Application Name", "MM-dd-yyyy"));
	RecordDef recDef = new RecordDef(fields);
	DataTable table = new DataTable(recDef);
	reader.read(table);
	Iterator<Record> rowIter = table.iterator();
	rowIter.next(); // skip over header row
	Record rec = rowIter.next();
	assertEquals("Junit_DataTableReaderExcelTest",
		rec.getStringFieldValue("applicationName"));
	assertEquals("SeaMonkey", rec.getStringFieldValue("componentName"));
	assertEquals("1.1", rec.getStringFieldValue("componentVersion"));
	assertEquals("vuln1", rec.getStringFieldValue("vulnerabilityName"));
	assertEquals("12-01-2014",
		rec.getDateFieldValue("targetRemediationDate"));
	assertEquals(null, rec.getDateFieldValue("actualRemediationDate"));

	rec = rowIter.next(); // go to row 3 (second data row)
	assertEquals("Junit_DataTableReaderExcelTest",
		rec.getStringFieldValue("applicationName"));
	assertEquals("SeaMonkey", rec.getStringFieldValue("componentName"));
	assertEquals("1.1", rec.getStringFieldValue("componentVersion"));
	assertEquals("vuln2", rec.getStringFieldValue("vulnerabilityName"));
	assertEquals("01-01-2014",
		rec.getDateFieldValue("targetRemediationDate"));
	assertEquals("02-01-2014",
		rec.getDateFieldValue("actualRemediationDate"));

	rec = rowIter.next(); // go to row 4
	assertEquals("Junit_DataTableReaderExcelTest",
		rec.getStringFieldValue("applicationName"));
	assertEquals("SeaMonkey", rec.getStringFieldValue("componentName"));
	assertEquals("1.1", rec.getStringFieldValue("componentVersion"));
	assertEquals("vuln3", rec.getStringFieldValue("vulnerabilityName"));
	assertEquals(null, rec.getDateFieldValue("targetRemediationDate"));
	assertEquals(null, rec.getDateFieldValue("actualRemediationDate"));
    }

}
