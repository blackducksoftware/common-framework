/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.datatable.reader;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import soleng.framework.standard.datatable.DataTable;
import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.FieldType;
import soleng.framework.standard.datatable.Record;
import soleng.framework.standard.datatable.RecordDef;

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
		
		Calendar janOneCal = new GregorianCalendar(2014, 1, 1);
		Date janOne = janOneCal.getTime();
		
		DataTableReader reader = new DataTableReaderExcel("src/test/resources/simple.xlsx");
		
		List<FieldDef> fields = new ArrayList<FieldDef>();
		
		fields.add(new FieldDef("applicationName", FieldType.STRING, "Application Name"));
		fields.add(new FieldDef("componentName", FieldType.STRING, "Application Name"));
		fields.add(new FieldDef("componentVersion", FieldType.STRING, "Application Name"));
		fields.add(new FieldDef("vulnerabilityName", FieldType.STRING, "Application Name"));
		fields.add(new FieldDef("targetRemediationDate", FieldType.DATE, "Application Name", "MM-dd-yyyy"));
		fields.add(new FieldDef("actualRemediationDate", FieldType.DATE, "Application Name", "MM-dd-yyyy"));
		RecordDef recDef = new RecordDef(fields);
		DataTable table = new DataTable(recDef);
		reader.read(table);
		Iterator<Record> rowIter = table.iterator();
		rowIter.next(); // skip over header row
		Record rec = rowIter.next();
		assertEquals("Junit_DataTableReaderExcelTest", rec.getStringFieldValue("applicationName"));
		assertEquals("SeaMonkey", rec.getStringFieldValue("componentName"));
		assertEquals("1.1", rec.getStringFieldValue("componentVersion"));
		assertEquals("vuln1", rec.getStringFieldValue("vulnerabilityName"));
		assertEquals("12-01-2014", rec.getDateFieldValue("targetRemediationDate"));
		assertEquals(null, rec.getDateFieldValue("actualRemediationDate"));
		
		rec = rowIter.next(); // go to row 3 (second data row)
		assertEquals("Junit_DataTableReaderExcelTest", rec.getStringFieldValue("applicationName"));
		assertEquals("SeaMonkey", rec.getStringFieldValue("componentName"));
		assertEquals("1.1", rec.getStringFieldValue("componentVersion"));
		assertEquals("vuln2", rec.getStringFieldValue("vulnerabilityName"));
		assertEquals("01-01-2014", rec.getDateFieldValue("targetRemediationDate"));
		assertEquals("02-01-2014", rec.getDateFieldValue("actualRemediationDate"));

		rec = rowIter.next(); // go to row 4
		assertEquals("Junit_DataTableReaderExcelTest", rec.getStringFieldValue("applicationName"));
		assertEquals("SeaMonkey", rec.getStringFieldValue("componentName"));
		assertEquals("1.1", rec.getStringFieldValue("componentVersion"));
		assertEquals("vuln3", rec.getStringFieldValue("vulnerabilityName"));
		assertEquals(null, rec.getDateFieldValue("targetRemediationDate"));
		assertEquals(null, rec.getDateFieldValue("actualRemediationDate"));
	}

}
