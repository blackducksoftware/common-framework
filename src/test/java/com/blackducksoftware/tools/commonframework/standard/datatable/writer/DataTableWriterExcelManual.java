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
