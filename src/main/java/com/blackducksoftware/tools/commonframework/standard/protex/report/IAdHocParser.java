/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/

package com.blackducksoftware.tools.commonframework.standard.protex.report;

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
    HocElement parseHeadersFromDoc(Document doc,
	    int targetSectionIndex) throws Exception;

    /**
     * Parse report section table rows from the Protex report, returning the
     * data in a list of HocElements. nonConforming = report sections of the
     * form: Label: value. Normal (conforming) = report sections that contain
     * columns of data, each with a header (label) at the top (first row).
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
    ArrayList<T> parseRows(Document doc,
	    HocElement adHocHeader, boolean returnRawHtml,
	    Class<T> hocElementClass, int targetSectionIndex) throws Exception;

}
