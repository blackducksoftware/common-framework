/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.protex.report;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Protex 6 version of the parsing class for the AdHoc SDK Report sections.
 * The class and constructor are public only because the RGT SectionTest instantiates it directly.
 * 
 * Origin:
 * The starting point for this was Rev 4782 of AdHocParser.java (All RGT 6/7 tests passed on this version.)
 * Rev 4414 of AdHocParser didn't work; rev 4782 changes apparently include fixes for Protex 6.
 * So this is rev 4782 of AdHocParser with the conditional logic removed by hand.
 * 
 * @author sbillings
 * @param <T> the generic type
 */
public class AdHocParserProtex6<T extends HocElement> implements IAdHocParser<T> {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(AdHocParserProtex6.class);

	/** The raw columns. */
	
	// parent > child: child elements that descend directly from parent, e.g. div.content > p finds p elements; and body > * finds the direct children of the body tag
	// http://jsoup.org/cookbook/extracting-data/selector-syntax
	private static String SELECTOR_RAW_COLUMNS_REPORTTABLE = "table[class=reportTable] > thead";
	
	/** The raw rows. */
	private static String SELECTOR_RAW_ROWS_REPORTTABLE = "table[class=reportTable] > tbody";

	/** The table header. */
	private static String SELECTOR_TAG_TABLE_HEADER = "th";

	/** The table data. */
	private static String SELECTOR_TAG_TABLE_DATA = "td";

	/** The table row. */
	private static String SELECTOR_TAG_TABLE_ROW = "tr";
	
	private boolean	nonConforming = false;		
	
	private static int MAX_LEGIT_EMPTY_CELLS = 7;

	
	/**
	 * Instantiates a new ad hoc parser.
	 */
	public AdHocParserProtex6() {
	}

	/**
	 * Parses the headers from doc.
	 * 
	 * @param doc the doc
	 * @param targetSectionIndex 
	 * @return the hoc element
	 * @throws Exception the exception
	 */
	public HocElement parseHeadersFromDoc(Document doc, int targetSectionIndex) throws Exception
	{
		try
		{
			HocElement adHocHeader = parseHeaders(doc, targetSectionIndex);	
			adHocHeader.setDoc(doc, SELECTOR_RAW_COLUMNS_REPORTTABLE);
			
			return adHocHeader;
		} catch (Exception e)
		{
			log.error("Error while parsing HTML");
			throw e;
		}
	}

	/**
	 * Parse report section table rows from the Protex report, returning the data in a list of HocElements.
	 * nonConforming = report sections of the form: Label: value.
	 * Normal (conforming) = report sections that contain columns of data, each with a header (label) at the top (first row).
	 * 
	 * @param doc the doc
	 * @param adHocHeader the ad hoc header
	 * @param returnRawHtml the return raw html
	 * @param hocElementClass  the hoc element class
	 * @return the array list
	 * @throws Exception
	 */
	public ArrayList<HocElement> parseRows(Document doc, HocElement adHocHeader, boolean returnRawHtml,  Class<T> hocElementClass, int targetSectionIndex) throws Exception
	{
		try
		{	
			ArrayList<HocElement> rowElements = new ArrayList<HocElement>();
			
			// Use the counter to make every item unique, this is important for when parsing happens on the RGT side as some items can be otherwise identical!
			Integer counter = 0;
	
			Elements allReportSections = doc.select(SELECTOR_RAW_ROWS_REPORTTABLE);
			if(!nonConforming)
			{
				parseConformingSection(adHocHeader, 
									   returnRawHtml,
									   hocElementClass, 
									   targetSectionIndex, 
									   rowElements,
									   counter, 
									   allReportSections);
			}
			else
			{
				parseNonConformingSection(returnRawHtml, 
										  hocElementClass,
										  targetSectionIndex, 
										  rowElements, 
										  counter,
										  allReportSections);
			}

			log.debug("Number of rows parsed: " + rowElements.size());
			return rowElements;
			
		} catch (Exception e)
		{
			log.error("Error parsing rows");
			throw e;
		}
	}

