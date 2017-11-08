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
