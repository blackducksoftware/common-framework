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
/**
 *
 */
package com.blackducksoftware.tools.commonframework.standard.protex.report.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The Class ReportRowDataBlock.
 *
 * @author Ari Kamen
 *
 *         Abstract ReportGenerator object to hold an entire row of data
 */
public class ReportRowDataBlock extends HashMap<String, String> implements
	Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4325280841768786267L;

    /** The row number. */
    private Integer rowNumber;

    /** The access map. */
    private final HashMap<String, Boolean> accessMap;

    /**
     * Instantiates a new report row data block.
     *
     * @param row
     *            the row
     */
    public ReportRowDataBlock(Integer row) {
	super();
	rowNumber = new Integer(row);
	accessMap = new HashMap<String, Boolean>();
    }

    /**
     * Adds the data point.
     *
     * @param dirtyKey
     *            the dirty key
     * @param value
     *            the value
     */
    public void addDataPoint(String dirtyKey, String value) {
	super.put(dirtyKey, value);
	accessMap.put(dirtyKey, false);
    }

    /**
     * Sets the row.
     *
     * @param row
     *            the new row
     */
    public void setRow(Integer row) {
	rowNumber = row;
    }

    /**
     * Gets the data point.
     *
     * @param dirtyKey
     *            the dirty key
     * @return the data point
     */
    public String getDataPoint(String dirtyKey) {
	String value = super.get(dirtyKey);
	accessMap.put(dirtyKey, true);

	return value;
    }

    /**
     * Gets the row number.
     *
     * @return the row number
     */
    public Integer getRowNumber() {
	return rowNumber;
    }

    /**
     * Gets the unused data points.
     *
     * @return the unused data points
     */
    public String getUnusedDataPoints() {
	StringBuffer buffer = new StringBuffer();
	Iterator<String> it = accessMap.keySet().iterator();
	while (it.hasNext()) {
	    String key = it.next();
	    Boolean bool = accessMap.get(key);
	    if (!bool) {
		buffer.append("Unused key: " + key + " value: "
			+ super.get(key) + "\n");
	    }
	}

	return buffer.toString();
    }

    @Override
    public String toString() {
	return super.toString();
    }
}
