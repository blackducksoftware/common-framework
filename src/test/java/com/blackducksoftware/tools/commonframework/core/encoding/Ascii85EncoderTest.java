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
	int numTests = 10000; // 10K is a good number

	for (int i = 0; i < numTests; i++) {
	    String psw = generateRandomPassword(64);
	    testPassword(psw);
	}
    }

    private void testPassword(String origString) throws Exception {
	byte[] encryptedBinary = Password.encryptStringToBinary(origString);
	byte[] encodedBytes = Ascii85Encoder.encode(encryptedBinary);
	assertTrue(isAscii(encodedBytes));

	String encodedString = new String(encodedBytes, UTF8);
	byte[] decodedBytes = Ascii85Encoder.decode(encodedString.getBytes());
	String decryptedString = Password.decryptBinaryToString(decodedBytes);
	assertEquals(origString, decryptedString);
    }

    public static String generateRandomPassword(int maxLen) {
	List<Character> badChars = Arrays
		.asList(Password.PROPERTY_VALUE_PROBLEMATIC_CHARS);

	int len = 0;
	while (len < Password.MIN_LENGTH) {
	    len = rand.nextInt() % maxLen;
	}
	byte[] outputBuffer = new byte[len];

	int numPossibleCharValues = Password.MAX_CHAR_VALUE
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
	String randomString = new String(outputBuffer, UTF8);
	return randomString;
    }

    private boolean isAscii(byte[] bytes) {
	String stringToTest = new String(bytes, UTF8);
	return StringUtils.isAsciiPrintable(stringToTest);
    }

}
