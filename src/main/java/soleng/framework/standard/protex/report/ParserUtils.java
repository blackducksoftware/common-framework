/**
 * Utility methods supporting parsing functionality
 * 
 * @author garoushanian
 * @date Oct 15, 2014
 *
 * Copyright (C) 2014 Black Duck Software Inc.
 *
 * http://www.blackducksoftware.com/
 * All rights reserved. 
 *
 */
package soleng.framework.standard.protex.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserUtils {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ParserUtils.class);
	
	private static String HTML_ENTITY_QUOT = "&quot;";
	private static String HTML_ENTITY_QUOT_REPLACEMENT = "\"";
	
	private static String EMPTY_STRING = "";

	/**
	 * Replace the HTML entity reference "&quot;" with "\""
	 * @param text the text to check for encoding
	 * @return the encoded text
	 */
	public static String decode(String text){
		if (text != null && !text.trim().isEmpty()){
			if (text.contains(HTML_ENTITY_QUOT)){
				log.debug("!!!  Replacing text: " + text);
			}
			return text.replaceAll(HTML_ENTITY_QUOT, HTML_ENTITY_QUOT_REPLACEMENT);
		}
		return EMPTY_STRING;
	}
}

