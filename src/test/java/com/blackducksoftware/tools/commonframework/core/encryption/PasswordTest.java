package com.blackducksoftware.tools.commonframework.core.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordTest {
	private static final String EXPECTED_MSG = "Passwords must consist only of printable ASCII characters, "
			+ "excluding whitespace, #, :, !, \\, =, |, [, {, ("
			+ ". Passwords must have a minimum length of 1 and a maximum length of 64.";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(EXPECTED_MSG, Password.getPasswordRulesMessage());
	}

}
