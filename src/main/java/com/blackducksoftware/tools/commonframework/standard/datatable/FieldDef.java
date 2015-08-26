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
