/**
 * Handles parsing utility module testing
 * 
 * @author garoushanian
 * @date Oct 15, 2014
 *
 * Copyright (C) 2014 Black Duck Software Inc.
 *
 * http://www.blackducksoftware.com/
 * All rights reserved. 
 *
 */
package soleng.framework.standard.protex.report;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOneReplacement() {
		String expectedText = "Testing \"";		
		String text = "Testing &quot;";
		
		String actualText = ParserUtils.decode(text);		
		assertEquals(expectedText, actualText);
	}
	
	@Test
	public void testTwoReplacements() {
		String expectedText = "\" Testing \"";		
		String text = "&quot; Testing &quot;";
		
		String actualText = ParserUtils.decode(text);		
		assertEquals(expectedText, actualText);
	}
		
	@Test
	public void testMultipleReplacements() {
		String expectedText = "<p>&nbsp;Hello World\"\"\"</p>";
		String text = "<p>&nbsp;Hello World&quot;&quot;&quot;</p>";
		
		String actualText = ParserUtils.decode(text);		
		assertEquals(expectedText, actualText);
	}
	
}
