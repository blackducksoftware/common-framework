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
