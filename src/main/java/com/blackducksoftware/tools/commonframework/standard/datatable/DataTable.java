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
