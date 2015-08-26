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

class RecordDefIterator implements Iterator<FieldDef> {
    private final RecordDef record;

    private int curFieldIndex = 0;

    public RecordDefIterator(RecordDef record) {
	this.record = record;
    }

    @Override
    public boolean hasNext() {
	return curFieldIndex < record.size();
    }

    @Override
    public FieldDef next() {
	return record.getFieldDef(curFieldIndex++);
    }

    @Override
    public void remove() {
	throw new UnsupportedOperationException(
		"RecordIterator does not support remove()");
    }
}
