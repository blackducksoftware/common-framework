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
