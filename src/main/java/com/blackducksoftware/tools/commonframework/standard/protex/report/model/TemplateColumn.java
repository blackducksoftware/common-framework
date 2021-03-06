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

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Columns representing the excel sheet.
 * 
 * @author akamen
 *
 */
public class TemplateColumn {

    /** The column name. */
    private String columnName;

    /** The column pos. */
    private Integer columnPos;

    /** The cell style. */
    private CellStyle cellStyle;

    private String cellFormula;

    // This is an optional value that is set by the ConfigManager via poperty
    // file mappings
    // Represents the method to be used for the POJO via reflection
    /** The lookup mapping name. */
    private String lookupMappingName;

    /** The is column url. */
    private boolean isColumnURL = false;

    /**
     * This denotes what kind of cell this is. Numbe, string, etc? Useful for
     * formatting.
     */
    private Integer cellType;

    /**
     * Gets the column name.
     *
     * @return the column name
     */
    public String getColumnName() {
	return columnName;
    }

    /**
     * Sets the column name.
     *
     * @param columnName
     *            the new column name
     */
    public void setColumnName(String columnName) {
	this.columnName = columnName;
    }

    /**
     * Gets the column pos.
     *
     * @return the column pos
     */
    public Integer getColumnPos() {
	return columnPos;
    }

    /**
     * Sets the column pos.
     *
     * @param columnPos
     *            the new column pos
     */
    public void setColumnPos(Integer columnPos) {
	this.columnPos = columnPos;
    }

    /**
     * Gets the cell style.
     *
     * @return the cell style
     */
    public CellStyle getCellStyle() {
	return cellStyle;
    }

    /**
     * Sets the cell style.
     *
     * @param cellStyle
     *            the new cell style
     */
    public void setCellStyle(CellStyle cellStyle) {
	this.cellStyle = cellStyle;
    }

    /**
     * Checks if is column url.
     *
     * @return true, if is column url
     */
    public boolean isColumnURL() {
	return isColumnURL;
    }

    /**
     * Sets the column url.
     *
     * @param isColumnURL
     *            the new column url
     */
    public void setColumnURL(boolean isColumnURL) {
	this.isColumnURL = isColumnURL;
    }

    /**
     * Gets the lookup mapping name.
     *
     * @return the lookup mapping name
     */
    public String getLookupMappingName() {
	return lookupMappingName;
    }

    /**
     * Sets the lookup mapping name.
     *
     * @param lookupMappingName
     *            the new lookup mapping name
     */
    public void setLookupMappingName(String lookupMappingName) {
	this.lookupMappingName = lookupMappingName;
    }

    public Integer getCellType() {
	return cellType;
    }

    public void setCellType(Integer cellType) {
	this.cellType = cellType;
    }

    public String getCellFormula() {
	return cellFormula;
    }

    public void setCellFormula(String cellFormula) {
	this.cellFormula = cellFormula;
    }

}
