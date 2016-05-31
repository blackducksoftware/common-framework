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

import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.datatable.DataTable;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldType;
import com.blackducksoftware.tools.commonframework.standard.datatable.HyperlinkFieldValue;
import com.blackducksoftware.tools.commonframework.standard.datatable.Record;
import com.blackducksoftware.tools.commonframework.standard.datatable.RecordDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.writer.DataSetWriter;
import com.blackducksoftware.tools.commonframework.standard.datatable.writer.DataSetWriterExcel;
import com.blackducksoftware.tools.commonframework.standard.datatable.writer.DataSetWriterStdOut;

public class DataTableTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void test() throws Exception {

	// Create the RecordDef
	List<FieldDef> fields = new ArrayList<FieldDef>();
	fields.add(new FieldDef("applicationName", FieldType.STRING,
		"Application Name"));
	fields.add(new FieldDef("applicationVersion", FieldType.STRING,
		"Application Version"));
	fields.add(new FieldDef("componentName", FieldType.STRING,
		"Component Name"));
	fields.add(new FieldDef("componentVersion", FieldType.STRING,
		"Component Version"));
	fields.add(new FieldDef("cveName", FieldType.STRING, "CVE Name"));
	fields.add(new FieldDef("vulnerabilityDescription", FieldType.STRING,
		"Vulnerability Description"));
	fields.add(new FieldDef("link", FieldType.HYPERLINK, "Link"));
	fields.add(new FieldDef("publishedDate", FieldType.DATE,
		"Published Date", "yyyy-MM-dd HH:mm:ss.0"));
	RecordDef recordDef = new RecordDef(fields);

	// Create a new/empty DataSet that uses that RecordDef
	DataTable dataSet = new DataTable(recordDef);
	assertEquals(0, dataSet.size());

	// Add a record to the dataset
	Record record = new Record(recordDef);
	for (FieldDef fieldDef : recordDef) {
	    switch (fieldDef.getType()) {
	    case STRING:
		record.setFieldValue(fieldDef.getName(), fieldDef.getName()
			+ " test value");
		break;
	    case HYPERLINK:
		HyperlinkFieldValue hyperlink = new HyperlinkFieldValue(
			"http://www.google.com", "hyperlink display text");
		record.setFieldValue(fieldDef.getName(), hyperlink);
		break;
	    case DATE:
		GregorianCalendar date = new GregorianCalendar(2000, 0, 1, 0, 0);
		record.setFieldValue(fieldDef.getName(), date);
	    default:

	    }
	}

	// Iterator should say no records
	Iterator<Record> dataSetIter = dataSet.iterator();
	assertFalse(dataSetIter.hasNext());

	// Add a record
	assertEquals(0, dataSet.size());
	assertFalse(dataSet.contains(record));
	dataSet.add(record);
	assertEquals(1, dataSet.size());
	assertTrue(dataSet.contains(record));

	Record firstRecord = record; // remember this for later

	// Iterator should get us one record
	dataSetIter = dataSet.iterator();
	assertTrue(dataSetIter.hasNext());
	record = dataSetIter.next();
	assertEquals("applicationName test value",
		record.getStringFieldValue("applicationName"));
	assertEquals("applicationVersion test value",
		record.getStringFieldValue("applicationVersion"));
	assertEquals("componentName test value",
		record.getStringFieldValue("componentName"));
	assertEquals("componentVersion test value",
		record.getStringFieldValue("componentVersion"));
	assertEquals("cveName test value",
		record.getStringFieldValue("cveName"));
	assertEquals("vulnerabilityDescription test value",
		record.getStringFieldValue("vulnerabilityDescription"));
	assertEquals("http://www.google.com",
		record.getHyperlinkFieldValue("link").getHyperlinkText());
	assertEquals("hyperlink display text",
		record.getHyperlinkFieldValue("link").getDisplayText());
	assertEquals("2000-01-01 00:00:00.0",
		record.getDateFieldValue("publishedDate"));
	assertEquals("2000-01-01 00:00:00.0",
		record.getStringFieldValue("publishedDate"));
	assertFalse(dataSetIter.hasNext());

	// Add a second record
	record = new Record(recordDef);
	for (FieldDef fieldDef : recordDef) {
	    switch (fieldDef.getType()) {
	    case STRING:
		record.setFieldValue(fieldDef.getName(), fieldDef.getName()
			+ " test value2");
		break;
	    case HYPERLINK:
		HyperlinkFieldValue hyperlink = new HyperlinkFieldValue(
			"http://www.blackducksoftware.com",
			"hyperlink display text2");
		record.setFieldValue(fieldDef.getName(), hyperlink);
		break;
	    case DATE:
		GregorianCalendar date = new GregorianCalendar(2001, 0, 1, 0, 0);
		record.setFieldValue(fieldDef.getName(), date);
	    default:

	    }
	}
	dataSet.add(record);
	Record secondRecord = record; // remember this for later
	assertEquals(2, dataSet.size());

