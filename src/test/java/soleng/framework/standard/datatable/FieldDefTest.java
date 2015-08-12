package soleng.framework.standard.datatable;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import soleng.framework.standard.datatable.FieldDef;
import soleng.framework.standard.datatable.FieldType;

public class FieldDefTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		FieldDef field = new FieldDef("field1", FieldType.STRING, "Field 1");
		assertEquals("field1", field.getName());
		assertEquals(FieldType.STRING, field.getType());
		assertEquals("Field 1", field.getDescription());
	}

}
