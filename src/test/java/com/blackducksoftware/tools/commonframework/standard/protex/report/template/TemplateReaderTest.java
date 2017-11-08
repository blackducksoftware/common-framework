/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.tools.commonframework.standard.protex.report.template;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManagerTest;
import com.blackducksoftware.tools.commonframework.core.config.testbeans.TestProtexConfigurationManager;
import com.blackducksoftware.tools.commonframework.standard.protex.report.model.TemplateColumn;
import com.blackducksoftware.tools.commonframework.standard.protex.report.model.TemplateSheet;

// TODO: Auto-generated Javadoc
/**
 * Tests the Template Reader.
 *
 * @author akamen
 */
public class TemplateReaderTest extends TemplateTestConstants {

    /** The tr. */
    private TemplateReader tr = new TemplateReader();

    /** The tr config. */
    private TemplateReader trConfig = null;

    /** The working file. */
    private File workingFile = null;

    /** The not afile. */
    private File notAfile = null;

    /** The protex cm. */
    private TestProtexConfigurationManager protexCM;

    /** The junit working folder. */
    @Rule
    public TemporaryFolder junitWorkingFolder = new TemporaryFolder();

    /** The exception. */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Creates the empty work dir.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Before
    public void createEmptyWorkDir() throws IOException {
        // In order to test the mappings, bring in a ConfigManager
        String fullLocation = ClassLoader.getSystemResource(
                ConfigurationManagerTest.testFile).getFile();
        protexCM = new TestProtexConfigurationManager(fullLocation);

        workingFile = junitWorkingFolder.newFile("temp.xlsx");
        notAfile = junitWorkingFolder.newFolder();
    }

