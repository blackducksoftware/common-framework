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
