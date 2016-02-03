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
package com.blackducksoftware.tools.commonframework.standard.datatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataTable implements Iterable<Record> {
    private final RecordDef recordDef;

    private final List<Record> records;

    public DataTable(RecordDef recordDef) {
	this.recordDef = recordDef;
	records = new ArrayList<Record>();
    }

    public DataTable(RecordDef recordDef, int size) {
	this.recordDef = recordDef;
	records = new ArrayList<Record>(size);
    }

    public RecordDef getRecordDef() {
	return recordDef;
    }

    public void add(Record record) {
	records.add(record);
    }

    public void clear() {
	records.clear();
    }

    public int size() {
	return records.size();
    }

    public boolean contains(Record record) {
	return records.contains(record);
    }

    public void remove(Record record) {
	records.remove(record);
    }

    @Override
    public Iterator<Record> iterator() {
	return records.iterator();
    }
}
