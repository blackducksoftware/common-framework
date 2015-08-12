package soleng.framework.standard.protex.report.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.core.config.ConfigurationManager;
import soleng.framework.standard.protex.report.model.TemplateColumn;
import soleng.framework.standard.protex.report.model.TemplateSheet;

// TODO: Auto-generated Javadoc
/**
 * Reads a user specified Excel template.
 * <br>
 * <li> Creates internal {@link TemplateSheet } with populated columns. 
 * <li> Can optionally associate {@link ConfigurationManager } and user specified mappings. 
 *
 * @author akamen
 * @see ConfigurationManager#getMappings() ConfigurationManager.getMappings() for more info
 * 
 * <br><br>
 */
public class TemplateReader
{
	
	/** The template book. */
	private static Workbook templateBook;
	
	/** The is lookup required. */
	private boolean isLookupRequired = false;
	
	/** The is processing required. */
	private boolean isProcessingRequired = false;
	
	/** The output location. */
	private File outputLocation = null;
	
	/** The sheet map. */
	private Map<String, TemplateSheet> sheetMap = new HashMap<String, TemplateSheet>();
	
	/** The cm. */
	private ConfigurationManager cm = null;
	
	/** The log. */
	static Logger log = LoggerFactory.getLogger(TemplateReader.class);
	

	/**
	 * Instantiates a new template reader.
	 */
	public TemplateReader() 
	{
	}
	
	/**
	 * Optional constructor.
	 *
	 * @param cm the cm
	 */
	public TemplateReader(ConfigurationManager cm)
	{
		this.cm = cm;
	}
	
	/**
	 * Copies one excel file into another
	 * Populates internal mappings.
	 *
	 * @param templateName the template name
	 * @param outputLocation the output location
	 * @throws Exception the exception
	 */
	public void copyTemplateIntoFile(String templateName, File outputLocation) throws Exception 
	{
		this.outputLocation = outputLocation;
		
		if(outputLocation != null)
		{
			URL srcDir = getClass().getResource("/" + templateName);
			if(srcDir == null)
				throw new IOException(templateName + " does not exist!");
			FileUtils.copyURLToFile(srcDir, outputLocation);
			log.info("Finished copying from template: " + templateName);
		}
	
		String fileName = outputLocation.getName();	
		if(fileName.endsWith("xlsx"))
		{			
			templateBook  = generateWorkBookFromFile(outputLocation);		
		}

		populateTemplateMap();	
	}
	
	/**
	 * Populates the internal maps based on user specified columns
	 * This is used to gain TemplateColumn info from existing sheets (non-templates).
	 *
	 * @param book the book
	 * @param columnNames the column names
	 * @throws Exception the exception
	 */
	public void generateMappingsFromList(XSSFWorkbook book, List<String> columnNames) throws Exception
	{
		for(int i = 0; i < book.getNumberOfSheets(); i++)
		{
			Sheet sheet = book.getSheetAt(i);
			TemplateSheet templateSheet = new TemplateSheet(sheet.getSheetName());
			populateColumns(sheet, templateSheet, columnNames);
			sheetMap.put(sheet.getSheetName(), templateSheet);
			i++;
		}
	}
	

	/**
	 * Populates the template sheets and columns.
	 *
	 * @throws Exception the exception
	 */
	public void populateTemplateMap() throws Exception 
	{		
		for(int i = 0; i < templateBook.getNumberOfSheets(); i++)
		{
			Sheet sheet = templateBook.getSheetAt(i);
			TemplateSheet templateSheet = new TemplateSheet(sheet.getSheetName());
			populateColumns(sheet, templateSheet);
			sheetMap.put(sheet.getSheetName(), templateSheet);
		}
	}

	/**
	 * Optionally checks to see if a configuration is provided and attemps to map 
	 * user specified mappings to the column objects.
	 *
	 * @param columnMap the column map
	 */
	private void populateColumnMappings(Map<String, TemplateColumn> columnMap) 
	{
		if(cm != null)
		{
			Map<String, String> userMappings = cm.getMappings();
			
			Set<String> keys = columnMap.keySet();
			
			for(String key : keys)
			{
				TemplateColumn tc = columnMap.get(key);
				if(tc != null)
				{
					String lookupName = userMappings.get(key);
					tc.setLookupMappingName(lookupName);
					
					log.debug("Set lookup name: " + lookupName + " for column: " + tc.getColumnName());
				}
			}
		}
	}