	/**
	 * Parse a "non-conforming" report section.
	 * Non-conforming = The data label for each data value is in the cell to the left of the value.
	 * 
	 * @param returnRawHtml
	 * @param hocElementClass
	 * @param targetSectionIndex
	 * @param rowElements
	 * @param counter
	 * @param allReportSections
	 * @throws Exception
	 */
	private void parseNonConformingSection(boolean returnRawHtml,
										   Class<T> hocElementClass, 
										   int targetSectionIndex,
										   ArrayList<HocElement> rowElements, 
										   Integer counter,
										   Elements allReportSections) throws Exception {
		log.info("Parsing Non-Conforming Section: " + rowElements.size());
		HocElement adHocRow = generateNewInstance(hocElementClass);
		
		adHocRow.setPair("counter", counter.toString());
		Element reportSection = allReportSections.get(targetSectionIndex);
		Elements rows = reportSection.select(SELECTOR_TAG_TABLE_ROW);
		
		for(Element row : rows)
		{
			Elements rowCells = row.select(SELECTOR_TAG_TABLE_DATA);

			int index = 1;
			Element nextElement = null;
			int elementCount = rowCells.size();
			
			for(Element rowCell : rowCells)
			{
		
				if(elementCount > 2)
				{
					rowCell = rowCells.get(1);
					nextElement = rowCells.get(2);
					index++;
				}
				else
				{
					if(index < elementCount){
						nextElement = rowCells.get(index);
					}
					else{
						nextElement = null;
					}
				}
				
				if(nextElement != null)
				{
//					if(returnRawHtml)
//						adHocRow.setPair(rowCell.text(), nextElement.html());
					
					adHocRow.setPair(rowCell.text(), ParserUtils.decode(nextElement.text()));
					log.debug("Set Pair: " + rowCell.text()+ " : " + nextElement.text());
				}			
				index++;
			}
		} // row
		
		counter++;
		rowElements.add(adHocRow);
	}

	/**
	 * Parse a "conforming" report section.
	 * "Conforming" = columns of data with column headers in first row.
	 * @param adHocHeader
	 * @param returnRawHtml
	 * @param hocElementClass
	 * @param targetSectionIndex
	 * @param rowElements
	 * @param counter
	 * @param allReportSections
	 * @throws Exception
	 */
	private void parseConformingSection(HocElement adHocHeader,
										boolean returnRawHtml, 
										Class<T> hocElementClass,
										int targetSectionIndex, 
										ArrayList<HocElement> rowElements,
										Integer counter, 
										Elements allReportSections) throws Exception {
		int adHocHeaderSize =  adHocHeader.getSize();		
		Element reportSection = allReportSections.get(targetSectionIndex);
		Elements rows = reportSection.select(SELECTOR_TAG_TABLE_ROW);
		log.info("Parsing Conforming Section. adHocHeader size = " + adHocHeaderSize + " ; row count = " + rows.size());			
		for(Element row : rows){
			HocElement adHocRow = generateNewInstance(hocElementClass);	
			adHocRow.setPair("counter", counter.toString());
			Elements rowCells = row.select(SELECTOR_TAG_TABLE_DATA);

			int position = 1;
			for(Element rowCell : rowCells) {
				if(adHocHeaderSize > 0){
					String headerKey = adHocHeader.getCoordinate(position);					
					if(returnRawHtml){
						// This grabs the inner HTML (thus stripping out table data blocks) and stuffs it raw.
						adHocRow.setPair(headerKey, ParserUtils.decode(rowCell.html().toString()));
						log.debug("    Set Pair: " + headerKey + " : " + rowCell.html().toString());
					}
					else {
						adHocRow.setPair(headerKey, ParserUtils.decode(rowCell.text()));
						log.debug("Set Pair in position: " + position + " -  " + headerKey+ " : " + rowCell.text());
					}
				}
				position++;
			} // row cell
			
			if(rowContainsValidData(adHocRow, adHocHeader)){
				rowElements.add(adHocRow);
				log.debug("Add row element : " + counter);
			}
			counter++;
		} // row
		log.debug("Parsing Conforming Section. counter = " + counter);
	}

