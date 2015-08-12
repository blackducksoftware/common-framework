/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.protex.report;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

public interface IAdHocParser<T extends HocElement> {

	/**
	 * Parses the headers from doc.
	 * 
	 * @param doc
	 *            the doc
	 * @return the hoc element
	 * @throws Exception
	 *             the exception
	 */
	public abstract HocElement parseHeadersFromDoc(Document doc,
			int targetSectionIndex) throws Exception;

	/**
	 * Parse report section table rows from the Protex report, returning the data in a list of HocElements.
	 * nonConforming = report sections of the form: Label: value.
	 * Normal (conforming) = report sections that contain columns of data, each with a header (label) at the top (first row).
	 * 
	 * @param doc
	 *            the doc
	 * @param adHocHeader
	 *            the ad hoc header
	 * @param returnRawHtml
	 *            the return raw html
	 * @param hocElementClass
	 *            the hoc element class
	 * @return the array list
	 * @throws Exception
	 */
	public abstract ArrayList<HocElement> parseRows(Document doc,
			HocElement adHocHeader, boolean returnRawHtml,
			Class<T> hocElementClass, int targetSectionIndex) throws Exception;

}