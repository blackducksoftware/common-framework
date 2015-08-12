package soleng.framework.standard.datatable;

import java.util.Iterator;

class RecordDefIterator implements Iterator<FieldDef> {
	private RecordDef record;
	private int curFieldIndex=0;
	
	public RecordDefIterator(RecordDef record) {
		this.record = record;
	}
	public boolean hasNext() {
		return curFieldIndex < record.size();
	}
	
	public FieldDef next() {
		return record.getFieldDef(curFieldIndex++);
	}
	
	public void remove() {
		throw new UnsupportedOperationException("RecordIterator does not support remove()");
	}
}