    /**
     * Copy template into good file test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void copyTemplateIntoGoodFileTest() throws Exception {
        tr.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
    }

    /**
     * Copy template from missing file.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void copyTemplateFromMissingFile() throws Exception {
        String bogusName = "doesnotexist_file";

        exception.expect(IOException.class);
        exception.expectMessage(bogusName + " does not exist");
        tr.copyTemplateIntoFile(bogusName, workingFile);
    }

    /**
     * Copy template into bad location test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void copyTemplateIntoBadLocationTest() throws Exception {
        exception.expect(IOException.class);
        tr.copyTemplateIntoFile(TEMPLATE_NAME, notAfile);
    }

    /**
     * Test populated map.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testPopulatedMap() throws Exception {
        tr.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
        Map<String, TemplateSheet> sheetMap = tr.getSheetMap();
        Assert.assertEquals(2, sheetMap.size());
    }

    /**
     * Test both sheet and its columns.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testTemplateSheet() throws Exception {
        tr.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
        Map<String, TemplateSheet> sheetMap = tr.getSheetMap();

        // First Sheet
        TemplateSheet sheet = sheetMap.get(TEST_SHEET_NAME);
        Map<String, TemplateColumn> columns = sheet.getColumnMap();

        Assert.assertEquals(3, columns.size());

        testColumn(columns, COLUMN_A, 0);
        testColumn(columns, COLUMN_B, 1);
        testColumn(columns, COLUMN_C, 2);

        // Second sheet
        TemplateSheet sheetTwo = sheetMap.get(TEST_SHEET_NAME_2);
        Map<String, TemplateColumn> columnsFromSheetTwo = sheetTwo
                .getColumnMap();

        testColumn(columnsFromSheetTwo, COLUMN_A_2, 0);
        testColumn(columnsFromSheetTwo, COLUMN_B_2, 1);

    }

    /**
     * Make sure that each sheet and each column have unique cell styles.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testTemplateSheetStylesUniqueness() throws Exception {
        tr.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
        Map<String, TemplateSheet> sheetMap = tr.getSheetMap();

        // First Sheet
        TemplateSheet sheet = sheetMap.get(TEST_SHEET_NAME);
        Map<String, TemplateColumn> columns = sheet.getColumnMap();

        TemplateColumn columnA = columns.get(COLUMN_A);

        TemplateSheet sheetTwo = sheetMap.get(TEST_SHEET_NAME_2);
        Map<String, TemplateColumn> columnsFromSheetTwo = sheetTwo
                .getColumnMap();

        TemplateColumn columnAPageTwo = columnsFromSheetTwo.get(COLUMN_A_2);
        TemplateColumn columnBPageTwo = columnsFromSheetTwo.get(COLUMN_B_2);

        CellStyle columnAStyle = columnA.getCellStyle();
        CellStyle columnBStyle = columnA.getCellStyle();
        CellStyle columnCStyle = columnA.getCellStyle();
        CellStyle columnAPage2Style = columnAPageTwo.getCellStyle();
        CellStyle columnBPage2Style = columnBPageTwo.getCellStyle();

        Assert.assertNotEquals(columnAStyle, columnAPage2Style);
        Assert.assertNotEquals(columnBStyle, columnAPage2Style);
        Assert.assertNotEquals(columnCStyle, columnAPage2Style);

        Assert.assertNotEquals(columnAStyle, columnBPage2Style);
        Assert.assertNotEquals(columnBStyle, columnBPage2Style);
        Assert.assertNotEquals(columnCStyle, columnBPage2Style);
    }

    /**
     * Tests user specified column/name mappings We provide a
     * ConfigurationManager to the Template Reader.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testTemplateSheetWithUserMappings() throws Exception {
        trConfig = new TemplateReader(protexCM);
        trConfig.copyTemplateIntoFile(TEMPLATE_NAME, workingFile);
        Map<String, TemplateSheet> sheetMap = trConfig.getSheetMap();

        TemplateSheet sheet = sheetMap.get(TEST_SHEET_NAME);
        Map<String, TemplateColumn> columns = sheet.getColumnMap();

        testColumnMappings(columns, COLUMN_A, "Value1");
        testColumnMappings(columns, COLUMN_B, "Value2");
        testColumnMappings(columns, COLUMN_C, "Value4");

    }

    /**
     * Test bad template sheet missing styles.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    /**
     * Pass in excel template with no styles and make sure proper exceptions
     * and messages are thrown
     */
    public void testBadTemplateSheetMissingStyles() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("No style row found! Please create one.");
        tr.copyTemplateIntoFile(BAD_TEMPLATE_NO_STYLE, workingFile);
    }

    /**
     * Poorly formatted style in the first position.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testBadTemplateSheetBadFormat() throws Exception {
        exception.expect(Exception.class);
        exception
                .expectMessage("The following style position is not defined: " + 0);
        tr.copyTemplateIntoFile(BAD_TEMPLATE_FORMAT, workingFile);
    }

    /**
     * Test the "no copy" usage that SCM Connector uses. Generate a workbook
     * from a template file, and populate the template map.
     *
     * @throws Exception
     */
    @Test
    public void testNoCopyUsage() throws Exception {
        Properties props = new Properties();
        props.setProperty("protex.server.name", "not used");
        props.setProperty("protex.user.name", "not used");
        props.setProperty("protex.password", "not used");
        ConfigurationManager config = new TestProtexConfigurationManager(props);

        // Generate a workbook from a template file, and populate the template
        // map.
        TemplateReader templateReader = new TemplateReader(config);
        Workbook wb = TemplateReader.generateWorkBookFromFile(new File(
                "src/test/resources/test_excel_template.xlsx"));
        templateReader.setTemplateBook(wb);
        templateReader.populateTemplateMap();

        // Check the resulting workbook
        Assert.assertEquals("test_sheet", wb.getSheetAt(0).getSheetName());

        Assert.assertEquals("ColumnA", wb.getSheetAt(0).getRow(0).getCell(0)
                .getStringCellValue());
        Assert.assertEquals("ColumnB", wb.getSheetAt(0).getRow(0).getCell(1)
                .getStringCellValue());
        Assert.assertEquals("ColumnC", wb.getSheetAt(0).getRow(0).getCell(2)
                .getStringCellValue());

        Assert.assertEquals("tmp", wb.getSheetAt(0).getRow(1).getCell(0)
                .getStringCellValue());
        Assert.assertEquals("tmp", wb.getSheetAt(0).getRow(1).getCell(1)
                .getStringCellValue());
        Assert.assertEquals(1, wb.getSheetAt(0).getRow(1).getCell(2)
                .getNumericCellValue(), 0.01);

        Assert.assertEquals("test_sheet_2", wb.getSheetAt(1).getSheetName());

        Assert.assertEquals("ColumnA_Sheet2", wb.getSheetAt(1).getRow(0)
                .getCell(0).getStringCellValue());
        Assert.assertEquals("ColumnB_Sheet2", wb.getSheetAt(1).getRow(0)
                .getCell(1).getStringCellValue());

        Assert.assertEquals("tmp", wb.getSheetAt(1).getRow(1).getCell(0)
                .getStringCellValue());
        Assert.assertEquals("tmp", wb.getSheetAt(1).getRow(1).getCell(1)
                .getStringCellValue());
    }

    /**
     * Convenient test method that makes sure each object contains appropriate
     * values.
     *
     * @param columns
     *            the columns
     * @param expectedName
     *            the expected name
     * @param expectedPos
     *            the expected pos
     */
    private void testColumn(Map<String, TemplateColumn> columns,
            String expectedName, Integer expectedPos) {
        TemplateColumn column = columns.get(expectedName);
        Assert.assertNotNull(column);

        Integer pos = column.getColumnPos();
        Assert.assertEquals(expectedPos, pos);

        Object cs = column.getCellStyle();
        Assert.assertNotNull(cs);

    }

    /**
     * Test column mappings.
     *
     * @param columns
     *            the columns
     * @param expectedName
     *            the expected name
     * @param lookupValue
     *            the lookup value
     */
    private void testColumnMappings(Map<String, TemplateColumn> columns,
            String expectedName, String lookupValue) {
        TemplateColumn column = columns.get(expectedName);

        String mappingName = column.getLookupMappingName();
        Assert.assertEquals(lookupValue, mappingName);
    }
}