	// Iterator should get us two records
	dataSetIter = dataSet.iterator();
	assertTrue(dataSetIter.hasNext());
	record = dataSetIter.next();
	assertEquals("applicationName test value",
		record.getStringFieldValue("applicationName"));
	assertEquals("applicationVersion test value",
		record.getStringFieldValue("applicationVersion"));
	assertEquals("componentName test value",
		record.getStringFieldValue("componentName"));
	assertEquals("componentVersion test value",
		record.getStringFieldValue("componentVersion"));
	assertEquals("cveName test value",
		record.getStringFieldValue("cveName"));
	assertEquals("vulnerabilityDescription test value",
		record.getStringFieldValue("vulnerabilityDescription"));
	assertEquals("http://www.google.com",
		record.getHyperlinkFieldValue("link").getHyperlinkText());
	assertEquals("hyperlink display text",
		record.getHyperlinkFieldValue("link").getDisplayText());
	assertEquals("2000-01-01 00:00:00.0",
		record.getDateFieldValue("publishedDate"));

	// Test the "asDate" method
	GregorianCalendar expectedCal = new GregorianCalendar(2000, 0, 1, 0, 0);
	Date expectedDate = expectedCal.getTime();
	assertEquals(expectedDate,
		record.getDateFieldValueAsDate("publishedDate"));

	assertTrue(dataSetIter.hasNext());
	record = dataSetIter.next();
	assertEquals("applicationName test value2",
		record.getStringFieldValue("applicationName"));
	assertEquals("applicationVersion test value2",
		record.getStringFieldValue("applicationVersion"));
	assertEquals("componentName test value2",
		record.getStringFieldValue("componentName"));
	assertEquals("componentVersion test value2",
		record.getStringFieldValue("componentVersion"));
	assertEquals("cveName test value2",
		record.getStringFieldValue("cveName"));
	assertEquals("vulnerabilityDescription test value2",
		record.getStringFieldValue("vulnerabilityDescription"));
	assertEquals("http://www.blackducksoftware.com", record
		.getHyperlinkFieldValue("link").getHyperlinkText());
	assertEquals("hyperlink display text2",
		record.getHyperlinkFieldValue("link").getDisplayText());
	assertEquals("2001-01-01 00:00:00.0",
		record.getDateFieldValue("publishedDate"));
	assertEquals("2001-01-01 00:00:00.0",
		record.getStringFieldValue("publishedDate"));

	assertFalse(dataSetIter.hasNext());

	// Delete, then re-add the first record
	assertEquals(2, dataSet.size());
	assertTrue(dataSet.contains(firstRecord));
	assertTrue(dataSet.contains(secondRecord));
	dataSet.remove(firstRecord);
	assertEquals(1, dataSet.size());
	assertFalse(dataSet.contains(firstRecord));
	assertTrue(dataSet.contains(secondRecord));
	dataSet.add(firstRecord);
	assertEquals(2, dataSet.size());
	assertTrue(dataSet.contains(firstRecord));
	assertTrue(dataSet.contains(secondRecord));

	DataSetWriter writer = new DataSetWriterStdOut();
	writer.write(dataSet);

	writer = new DataSetWriterExcel("test_output1.xlsx");
	writer.write(dataSet);

	// Purge the dataset
	dataSet.clear();
	assertEquals(0, dataSet.size());
    }

    @Test
    public void testTruncation() throws Exception {

	// Create the RecordDef
	List<FieldDef> fields = new ArrayList<FieldDef>();
	fields.add(new FieldDef("sealId", FieldType.STRING, "SEAL ID", 5)); // truncate
									    // values
									    // to
									    // 5
									    // chars

	RecordDef recordDef = new RecordDef(fields);

	// Create a new/empty DataSet that uses that RecordDef
	DataTable dataSet = new DataTable(recordDef);
	assertEquals(0, dataSet.size());

	// Add a record to the dataset
	Record record = new Record(recordDef);
	record.setFieldValue("sealId", "a              b"); // "b" should get
							    // truncated off;
							    // spaces should get
							    // trimmed
	dataSet.add(record);
	assertEquals(1, dataSet.size());

	// Iterator should get us one record
	Iterator<Record> dataSetIter = dataSet.iterator();
	assertTrue(dataSetIter.hasNext());
	record = dataSetIter.next();
	assertEquals("a", record.getStringFieldValue("sealId"));

    }

}
