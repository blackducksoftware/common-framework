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

import java.util.Iterator;
import java.util.List;

public class RecordDef implements Iterable<FieldDef> {
    private final List<FieldDef> fieldDefs;

    public RecordDef(List<FieldDef> fields) {
	fieldDefs = fields;
    }

    public FieldDef getFieldDef(int index) {
	return fieldDefs.get(index);
    }

    public FieldDef getFieldDef(String fieldName) throws Exception {

	for (FieldDef fieldDef : fieldDefs) {
	    if (fieldDef.getName().equals(fieldName)) {
		return fieldDef;
	    }
	}

	int fieldIndex = fieldDefs.indexOf(fieldName);
	if (fieldIndex < 0) {
	    throw new Exception("Field " + fieldName + " is not defined");
	}

	return fieldDefs.get(fieldIndex);
    }

    public int size() {
	return fieldDefs.size();
    }

    @Override
    public Iterator<FieldDef> iterator() {
	return new RecordDefIterator(this);
    }
}
