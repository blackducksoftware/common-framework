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

public class ParserUtils {

    private final static String HTML_ENTITY_QUOT = "&quot;";

    private final static String HTML_ENTITY_QUOT_REPLACEMENT = "\"";

    private final static String EMPTY_STRING = "";

    /**
     * Replace the HTML entity reference "&quot;" with "\""
     *
     * @param text
     *            the text to check for encoding
     * @return the encoded text
     */
    public static String decode(String text) {
	if (text != null && !text.trim().isEmpty()) {
	    return text.replaceAll(HTML_ENTITY_QUOT,
		    HTML_ENTITY_QUOT_REPLACEMENT);
	}
	return EMPTY_STRING;
    }
}
