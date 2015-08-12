package soleng.framework.standard.datatable.writer;

import soleng.framework.standard.datatable.DataTable;
import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.Record;
import soleng.framework.standard.datatable.RecordDef;

public class DataSetWriterStdOut implements DataSetWriter {

	public void write(DataTable dataSet) throws Exception {
		RecordDef recordDef = dataSet.getRecordDef();
		
		// Write header row
		for (FieldDef fieldDef : recordDef) {
			System.out.print(fieldDef.getDescription() + "|");
		}
		System.out.println();
		
		// Write data rows		
		for (Record record : dataSet) {
			for (FieldDef fieldDef : recordDef) {
				switch (fieldDef.getType()) {
				case STRING:
				case DATE:
					System.out.print(record.getStringFieldValue(fieldDef.getName()) + "|");
					break;
				case HYPERLINK:
					System.out.print(record.getHyperlinkFieldValue(fieldDef.getName()).getDisplayText() + "|");
					break;
				}
				
			}
			System.out.println();
		}
	}

}
