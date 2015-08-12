package soleng.framework.standard.datatable.writer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import soleng.framework.standard.datatable.DataTable;
import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.FieldType;
import soleng.framework.standard.datatable.HyperlinkFieldValue;
import soleng.framework.standard.datatable.Record;
import soleng.framework.standard.datatable.RecordDef;
import soleng.framework.standard.datatable.writer.DataSetWriterExcel;

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
				record.setFieldValue(fieldDef.getName(), fieldDef.getName() + " test value");
				break;
			case HYPERLINK:
				HyperlinkFieldValue hyperlink = new HyperlinkFieldValue("http://www.google.com", "hyperlink display text");
				record.setFieldValue(fieldDef.getName(), hyperlink);
				break;
			}
		}
		dataSet.add(record);
		
		// Add a second record
		record = new Record(recordDef);
		for (FieldDef fieldDef : recordDef) {
			switch (fieldDef.getType()) {
			case STRING:
				record.setFieldValue(fieldDef.getName(), fieldDef.getName() + " test value2");
				break;
			case HYPERLINK:
				HyperlinkFieldValue hyperlink = new HyperlinkFieldValue("http://www.blackducksoftware.com", "hyperlink display text2");
				record.setFieldValue(fieldDef.getName(), hyperlink);
				break;
			}
		}
		dataSet.add(record);

		DataSetWriterExcel writer = new DataSetWriterExcel();
		writer.write(dataSet);
		Workbook wb = writer.getWorkbook();
		assertEquals(2, wb.getSheetAt(0).getLastRowNum());
		assertEquals("hyperlink display text", wb.getSheetAt(0).getRow(1).getCell(6).getStringCellValue());
		assertEquals("hyperlink display text2", wb.getSheetAt(0).getRow(2).getCell(6).getStringCellValue());
		assertEquals("http://www.blackducksoftware.com", wb.getSheetAt(0).getRow(2).getCell(6).getHyperlink().getAddress());
	}
	
	
	
	private RecordDef createRecordDef() {
		List<FieldDef> fields = new ArrayList<FieldDef>();
		fields.add(new FieldDef("applicationName", FieldType.STRING, "Application Name"));
		fields.add(new FieldDef("applicationVersion", FieldType.STRING, "Application Version"));
		fields.add(new FieldDef("componentName", FieldType.STRING, "Component Name"));
		fields.add(new FieldDef("componentVersion", FieldType.STRING, "Component Version"));
		fields.add(new FieldDef("cveName", FieldType.STRING, "CVE Name"));
		fields.add(new FieldDef("vulnerabilityDescription", FieldType.STRING, "Vulnerability Description"));
		fields.add(new FieldDef("link", FieldType.HYPERLINK, "Link"));
		RecordDef recordDef = new RecordDef(fields);
		return recordDef;
	}
	
	

}