	/**
	 * Helper method to generate and retrieve a WorkBook from a file.
	 *
	 * @param workBookFile the work book file
	 * @return the workbook
	 * @throws Exception the exception
	 */
	public static Workbook generateWorkBookFromFile(File workBookFile) throws Exception
	{
		FileInputStream fis = null;
		if(workBookFile.exists())
		{
			fis = new FileInputStream(workBookFile);
			try{
				templateBook = WorkbookFactory.create(fis);	
			} catch (Exception e)
			{
				throw new Exception("Unable to read workbook: " + e.getMessage());
			}
			finally
			{
				fis.close();
			}
		}
		else
			throw new Exception("This file does not exist: " + workBookFile);
		
		fis.close();
		
		return templateBook;
	}
	
	/**
	 * Called by the TemplateReader.
	 *
	 * @param sheet the sheet
	 * @param templateSheet the template sheet
	 * @throws Exception the exception
	 */
	private void populateColumns(Sheet sheet, TemplateSheet templateSheet) throws Exception 
	{
		Map<String, TemplateColumn> columnMap = templateSheet.getColumnMap();
		Row headerRow = sheet.getRow(0);
		if(headerRow == null)
			throw new Exception("No header row found! Please create one.");
		
		Row styleRow = sheet.getRow(1);
		if(styleRow == null)
			throw new Exception("Sheet name " + templateSheet.getSheetName() + ": No style row found! Please create one.");
		
		for(int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++)
		{
			TemplateColumn column = new TemplateColumn();
			Cell headerCell = headerRow.getCell(i);
			Cell styleCell = styleRow.getCell(i);
			
			if(headerCell == null)
				throw new Exception("The following column appears to be empty: " + i);

			if(styleCell == null)
				throw new Exception("The following style position is not defined: " + i);
			
			String columnName = headerCell.getStringCellValue();
			// We want to use the style cell (row below) as the header will always be text.
			Integer cellType = styleCell.getCellType();
			
			
			column.setColumnPos(i);
			column.setCellStyle(styleCell.getCellStyle());
			column.setColumnName(columnName);
			column.setCellType(cellType);
			if(cellType == Cell.CELL_TYPE_FORMULA)
				column.setCellFormula(styleCell.getCellFormula());
			
			columnMap.put(columnName, column);
		}	
		
		populateColumnMappings(columnMap);
	}
	
	/**
	 * Populate columns.
	 *
	 * @param sheet the sheet
	 * @param templateSheet the template sheet
	 * @param columnNames the column names
	 * @throws Exception the exception
	 */
	private void populateColumns(Sheet sheet, TemplateSheet templateSheet,
			List<String> columnNames) throws Exception 
	{
		Map<String, TemplateColumn> columnMap = templateSheet.getColumnMap();
		Row headerRow = sheet.getRow(0);
		
		if(headerRow == null)
			throw new Exception("No header row found!");
		
		for(int i = 0; i < headerRow.getPhysicalNumberOfCells(); i ++)
		{
			Cell headerCell = headerRow.getCell(i);
			if(headerCell == null)
				throw new Exception("The following header column is empty: " + i);
			
			String headerName = headerCell.getStringCellValue().trim();
			if(columnNames.contains(headerName))
			{		
				log.info("Processing user specified column: " + headerName);
				TemplateColumn column = new TemplateColumn();
				
				column.setColumnPos(i);
				column.setColumnName(headerName);
				
				columnMap.put(headerName, column);
			}
		}
	}
	
	/**
	 * Gets the sheet map.
	 *
	 * @return the sheet map
	 */
	public Map<String, TemplateSheet> getSheetMap() {
		return sheetMap;
	}

	/**
	 * Retrieve the internal workbook.
	 *
	 * @return the internal work book
	 */
	public Workbook getInternalWorkBook()
	{
		return templateBook;
	}
	
	/**
	 * Checks if is processing required.
	 *
	 * @return true, if is processing required
	 */
	public boolean isProcessingRequired() {
		return isProcessingRequired;
	}

	/**
	 * Sets the processing required.
	 *
	 * @param isProcessingRequired the new processing required
	 */
	public void setProcessingRequired(boolean isProcessingRequired) {
		this.isProcessingRequired = isProcessingRequired;
	}

	/**
	 * Checks if is lookup required.
	 *
	 * @return true, if is lookup required
	 */
	public boolean isLookupRequired() {
		return isLookupRequired;
	}

	/**
	 * Sets the lookup required.
	 *
	 * @param isLookupRequired the new lookup required
	 */
	public void setLookupRequired(boolean isLookupRequired) {
		this.isLookupRequired = isLookupRequired;
	}

	/**
	 * Gets the output location.
	 *
	 * @return the output location
	 */
	public File getOutputLocation() {
		return outputLocation;
	}
}
