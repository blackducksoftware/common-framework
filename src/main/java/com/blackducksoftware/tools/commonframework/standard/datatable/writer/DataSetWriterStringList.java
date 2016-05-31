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
package com.blackducksoftware.tools.commonframework.standard.datatable.writer;

import java.util.List;

import com.blackducksoftware.tools.commonframework.standard.datatable.DataTable;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.Record;
import com.blackducksoftware.tools.commonframework.standard.datatable.RecordDef;

public class DataSetWriterStringList implements DataSetWriter {
    private final List<String> outputStringList;

    private final boolean includeHeader;

    /**
     * Convert each record to a String, and add it to the given (already created) list of strings.
     *
     * @param outputStringList
     *            a string list you've already created (usually empty)
     * @param includeHeader
     */
    public DataSetWriterStringList(List<String> outputStringList, boolean includeHeader) {
        this.outputStringList = outputStringList;
        this.includeHeader = includeHeader;
    }

    @Override
    public void write(DataTable dataSet) throws Exception {
        RecordDef recordDef = dataSet.getRecordDef();
        StringBuilder outputStringBuilder;

        if (includeHeader) {
            // add header row
            outputStringBuilder = new StringBuilder();
            for (FieldDef fieldDef : recordDef) {
                outputStringBuilder.append(fieldDef.getDescription() + "|");
            }
            outputStringList.add(outputStringBuilder.toString());
        }

        // Write data rows
        for (Record record : dataSet) {
            outputStringBuilder = new StringBuilder();
            for (FieldDef fieldDef : recordDef) {
                switch (fieldDef.getType()) {
                case STRING:
                case DATE:
                    outputStringBuilder.append(record
                            .getStringFieldValue(fieldDef.getName()) + "|");
                    break;
                case HYPERLINK:
                    outputStringBuilder.append(record.getHyperlinkFieldValue(
                            fieldDef.getName()).getDisplayText()
                            + "|");
                    break;
                }
            }
            outputStringList.add(outputStringBuilder.toString());
        }
    }

}
