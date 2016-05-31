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
import java.util.HashMap;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Basic object mapping for an adhoc report Can be used to retrieve all the
 * column,value pairs .
 *
 *
 * @author akamen
 */
public class AdHocElement implements HocElement {

    public final static String COUNTER = "counter";

    /** The internal map. */
    private final HashMap<Integer, String> internalMap = new HashMap<Integer, String>();

    /** The internal pair map. */
    private final HashMap<String, String> internalPairMap = new HashMap<String, String>();

    /** The doc. */
    private Document doc;

    /**
     * Is this element is vertical
     */
    private Boolean isVertical = false;
    
    /**
     * Checks to see if the internal map is populated
     */
    private Boolean isEmpty = false;
    
    /**
     * Instantiates a new ad hoc element.
     */
    public AdHocElement() {
    }

    /**
     * Determines the value of a column, where the position is the column number
     * .
     *
     * @param position
     *            the position
     * @param name
     *            the name
     * @throws Exception
     *             the exception
     */
    public void setCoordinate(Integer position, String name) throws Exception {
	String someName = internalMap.get(position);

	if (someName == null) {
	    internalMap.put(position, name);
	} else {
	    throw new Exception(
		    "Position and name combination must be unique!  name = "
			    + name);
	}
    }

    @Override
    public void setCounter(Integer counter) {
	if (internalPairMap != null) {
	    if (counter != null) {
		internalPairMap.put(COUNTER, counter.toString());
	    }
	}
    }

    @Override
    public String getCoordinate(Integer pos) {
	return internalMap.get(pos);
    }

    /**
     * Traditional key/value combination, presumably used after value is pulled
     * from coordinate.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @throws Exception
     *             the exception
     */
    @Override
    public void setPair(String key, String value) throws Exception {
	internalPairMap.put(key, value);
    }

    /**
     * Gets the pair keys.
     *
     * @return the pair keys
     */
    public Set<String> getPairKeys() {
	return internalPairMap.keySet();
    }

    @Override
    public String getValue(String key) {
	return internalPairMap.get(key);
    }

    @Override
    public int getSize() {
	return internalMap.size();
    }

    @Override
    public Collection<String> getInternalValues() {
	return internalMap.values();
    }

    /**
     * Returns the JSoup doc element that contains the headers.
     *
     * @return the doc
     */
    public Document getDoc() {
	return doc;
    }

    @Override
    public void setDoc(Document doc, String RAW_COLUMNS) {
	Elements vrows = doc.select(RAW_COLUMNS);
	String headerDoc = vrows.toString();
	doc = Jsoup.parseBodyFragment(headerDoc);
	this.doc = doc;
    }

    public Boolean isVertical() {
	return isVertical;
    }

    public void setIsVertical(Boolean isVertical) {
	this.isVertical = isVertical;
    }

    public Boolean isEmpty() {
	if(this.internalMap.keySet().size() > 0)
	    return false;
	else
	    return true;
    }


}
