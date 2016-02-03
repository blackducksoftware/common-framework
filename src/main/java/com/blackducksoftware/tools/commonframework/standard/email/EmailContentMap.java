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
package com.blackducksoftware.tools.commonframework.standard.email;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The map of keys that are defined in the user specified template.
 *
 * @author akamen
 *
 */
public class EmailContentMap extends HashMap<String, String> {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private static final long serialVersionUID = 1L;

    public EmailContentMap() {
	super();
    }

    /**
     * Place the value of the key within your template
     *
     * @throws IllegalArgumentException
     *             if key does not exist.
     */
    @Override
    public String put(String key, String value) {
	return super.put(key, value);
    }

    /**
     * Checks to see if the template contains your key
     *
     * @param key
     * @return
     */
    public Boolean doesMapContainKey(String key) {
	log.debug("Checking if key exists: " + key);
	if (super.containsKey(key)) {
	    log.debug("Key exists.");
	    return true;
	} else {
	    log.debug("Key does not exist.");
	    return false;
	}
    }

}
