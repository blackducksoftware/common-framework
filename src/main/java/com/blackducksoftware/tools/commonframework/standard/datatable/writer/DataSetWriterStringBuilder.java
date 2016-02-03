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
package com.blackducksoftware.tools.commonframework.standard.datatable.writer;

import com.blackducksoftware.tools.commonframework.standard.datatable.DataTable;
import com.blackducksoftware.tools.commonframework.standard.datatable.FieldDef;
import com.blackducksoftware.tools.commonframework.standard.datatable.Record;
import com.blackducksoftware.tools.commonframework.standard.datatable.RecordDef;

public class DataSetWriterStringBuilder implements DataSetWriter {
    private final StringBuilder outputStringBuilder;

    public DataSetWriterStringBuilder(StringBuilder outputStringBuilder) {
	this.outputStringBuilder = outputStringBuilder;
    }

    @Override
    public void write(DataTable dataSet) throws Exception {
	RecordDef recordDef = dataSet.getRecordDef();

	// Write header row
	for (FieldDef fieldDef : recordDef) {
	    outputStringBuilder.append(fieldDef.getDescription() + "|");
	}
	outputStringBuilder.append("\n");

	// Write data rows
	for (Record record : dataSet) {
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
	    outputStringBuilder.append("\n");
	}
    }

}
