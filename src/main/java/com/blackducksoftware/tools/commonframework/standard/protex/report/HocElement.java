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
package com.blackducksoftware.tools.commonframework.standard.protex.report;

import java.util.Collection;

import org.jsoup.nodes.Document;

/**
 * Generic representation of an element derived from parsing the AdHoc Report.
 *
 * Modified on March 4th, 2015: Added counter.
 *
 * @author akamen
 */
public interface HocElement {

    /**
     * Gets the size.
     *
     * @return the size
     */
    int getSize();

    /**
     * Gets the internal values.
     *
     * @return the internal values
     */
    Collection<String> getInternalValues();

    /**
     * Gets the value.
     *
     * @param key
     *            the key
     * @return the value
     */
    String getValue(String key);

    /**
     * Gets the coordinate.
     *
     * @param position
     *            the position
     * @return the coordinate
     */
    String getCoordinate(Integer position);

    /**
     * Sets the pair.
     *
     * @param string
     *            the string
     * @param string2
     *            the string2
     * @throws Exception
     *             the exception
     */
    void setPair(String string, String string2) throws Exception;

    /**
     * Sets the doc.
     *
     * @param doc
     *            the doc
     * @param rAW_COLUMNS
     *            the r a w_ columns
     */
    void setDoc(Document doc, String rAW_COLUMNS);

    /**
     * Sets the counter for this element
     *
     * @param counter
     */
    void setCounter(Integer counter);

}
