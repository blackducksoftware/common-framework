/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
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

}
