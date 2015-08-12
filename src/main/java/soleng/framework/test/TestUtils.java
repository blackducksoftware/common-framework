/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestUtils {
	private static int tempFileIndex=0;
	
	/**
	 * Get a temp report filename (path).
	 * @return
	 * @throws IOException
	 */
	public static String getTempReportFilePath() throws IOException {
		File tempFile = File.createTempFile("unitTest" + ++tempFileIndex, ".xlsx");
		tempFile.deleteOnExit();
		return tempFile.getAbsolutePath();
	}

	/**
	 * Compare two reports, optionally forgiving some diffs (like dates, etc.).
	 * @param expectedReportFilename
	 * @param actualReportFilename
	 * @param firstDataRowOnly
	 * @param beFlexible
	 * @throws Exception
	 */
	public static void checkReport(String expectedReportFilename, String actualReportFilename,
			boolean firstDataRowOnly, boolean beFlexible) throws Exception {
		
//		System.out.println("Expected: " + expectedReportFilename + "; Actual: " + actualReportFilename);
		Workbook expectedWorkbook = WorkbookFactory.create(new File(expectedReportFilename));
		Workbook actualWorkbook = WorkbookFactory.create(new File(actualReportFilename));

		for (int sheetIndex=0; sheetIndex < expectedWorkbook.getNumberOfSheets(); sheetIndex++) {
			Sheet expectedSheet = expectedWorkbook.getSheetAt(sheetIndex);
			String sheetName = expectedSheet.getSheetName();
			Sheet actualSheet = actualWorkbook.getSheet(sheetName);
			
			assertNotNull(actualSheet);
//			System.out.println("Checking sheet " + sheetName);
			
			Iterator<Row> expectedRowIter = expectedSheet.iterator();
			Iterator<Row> actualRowIter = actualSheet.iterator();
			int rowIndex = 0;
			while (expectedRowIter.hasNext()) {
				
				if (firstDataRowOnly) {
					if (rowIndex > 1) {
						break; // Just check the first data row
					}
				}
				Row expectedRow = expectedRowIter.next();
				Row actualRow = actualRowIter.next();
				
				Iterator<Cell> expectedCellIter = expectedRow.iterator();
				Iterator<Cell> actualCellIter = actualRow.iterator();
				
				int colIndex = 0;
				while (expectedCellIter.hasNext()) {
					compareCells(expectedCellIter, actualCellIter, colIndex++, beFlexible);
				}
				while (actualCellIter.hasNext()) {
					Cell actualCell = actualCellIter.next();
					String actualValue = getCellValueString(actualCell);
					if (actualValue.length() > 0) {
//						System.out.println("Found extra value: " + actualValue);
						fail("Actual report row has more values than expected report row");
					}
				}
				rowIndex++;
			}
		}
	}
	
	private static void compareCells(Iterator<Cell> expectedCellIter, Iterator<Cell> actualCellIter, int colIndex, boolean beFlexible) {
		int colLetterInt = (int)('A') + colIndex;
		char[] colLetterChars = Character.toChars(colLetterInt);
		char colLetter = colLetterChars[0]; // we know it won't be a surrogate pair, so we can assume 1 char
		
		Cell expectedCell = expectedCellIter.next();
		String expectedValue = getCellValueString(expectedCell);

		Cell actualCell=null;
		try {
			actualCell = actualCellIter.next();
		} catch (NoSuchElementException e) {
			if (expectedValue.length() == 0) {
				return; // OK if actual cell isn't there; expected cell is empty 
			} else {
				fail("Missing value");
			}
		}
		
		String actualValue = getCellValueString(actualCell);
		if (! match(expectedValue, actualValue, beFlexible)) {
//			System.out.println("*** Mismatch ***");
			fail("Mismatch");
		}
	}
	
	private static boolean match(String expectedValue, String actualValue, boolean beFlexible) {
//		System.out.println("Comparing [" + actualValue + "] to [" + expectedValue + "]. beFlexible=" + beFlexible);
		
		if (!beFlexible) {
			return actualValue.equals(expectedValue);
		}
		
		if (isDateString(expectedValue)) {
			return true; // let date fields slide
		} else if (isProtexVersion(expectedValue)) {
			return true; // be flexible on Protex versions
		} else if (isMachineName(expectedValue)) {
			return true;
		} else if (isOsName(expectedValue)) {
			return true;
		} else if (isLocaleName(expectedValue)) {
			return true;
		} else {
			return expectedValue.equals(actualValue);
		}
	}
	
	private static boolean isOsName(String s) {
		if (s.contains("Windows")) {
			return true;
		} else if (s.contains("Linux")) {
			return true;
		} else if (s.contains("Unix")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isLocaleName(String s) {
		if (s.startsWith("en-")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isMachineName(String s) {
		if (s.contains(".blackducksoftware.com")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isProtexVersion(String s) {
		if (s.contains("Product version")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isDateString(String s) {
		if (
				(s.contains("January")) ||
				(s.contains("February")) ||
				(s.contains("March")) ||
				(s.contains("April")) ||
				(s.contains("May")) ||
				(s.contains("June")) ||
				(s.contains("July")) ||
				(s.contains("August")) ||
				(s.contains("September")) ||
				(s.contains("October")) ||
				(s.contains("November")) ||
				(s.contains("December"))) {
			return true;
		}
		return false;
	}
	
	private static String getCellValueString(Cell cell) {
		String cellValueString;

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cellValueString = String.valueOf(cell.getNumericCellValue());
		} else {
			cellValueString = cell.getStringCellValue();
		}
		return cellValueString.trim();
	}
}
