package soleng.framework.standard.datatable.writer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import soleng.framework.standard.datatable.DataTable;
import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.FieldType;
import soleng.framework.standard.datatable.Record;
import soleng.framework.standard.datatable.RecordDef;
import soleng.framework.standard.datatable.writer.DataSetWriterExcel;

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
		
		for (int i=0; i < DataSetWriterExcel.EXCEL_MAX_ROWS; i++) {
			Record record = new Record(recordDef);
			for (FieldDef fieldDef : recordDef) {
				record.setFieldValue(fieldDef.getName(), fieldDef.getName() + " test value " + i);
			}
			dataSet.add(record);
		}
		
		DataSetWriterExcel writer = new DataSetWriterExcel(); // Pass a filename if you want an output file
		writer.write(dataSet);
		Workbook wb = writer.getWorkbook();
		assertEquals(2, wb.getNumberOfSheets());
		
		// Second sheet
		Sheet sheet = wb.getSheetAt(1);
		assertEquals(2, sheet.getLastRowNum());
		
		// Last row
		Row row = sheet.getRow(2);
		assertEquals("applicationVersion test value 1048575", row.getCell(1).getStringCellValue());
	}
	
	private RecordDef createSimpleRecordDef() {
		List<FieldDef> fields = new ArrayList<FieldDef>();
		fields.add(new FieldDef("applicationName", FieldType.STRING, "Application Name"));
		fields.add(new FieldDef("applicationVersion", FieldType.STRING, "Application Version"));
		RecordDef recordDef = new RecordDef(fields);
		return recordDef;
	}

}
