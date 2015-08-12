package soleng.framework.standard.datatable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	public Iterator<Record> iterator() {
		return records.iterator();
	}
}
