package soleng.framework.standard.datatable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Record {
	private RecordDef recordDef;
	private Map<String, Object> fields;
	
	public Record(RecordDef recordDef) {
		this.recordDef = recordDef;
		fields = new HashMap<String, Object>(recordDef.size());
	}
	
	public void setFieldValue(String fieldName, String fieldValue) throws Exception {
		for (FieldDef fieldDef : recordDef) {
			if (fieldDef.getName().equals(fieldName)) {
				switch (fieldDef.getType()) {
				case STRING:
					if ((fieldDef.getMaxLen() > 0) && (fieldValue.length() > fieldDef.getMaxLen())) {
						fieldValue = fieldValue.substring(0, fieldDef.getMaxLen()).trim();
					}
					fields.put(fieldName, fieldValue);
					return;
				default:
					throw new Exception("FieldType " + fieldDef.getType() + " not supported");
				}
			}
		}
		throw new Exception("Field " + fieldName + " is not defined");
	}
	
	public void setFieldValue(String fieldName, HyperlinkFieldValue fieldValue) throws Exception {
		for (FieldDef fieldDef : recordDef) {
			if (fieldDef.getName().equals(fieldName)) {
				switch (fieldDef.getType()) {
				case HYPERLINK:
					fields.put(fieldName, fieldValue);
					return;
				default:
					throw new Exception("FieldType " + fieldDef.getType() + " not supported");
				}
			}
		}
		throw new Exception("Field " + fieldName + " is not defined");
	}
	
	public void setFieldValue(String fieldName, GregorianCalendar fieldValue) throws Exception {
		for (FieldDef fieldDef : recordDef) {
			if (fieldDef.getName().equals(fieldName)) {
				switch (fieldDef.getType()) {
				case DATE:
					fields.put(fieldName, fieldValue);
					return;
				default:
					throw new Exception("FieldType " + fieldDef.getType() + " not supported");
				}
			}
		}
		throw new Exception("Field " + fieldName + " is not defined");
	}
	
	public Date getDateFieldValueAsDate(String fieldName) throws Exception {
		FieldDef fieldDef = recordDef.getFieldDef(fieldName);

		if (!fields.containsKey(fieldName)) {
			return null;
		}
		if (fieldDef.getType() == FieldType.DATE) {
			GregorianCalendar cal = (GregorianCalendar)(fields.get(fieldName));
			Date date = cal.getTime();
			return date;
		}
		throw new Exception("Field " + fieldName + " is not a Date type field");
	}
	
	public String getDateFieldValue(String fieldName) throws Exception {
		return getStringFieldValue(fieldName);
	}
	
	public String getStringFieldValue(String fieldName) throws Exception {
		FieldDef fieldDef = recordDef.getFieldDef(fieldName);

		if (!fields.containsKey(fieldName)) {
			return null;
		}
		if (fieldDef.getType() == FieldType.DATE) {
			GregorianCalendar cal = (GregorianCalendar)(fields.get(fieldName));
			Date date = cal.getTime();
			DateFormat formatter = new SimpleDateFormat(fieldDef.getFormatString());
			String formattedDate = formatter.format(date);
			return formattedDate;
		} else if (fieldDef.getType() == FieldType.STRING) {
			return (String)fields.get(fieldName);
		}
		throw new Exception("Field " + fieldName + " is not a String or Date type field");
	}
	
	public HyperlinkFieldValue getHyperlinkFieldValue(String fieldName) throws Exception {
		FieldDef fieldDef = recordDef.getFieldDef(fieldName);
		if (fieldDef.getType() != FieldType.HYPERLINK) {
			throw new Exception("Field " + fieldName + " is not a Hyperlink type field");
		}
		if (!fields.containsKey(fieldName)) {
			throw new Exception("Field " + fieldName + " has no value");
		}
		
		return (HyperlinkFieldValue)fields.get(fieldName);
	}
}
