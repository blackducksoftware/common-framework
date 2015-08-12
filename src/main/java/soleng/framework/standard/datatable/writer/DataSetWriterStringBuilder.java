package soleng.framework.standard.datatable.writer;

import soleng.framework.standard.datatable.DataTable;
import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.Record;
import soleng.framework.standard.datatable.RecordDef;

public class DataSetWriterStringBuilder implements DataSetWriter {
	private StringBuilder outputStringBuilder;
	
	public DataSetWriterStringBuilder(StringBuilder outputStringBuilder) {
		this.outputStringBuilder = outputStringBuilder;
	}

	public void write(DataTable dataSet) throws Exception {
		RecordDef recordDef = dataSet.getRecordDef();
		
		// Write header row
		for (FieldDef fieldDef : recordDef) {
			this.outputStringBuilder.append(fieldDef.getDescription() + "|");
		}
		this.outputStringBuilder.append("\n");
		
		// Write data rows		
		for (Record record : dataSet) {
			for (FieldDef fieldDef : recordDef) {
				switch (fieldDef.getType()) {
				case STRING:
				case DATE:
					this.outputStringBuilder.append(record.getStringFieldValue(fieldDef.getName()) + "|");
					break;
				case HYPERLINK:
					this.outputStringBuilder.append(record.getHyperlinkFieldValue(fieldDef.getName()).getDisplayText() + "|");
					break;
				}
			}
			this.outputStringBuilder.append("\n");
		}
	}

}

