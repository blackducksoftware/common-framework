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
package com.blackducksoftware.tools.commonframework.standard.workbook;

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

import com.opencsv.CSVWriter;

/**
 * Writes a POI workbook to a CSV file.
 *
 * @author sbillings
 *
 */
public class CsvWriter implements WorkbookWriter {
    private String filePath;

    public CsvWriter(String filePath) {
	this.filePath = filePath;
    }

    @Override
    public void write(Workbook wb) throws IOException {

	int numSheets = wb.getNumberOfSheets();
	for (int i = 0; i < numSheets; i++) {

	    File curOutputFile = getCurrentOutputFile(filePath, numSheets, i);

	    CSVWriter pw = new CSVWriter(new OutputStreamWriter(
		    new FileOutputStream(curOutputFile)),
		    CSVWriter.DEFAULT_SEPARATOR,
		    CSVWriter.DEFAULT_QUOTE_CHARACTER, "\r\n");

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
			    boolean cellValueBoolean = cell
				    .getBooleanCellValue();
			    cellValue = cellValueBoolean ? "true" : "false";
			    break;
			case Cell.CELL_TYPE_ERROR:
			    cellValue = "<error: " + cell.getErrorCellValue()
				    + ">";
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
		    pw.writeNext(cellArray);
		}
	    } finally {
		pw.close();
	    }
	}
    }

    /**
     * Derive and return the actual filename for a given report section. CSV
     * files do not support multiple tabs, so each report section is written to
     * a separate file.
     *
     * @param origFilePath
     * @param numToGenerate
     * @param index
     * @return
     */
    public static File getCurrentOutputFile(String origFilePath,
	    int numToGenerate, int index) {
	if (numToGenerate < 2) {
	    return new File(origFilePath);
	}

	int dotIndex = origFilePath.lastIndexOf('.');
	String basePath = origFilePath.substring(0, dotIndex);
	String ext = origFilePath.substring(dotIndex + 1);

	return new File(basePath + "_" + index + "." + ext);
    }
}
