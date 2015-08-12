package soleng.framework.standard.datatable;

import java.util.Iterator;
import java.util.List;

public class RecordDef implements Iterable<FieldDef> {
	private List<FieldDef> fieldDefs;
	
	public RecordDef(List<FieldDef> fields) {
		this.fieldDefs = fields;
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
	
	public Iterator<FieldDef> iterator() {
		return new RecordDefIterator(this);
	}
}
