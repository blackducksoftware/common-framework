/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.datatable.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.standard.datatable.DataTable;
import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.Record;

public class DataTableReaderExcel implements DataTableReader {
	private static Logger log = LoggerFactory.getLogger(DataTableReaderExcel.class);
	private Workbook workbook=null;
	private InputStream inputStream=null;
	
	public DataTableReaderExcel(String filePath) throws FileNotFoundException {
		inputStream = new FileInputStream(filePath);
	}
	
	public DataTableReaderExcel(InputStream is) {
		inputStream = is;
	}
	
	public void read(DataTable dataTable) throws Exception {
		
		workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowIndex=0;
		for (Row row : sheet) {
			log.debug("\nRow: " + rowIndex++);
			Record rec = new Record(dataTable.getRecordDef());
			int colIndex=0;
			for (FieldDef fieldDef : dataTable.getRecordDef()) {
				log.debug("Col: " + colIndex + ": " + fieldDef.getName() + ": " + fieldDef.getDescription());
				
				Cell cell = row.getCell(colIndex++);
				readCell(rec, fieldDef, cell);
			}
			dataTable.add(rec);
		}
	}
	
	private void readCell(Record rec, FieldDef fieldDef, Cell cell) throws Exception {
		if (cell == null) {
			return;
		}
		switch (fieldDef.getType()) {
		case STRING:
			String cellStringValue = "";
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cellStringValue = String.valueOf(cell.getNumericCellValue());
			} else {
				cellStringValue = cell.getStringCellValue();
			}
			log.debug("String cell; value: " + cellStringValue);
			rec.setFieldValue(fieldDef.getName(), cellStringValue);
			break;
		case DATE:
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_STRING:
				break;
			case Cell.CELL_TYPE_NUMERIC:
				Date cellDateValue = cell.getDateCellValue();
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(cellDateValue);
				rec.setFieldValue(fieldDef.getName(), cal);
				break;
			}
			break;
		case HYPERLINK:
			throw new Exception("DataTableReaderExcel does not yet support HYPERLINK field type");
		default:
		}
	}
}
