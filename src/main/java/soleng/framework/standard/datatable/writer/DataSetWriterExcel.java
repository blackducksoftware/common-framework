package soleng.framework.standard.datatable.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;

import soleng.framework.standard.datatable.DataTable;
import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.Record;
import soleng.framework.standard.datatable.RecordDef;

public class DataSetWriterExcel implements DataSetWriter {
	private static Logger log = LoggerFactory.getLogger(DataSetWriterExcel.class);
	public static int EXCEL_MAX_ROWS=1048576; // 1048576 is the max imposed by Excel
	private Workbook workbook=null;
	private OutputStream outputStream=null;
	
	public DataSetWriterExcel(String filePath) throws FileNotFoundException {
		outputStream = new FileOutputStream(filePath);
	}
	
	public DataSetWriterExcel(OutputStream os) {
		outputStream = os;
	}
	
	public DataSetWriterExcel() {
	}
	
	public void write(DataTable dataSet) throws Exception {
		log.info("Generating workbook");
		RecordDef recordDef = dataSet.getRecordDef();

		workbook = new XSSFWorkbook();
	    Sheet sheet=null;
	    int rowIndex=EXCEL_MAX_ROWS; // Force creation of a new sheet (make the "current sheet" look full)

	    for (Record record : dataSet) {
	    	if (rowIndex >= (EXCEL_MAX_ROWS-1)) {
	    		sheet = initNewSheet(recordDef);
	    		rowIndex=1; // account for header that initNewSheet() wrote
	    	}
	    	addDataRow(sheet, recordDef, record, rowIndex++);
		}
		
		if (outputStream != null) {
			log.info("Writing workbook");
			workbook.write(outputStream);
		    outputStream.close();
		}
	}
	
	public Workbook getWorkbook() {
		return workbook;
	}
	
	private Sheet initNewSheet(RecordDef recordDef) {
		Sheet sheet = workbook.createSheet();
		// Write header row
		Row row = sheet.createRow(0);
		int cellIndex=0;
		for (FieldDef fieldDef : recordDef) {
			Cell cell = row.createCell(cellIndex++);
			String cellValue = fieldDef.getDescription();
			cell.setCellValue(cellValue);
		}
		return sheet;
	}
	
	private void addDataRow(Sheet sheet, RecordDef recordDef, Record record, int rowIndex) throws Exception {
		Row row = sheet.createRow(rowIndex++);
		int cellIndex=0;
		for (FieldDef fieldDef : recordDef) {
			Cell cell = row.createCell(cellIndex++);
			switch (fieldDef.getType()) {
			case STRING:
			case DATE:
				populateStringCell(record, fieldDef, cell);
				break;
			case HYPERLINK:
				populateHyperlinkCell(record, fieldDef, cell);
				break;

			}
		}
	}
	
	private void populateStringCell(Record record, FieldDef fieldDef, Cell cell) throws Exception {
		String cellValue = record.getStringFieldValue(fieldDef.getName());
		cell.setCellValue(cellValue);
	}
	
	private void populateHyperlinkCell(Record record, FieldDef fieldDef, Cell cell) throws Exception {
		String cellValue = record.getHyperlinkFieldValue(fieldDef.getName()).getDisplayText();
		cell.setCellValue(cellValue);
		
		//cell style for hyperlinks
	    //by default hyperlinks are blue and underlined
	    CellStyle hlink_style = workbook.createCellStyle();
	    Font hlink_font = workbook.createFont();
	    hlink_font.setUnderline(Font.U_SINGLE);
	    hlink_font.setColor(IndexedColors.BLUE.getIndex());
	    hlink_style.setFont(hlink_font);
		
		// Make it a hyperlink
		CreationHelper createHelper = workbook.getCreationHelper();
		Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
	    link.setAddress(record.getHyperlinkFieldValue(fieldDef.getName()).getHyperlinkText());
	    cell.setHyperlink(link);
	    cell.setCellStyle(hlink_style);
	}
	
		
	

}
