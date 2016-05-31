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
