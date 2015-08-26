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
