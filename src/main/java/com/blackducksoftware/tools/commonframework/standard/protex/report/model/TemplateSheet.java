/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
