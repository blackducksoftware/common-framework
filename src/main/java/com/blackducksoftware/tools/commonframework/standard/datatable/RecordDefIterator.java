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
