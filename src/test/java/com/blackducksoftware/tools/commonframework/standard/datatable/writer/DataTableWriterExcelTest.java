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
package com.blackducksoftware.tools.commonframework.standard.datatable.writer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.datatable.DataTable;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldType;
import com.blackducksoftware.tools.commonframework.standard.datatable.HyperlinkFieldValue;
import com.blackducksoftware.tools.commonframework.standard.datatable.Record;
import com.blackducksoftware.tools.commonframework.standard.datatable.RecordDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.writer.DataSetWriterExcel;

public class DataTableWriterExcelTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void test() throws Exception {
	RecordDef recordDef = createRecordDef();
	DataTable dataSet = new DataTable(recordDef);

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
	    default:
		break;
	    }
	}
	dataSet.add(record);

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
	    default:
		break;
	    }
	}
	dataSet.add(record);

	DataSetWriterExcel writer = new DataSetWriterExcel();
	writer.write(dataSet);
	Workbook wb = writer.getWorkbook();
	assertEquals(2, wb.getSheetAt(0).getLastRowNum());
	assertEquals("hyperlink display text", wb.getSheetAt(0).getRow(1)
		.getCell(6).getStringCellValue());
	assertEquals("hyperlink display text2", wb.getSheetAt(0).getRow(2)
		.getCell(6).getStringCellValue());
	assertEquals("http://www.blackducksoftware.com", wb.getSheetAt(0)
		.getRow(2).getCell(6).getHyperlink().getAddress());
    }

    private RecordDef createRecordDef() {
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
	RecordDef recordDef = new RecordDef(fields);
	return recordDef;
    }

}