	/**
	 * This checks the integrity of the row and makes sure there is data instead
	 * of blank values. Protex occasionally includes a file in the IdentifiedFiles section with all fields other than
	 * the filename blank. These should be weeded out, and that's what this method does.
	 * 
	 * @param adHocRow  the ad hoc row
	 * @param adHocHeader the ad hoc header
	 * @return true, if successful
	 */
	private boolean rowContainsValidData(HocElement adHocRow, HocElement adHocHeader) 
	{
		Collection<String> headerValues = adHocHeader.getInternalValues();
		Iterator<String> it = headerValues.iterator();
		int emptyCellCount = 0;
		
		while(it.hasNext())
		{
			String key = it.next();
			String value = adHocRow.getValue(key);
			if(value == null || value.length() == 0)
			{
				emptyCellCount++;
			}
		}

		if(emptyCellCount > MAX_LEGIT_EMPTY_CELLS) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Parses the header row of the HTML document. If a header is missing then a
	 * fatal exception is generated.
	 * 
	 * @param doc  the doc
	 * @param targetSectionIndex
	 * @return the ad hoc element
	 * @throws Exception the exception
	 */
	private AdHocElement parseHeaders(Document doc, int targetSectionIndex) throws Exception 
	{
		AdHocElement adHocHeader = new AdHocElement();
		
		try
		{
			/*
			 * Protex 6 reports:
			 * 	All sections:
			 * 		Headers are found in a table with class=reportTable.
			 * 
			 * Protex 7 reports:
			 * 	Summary section (perhaps all non-conforming sections):
			 *		Headers are found in a table with class=labeledTable
			 *	Other sections:
			 *		Headers are found in a table with class=reportTable.
			 *
			 * Try reportTable first. If the section header isn't found, then try labeledTable
			 * Keep track of which we're currently parsing.
			 */			
			log.debug("Selector RawColums ReportTable = " + SELECTOR_RAW_COLUMNS_REPORTTABLE);
			Elements reportSections = doc.body().select(SELECTOR_RAW_COLUMNS_REPORTTABLE);		
			
			if(reportSections.size() == 0){
				log.error("Error parsing headers");
				throw new Exception();
			}
			
			Element reportSection = reportSections.get(targetSectionIndex);
			Elements verticalColumns = reportSection.select(SELECTOR_TAG_TABLE_HEADER);

			// Start with position 0 as it is a bogus header
			// When we do the matching of the rows, we will start at 1.
			int position = 0;
			for(Element vColumn : verticalColumns)
			{
				adHocHeader.setCoordinate(position, vColumn.text());
				log.debug("Header column: " + vColumn.text());
				position++;	
			}
			
			log.debug("Number of headers parsed: " + adHocHeader.getSize());
			if(adHocHeader.getSize() <= 1)
			{
				nonConforming = true; // Data labels are to the left of the values, rather than at the top of columns
			}
			
		} catch (Exception e)
		{
			log.error("Error parsing headers");
			throw e;
		}		
		return adHocHeader;
	}

	/**
	 * We want to wrap the instantiation of the class carefully to avoid
	 * uncaught RunTime exceptions in case the wrong class is being shoved into here.
	 *
	 * @param hocElementClass the hoc element class
	 * @return the hoc element
	 * @throws Exception the exception
	 */
	private HocElement generateNewInstance(Class<T> hocElementClass) throws Exception
	{
		HocElement adHocRow = null;
		Constructor<?> constructor = null;;
		try {
			constructor = hocElementClass.getConstructor();
		} catch (SecurityException e) {
			throw new Exception(e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new Exception(e.getMessage());
		}

		try {
			adHocRow = (HocElement) constructor.newInstance();
		} catch (IllegalArgumentException e) {
			throw new Exception(e.getMessage());
		} catch (InstantiationException e) {
			throw new Exception(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new Exception(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new Exception(e.getMessage());
		}
		
		return adHocRow;
	}	
}

