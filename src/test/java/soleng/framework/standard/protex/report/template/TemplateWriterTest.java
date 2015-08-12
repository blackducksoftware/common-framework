package soleng.framework.standard.protex.report.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import soleng.framework.core.config.ConfigurationManagerTest;
import soleng.framework.core.config.testbeans.TestProtexConfigurationManager;
import soleng.framework.standard.protex.report.model.TemplatePojo;
import soleng.framework.standard.protex.report.model.TemplateSheet;

// TODO: Auto-generated Javadoc
/**
 * The Class TemplateWriterTest.
 */
public class TemplateWriterTest extends TemplateTestConstants {

	/** The protex cm. */
	private TestProtexConfigurationManager protexCM;
	
	/** The working file. */
	private File workingFile = null;
	
	/** The template reader. */
	private TemplateReader templateReader = null;
	
	/** The pojo list. */
	private List<TemplatePojo> pojoList = new ArrayList<TemplatePojo>();
	
	// The output of the test write
	/** The output book. */
	private Workbook outputBook = null;
	
	// POJO Values
	/** The POJ o_ t v_1. */
	private static String POJO_TV_1 = "Pojo_Test_Value_1";
	
	/** The POJ o_ t v_2. */
	private static String POJO_TV_2 = "Pojo_Test_Value_Two";
	
	/** The POJ o_ t v_3. */
	private static String POJO_TV_3 = "Pojo_Test_Value_3";
	
	/** The junit working folder. */
	@Rule
    public TemporaryFolder junitWorkingFolder = new TemporaryFolder();
	
	/** The exception. */
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	 * Creates a directory, configuration file and write out the Test Pojo.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void createWorkingDirAndPopulateTemplate() throws Exception 
	{
		// In order to test the mappings, bring in a ConfigManager
		String fullLocation = ClassLoader.getSystemResource(ConfigurationManagerTest.testFile).getFile();		
		protexCM = new TestProtexConfigurationManager(fullLocation);	
		
		workingFile = junitWorkingFolder.newFile("write_out.xlsx");
		
		templateReader = new TemplateReader(protexCM);
		// Populate the template
		templateReader.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
		
		// Setup Pojo
		TestPojo testPojo = new TestPojo();
		testPojo.setValue1(POJO_TV_1);
		testPojo.setValue2(POJO_TV_2);
		testPojo.setValue4(POJO_TV_3);
		
		TestPojo testPojo2 = new TestPojo();
		testPojo2.setValue1(POJO_TV_1);
		testPojo2.setValue2(POJO_TV_2);
		testPojo2.setValue4(POJO_TV_3);
		
		pojoList.add(testPojo);
		pojoList.add(testPojo2);
		
	
		// Grab the sheet
		TemplateSheet ts = templateReader.getSheetMap().get(TEST_SHEET_NAME);
		TemplateWriter tw = new TemplateWriter(templateReader);
		
		// Write out the data
		tw.writeOutPojo(pojoList, ts, TestPojo.class);
		
		outputBook = TemplateReader.generateWorkBookFromFile(workingFile);
	}
	
	/**
	 * Handle the POI case where multiple sheets are being written to the same workbook.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testWriteOutForMultipleSheets() throws Exception
	{
		TemplateReader templateReader = new TemplateReader(protexCM);
		workingFile = junitWorkingFolder.newFile("write_out_multiple.xlsx");
		templateReader.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
		
		TemplateWriter tw = new TemplateWriter(templateReader);
		
		TemplateSheet tsOne = templateReader.getSheetMap().get(TEST_SHEET_NAME);
		TemplateSheet tsTwo = templateReader.getSheetMap().get(TEST_SHEET_NAME_2);
		
		TestPojoPageTwo pojo = new TestPojoPageTwo();
		pojo.setValue1page2(POJO_TV_1);
		pojo.setValue2page2(POJO_TV_2);
	
		try{
			tw.writeOutPojo(pojoList, tsOne, TestPojo.class);
			
			// When writing the second sheet, make sure only the new POJOs are there
			pojoList.clear();
			pojoList.add(pojo);
					
			tw.writeOutPojo(pojoList, tsTwo, TestPojoPageTwo.class);
		} catch (Exception e)
		{
			Assert.fail(e.getMessage());
		}
	
		
		Sheet sheet = outputBook.getSheet(TEST_SHEET_NAME_2);
		int count = sheet.getPhysicalNumberOfRows();
		
		Assert.assertEquals(2, count);
		
	}
	
	/**
	 * Test row count.
	 */
	@Test
	public void testRowCount() 
	{
		Sheet sheet = outputBook.getSheet(TEST_SHEET_NAME);
		
		int rowCount = sheet.getPhysicalNumberOfRows();	
		Assert.assertEquals(3, rowCount);	
	}
	
	/**
	 * Test column count.
	 */
	@Test 
	public void testColumnCount()
	{	
		Sheet sheet = outputBook.getSheet(TEST_SHEET_NAME);
		Row testRow = sheet.getRow(1);	
		
		int cellCount = testRow.getPhysicalNumberOfCells();
	
		Assert.assertEquals(3, cellCount);
		
	}
	
	/**
	 * Tests to make sure that we wrote out, exactly what we set in the pojo.
	 */
	@Test
	public void testColumnValues()
	{
		Sheet sheet = outputBook.getSheet(TEST_SHEET_NAME);
		Row testRow = sheet.getRow(1);	
		
		Cell cellOne = testRow.getCell(0);
		Cell cellTwo = testRow.getCell(1);
		Cell cellThree = testRow.getCell(2);
		
		Assert.assertEquals(POJO_TV_1, cellOne.toString());
		Assert.assertEquals(POJO_TV_2, cellTwo.toString());
		Assert.assertEquals(POJO_TV_3, cellThree.toString());
	}
	
	/**
	 * This tests a POJO that has unimplemented mappings.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testBadPojo() throws Exception
	{
		exception.expect(Exception.class);
		exception.expectMessage("Methods missing from POJO: " + "Value1,Value2,Value4");
		
		pojoList.clear();
		BadTestPojo btj = new BadTestPojo();
		pojoList.add(btj);
		
		TemplateWriter<TemplatePojo> tw = new TemplateWriter<TemplatePojo>(templateReader);
		TemplateSheet ts = templateReader.getSheetMap().get(TEST_SHEET_NAME);
		
		// Write out the data
		tw.writeOutPojo(pojoList, ts, TemplatePojo.class);
		
	}
	
	/**
	 * Handles the case where user has specified no mappings.  
	 * Expecting an elegant exception.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testNoMappings() throws Exception
	{
		exception.expect(Exception.class);
		// Expecting the first column in the first sheet.
		exception.expectMessage("Columns missing mapping: " + COLUMN_A_2 + "," + COLUMN_B_2);
		// In order to test the mappings, bring in a ConfigManager
		String fullLocation = ClassLoader.getSystemResource(ConfigurationManagerTest.testFileNoMappings).getFile();		
		protexCM = new TestProtexConfigurationManager(fullLocation);	

		templateReader = new TemplateReader(protexCM);
		// Populate the template
		templateReader.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
		
		TestPojo tp = new TestPojo();
		// Grab the sheet
		TemplateSheet ts = templateReader.getSheetMap().get(TEST_SHEET_NAME_2);
		TemplateWriter tw = new TemplateWriter(templateReader);
		
		// Write out the data
		tw.writeOutPojo(pojoList, ts, TestPojo.class);
		
	}
	
}
