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

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.datatable.DataTable;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldType;
import com.blackducksoftware.tools.commonframework.standard.datatable.Record;
import com.blackducksoftware.tools.commonframework.standard.datatable.RecordDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.writer.DataSetWriterExcel;

public class DataTableWriterExcelManual {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testMultiSheet() throws Exception {
	RecordDef recordDef = createSimpleRecordDef();
	DataTable dataSet = new DataTable(recordDef);

	for (int i = 0; i < DataSetWriterExcel.EXCEL_MAX_ROWS; i++) {
	    Record record = new Record(recordDef);
	    for (FieldDef fieldDef : recordDef) {
		record.setFieldValue(fieldDef.getName(), fieldDef.getName()
			+ " test value " + i);
	    }
	    dataSet.add(record);
	}

	DataSetWriterExcel writer = new DataSetWriterExcel(); // Pass a filename
							      // if you want an
							      // output file
	writer.write(dataSet);
	Workbook wb = writer.getWorkbook();
	assertEquals(2, wb.getNumberOfSheets());

	// Second sheet
	Sheet sheet = wb.getSheetAt(1);
	assertEquals(2, sheet.getLastRowNum());

	// Last row
	Row row = sheet.getRow(2);
	assertEquals("applicationVersion test value 1048575", row.getCell(1)
		.getStringCellValue());
    }

    private RecordDef createSimpleRecordDef() {
	List<FieldDef> fields = new ArrayList<FieldDef>();
	fields.add(new FieldDef("applicationName", FieldType.STRING,
		"Application Name"));
	fields.add(new FieldDef("applicationVersion", FieldType.STRING,
		"Application Version"));
	RecordDef recordDef = new RecordDef(fields);
	return recordDef;
    }

}
