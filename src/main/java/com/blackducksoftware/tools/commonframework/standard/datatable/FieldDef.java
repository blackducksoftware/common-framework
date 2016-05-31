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
package com.blackducksoftware.tools.commonframework.standard.datatable;

public class FieldDef {
    private final String name;

    private final FieldType type;

    private final String description;

    private final String formatString; // for type=date, this is a
				       // SimpleDateFormat format string

    private final int maxLen; // for String fields: max # characters; 0 = no
			      // maximum

    public FieldDef(String name, FieldType type, String description) {
	if (type == FieldType.DATE) {
	    formatString = "yyyy-MM-dd HH:mm:ss";
	} else {
	    formatString = null;
	}
	this.name = name;
	this.type = type;
	this.description = description;
	this.maxLen = 0; // 0 = no maximum
    }

    public FieldDef(String name, FieldType type, String description, int maxLen)
	    throws Exception {
	if (type != FieldType.STRING) {
	    throw new Exception(
		    "Maximum length can only be set on String fields.");
	}
	this.name = name;
	this.type = type;
	this.description = description;
	formatString = null;
	this.maxLen = maxLen; // truncate values to this length
    }

    public FieldDef(String name, FieldType type, String description,
	    String formatString) {
	this.name = name;
	this.type = type;
	this.description = description;
	this.formatString = formatString;
	this.maxLen = 0;
    }

    public String getName() {
	return name;
    }

    public FieldType getType() {
	return type;
    }

    public String getDescription() {
	return description;
    }

    public String getFormatString() {
	return formatString;
    }

    public int getMaxLen() {
	return maxLen;
    }

}
