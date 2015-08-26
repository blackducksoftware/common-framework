/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.encryption;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.encryption.Password;

public class EncryptionTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void test() throws Exception {
	String password = "testpassword";
	String encryptedPassword = Password.encryptEncode(password);
	System.out.println("Password '" + password + "' encrypted: "
		+ encryptedPassword);
	assertEquals(
		"=MFOYfHA1mB<<u#:(TBIlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P",
		encryptedPassword);
	assertEquals(password, Password.decodeDecrypt(encryptedPassword));
    }

    @Test
    public void testEmpty() throws Exception {

	try {
	    Password.encryptEncode("");
	    fail("Expected exception on empty password");
	} catch (IllegalArgumentException e) {
	    // expect this
	}
    }

    @Test
    public void testNull() throws Exception {
	String password = null;
	try {
	    Password.encryptEncode(password);
	    fail("Should have thrown an exception");
	} catch (IllegalArgumentException e) {
	    // expect this
	}
    }

    @Test
    public void testValidation() {
	assertTrue(Password
		.isValidPassword("1234567890123456789012345678901234567890123456789012345678901234"));
	assertFalse(Password
		.isValidPassword("12345678901234567890123456789012345678901234567890123456789012345"));
	assertFalse(Password.isValidPassword(" abc"));
	assertFalse(Password.isValidPassword("abc "));
	assertFalse(Password.isValidPassword("abc\n"));
    }

}
