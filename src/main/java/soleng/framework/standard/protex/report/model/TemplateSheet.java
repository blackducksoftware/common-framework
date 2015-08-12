package soleng.framework.standard.protex.report.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

// TODO: Auto-generated Javadoc
/**
 * Represents an Excel sheet.
 *
 * @author akamen
 */
public class TemplateSheet {

	/** The sheet name. */
	private String sheetName;
	
	/** The column map. */
	private Map<String, TemplateColumn> columnMap = new HashMap<String, TemplateColumn>();
	
	/**
	 * Instantiates a new template sheet.
	 *
	 * @param sheetname the sheetname
	 */
	public TemplateSheet(String sheetname)
	{
		sheetName = sheetname;
	}
	
	/**
	 * Gets the sheet name.
	 *
	 * @return the sheet name
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * Sets the sheet name.
	 *
	 * @param sheetName the new sheet name
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	
	/**
	 * TODO: This does not belong in here! REMOVE IT.
	 * Gets the column that matches the user defined column for wrapping
	 * Sets the flag to true for that column for future use.
	 *
	 * @param columnName the column name
	 * @return the column for url wrapping
	 * @throws Exception the exception
	 */
	public TemplateColumn getColumnForURLWrapping(String columnName) throws Exception
	{
		TemplateColumn tColumn = columnMap.get(columnName);
		if(tColumn == null)
		{
			throw new Exception("The column that you specified for URL wrapping does not exist!  Column: " + columnName);
		}
		
		tColumn.setColumnURL(true);

		return tColumn;
	}
	
	

	/**
	 * Gets the column map.
	 *
	 * @return the column map
	 */
	public Map<String, TemplateColumn> getColumnMap() {
		return columnMap;
	}

	
}
