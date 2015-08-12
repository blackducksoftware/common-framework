package soleng.framework.standard.workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * TODO:  Temporary unused class.
 * @author akamen
 *
 */
public class CsvWriter implements WorkbookWriter {
	private String filePath;
	public CsvWriter(String filePath) {
		this.filePath = filePath;
	}

	public void write(Workbook wb) throws IOException {
	
		int numSheets = wb.getNumberOfSheets();
		for (int i=0; i < numSheets; i++) {

			File curOutputFile = getCurrentOutputFile(filePath, numSheets, i);
			
			//CSVWriter pw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(curOutputFile)), 
			//		CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, "\r\n");
	
			try {
				Sheet sheet = wb.getSheetAt(i);
				for (Row row : sheet) {
					List<String> cells = new ArrayList<String>();
					String cellValue = "";
					for (Cell cell : row) {
						int cellType = cell.getCellType();
						switch (cellType) {
						case Cell.CELL_TYPE_BLANK:
							cellValue = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							boolean cellValueBoolean = cell.getBooleanCellValue();
							cellValue = cellValueBoolean ? "true" : "false";
							break;
						case Cell.CELL_TYPE_ERROR:
							cellValue = "<error: " + cell.getErrorCellValue() + ">";
							break;
						case Cell.CELL_TYPE_FORMULA:
							cellValue = cell.getCellFormula();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							double cellValueDouble = cell.getNumericCellValue();
							cellValue = Double.toString(cellValueDouble);
							break;
						case Cell.CELL_TYPE_STRING:
							cellValue = cell.getStringCellValue();
							break;
						default:
							break;
						}
						
						cells.add(cellValue);
					}
					String[] typeExample = new String[cells.size()];
					String[] cellArray = cells.toArray(typeExample);
					//pw.writeNext(cellArray);
				}
			} finally {
				//pw.close();
			}
		}
	}
	
	/**
	 * Derive and return the actual filename for a given report section.
	 * CSV files do not support multiple tabs, so each report section is written to a separate file.
	 * @param origFilePath
	 * @param numToGenerate
	 * @param index
	 * @return
	 */
	public static File getCurrentOutputFile(String origFilePath, int numToGenerate, int index) {
		if (numToGenerate < 2) {
			return new File(origFilePath);
		}
		
		int dotIndex = origFilePath.lastIndexOf('.');
		String basePath = origFilePath.substring(0, dotIndex);
		String ext = origFilePath.substring(dotIndex+1);
		
		return new File(basePath + "_" + index + "." + ext);
	}
}
