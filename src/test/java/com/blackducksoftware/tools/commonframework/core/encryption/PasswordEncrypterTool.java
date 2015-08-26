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

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.encryption.Password;

public class PasswordEncrypterTool {
    private static final String PASSWORD = "]OqP/M!5V\\P3^XS4JsHZSr-A2!jWeRlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_PlI'nKT:u_P";

    @Test
    public void decodeDecryptPassword() throws InvalidKeyException,
	    UnrecoverableKeyException, IllegalBlockSizeException,
	    BadPaddingException, NoSuchAlgorithmException,
	    NoSuchPaddingException, KeyStoreException, CertificateException,
	    IOException, DecoderException {
	String plainTextPassword = null;
	plainTextPassword = Password.decodeDecrypt(PASSWORD);
	System.out.println("'" + PASSWORD + "' decoded/decrypted is: '"
		+ plainTextPassword + "'\n"
		+ "(The single quotes are not part of the password.)");
    }
}
