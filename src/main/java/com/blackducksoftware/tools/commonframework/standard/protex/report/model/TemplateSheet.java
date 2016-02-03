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
package com.blackducksoftware.tools.commonframework.standard.protex.report.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an Excel sheet.
 *
 * @author akamen
 */
public class TemplateSheet {

    /** The sheet name. */
    private String sheetName;

    /** The column map. */
    private final Map<String, TemplateColumn> columnMap = new HashMap<String, TemplateColumn>();

    /**
     * Instantiates a new template sheet.
     *
     * @param sheetname
     *            the sheetname
     */
    public TemplateSheet(String sheetname) {
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
     * @param sheetName
     *            the new sheet name
     */
    public void setSheetName(String sheetName) {
	this.sheetName = sheetName;
    }

    /**
     * TODO: This does not belong in here! REMOVE IT. Gets the column that
     * matches the user defined column for wrapping Sets the flag to true for
     * that column for future use.
     *
     * @param columnName
     *            the column name
     * @return the column for url wrapping
     * @throws Exception
     *             the exception
     */
    public TemplateColumn getColumnForURLWrapping(String columnName)
	    throws Exception {
	TemplateColumn tColumn = columnMap.get(columnName);
	if (tColumn == null) {
	    throw new Exception(
		    "The column that you specified for URL wrapping does not exist!  Column: "
			    + columnName);
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
