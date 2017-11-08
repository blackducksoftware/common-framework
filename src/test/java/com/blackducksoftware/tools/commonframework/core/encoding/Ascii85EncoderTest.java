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
package com.blackducksoftware.tools.commonframework.core.encoding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.encryption.Password;

/**
 *
 * @author sbillings
 *
 */
public class Ascii85EncoderTest {
	private static final Charset UTF8 = Charset.forName("UTF-8");

	private static Random rand = new Random(System.currentTimeMillis());

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws Exception {
		final int numTests = 10000; // 10K is a good number

		for (int i = 0; i < numTests; i++) {
			final String psw = generateRandomPassword(64);
			testPassword(psw);
		}
	}

	private void testPassword(final String origString) throws Exception {
		final byte[] encryptedBinary = Password.encryptStringToBinary(origString);
		final byte[] encodedBytes = Ascii85Encoder.encode(encryptedBinary);
		assertTrue(isAscii(encodedBytes));

		final String encodedString = new String(encodedBytes, UTF8);
		final byte[] decodedBytes = Ascii85Encoder.decode(encodedString.getBytes());
		final String decryptedString = Password.decryptBinaryToString(decodedBytes);
		assertEquals(origString, decryptedString);
	}

	public static String generateRandomPassword(final int maxLen) {
		final List<Character> badChars = Arrays
				.asList(Password.PROHIBITED_CHARS);

		int len = 0;
		while (len < Password.MIN_LENGTH) {
			len = rand.nextInt() % maxLen;
		}
		final byte[] outputBuffer = new byte[len];

		final int numPossibleCharValues = Password.MAX_CHAR_VALUE
				- Password.MIN_CHAR_VALUE + 1;
		for (int i = 0; i < len; i++) {

			int charValue = -1;
			do {
				int charValueOffset = -1;
				while (charValueOffset < 0) {
					charValueOffset = rand.nextInt();
				}
				charValue = Password.MIN_CHAR_VALUE + charValueOffset
						% numPossibleCharValues;
			} while (badChars.contains(new Character((char) charValue)));

			outputBuffer[i] = (byte) charValue;
		}
		final String randomString = new String(outputBuffer, UTF8);
		return randomString;
	}

	private boolean isAscii(final byte[] bytes) {
		final String stringToTest = new String(bytes, UTF8);
		return StringUtils.isAsciiPrintable(stringToTest);
	}

}
