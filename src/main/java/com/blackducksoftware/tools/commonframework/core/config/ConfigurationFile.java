/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 *  under the License.
 *
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.jmatrix.eproperties.EProperties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.core.encryption.Password;

/**
 * This class manages a configuration file. It can load the config file into a
 * Properties object and save a new version, preserving the original format, in
 * which all passwords needing encryption are encrypted and adjusting the
 * password meta-properties (removing *.password.isplaintext, and inserting
 * *.properties.isencrypted, where appropriate).
 * <p>
 * Password encryption works as follows:
 * <p>
 * *.password.isencrypted missing means: encrypt this password.
 *  <br>
 * *.password.isencrypted=true means: this password is already encrypted.
 *  <br>
 * *.password.isencrypted=false means: do not encrypt this password
 * <p>
 * Users will enter plain text passwords into the config file, and the utility
 * will automatically replace them in-place with encrypted passwords (setting
 * the new *.password.isencrypted property to true as it does it) unless it
 * finds *.password.isencrypted=false. - When creating a new config file, the
 * user enters the plain text password in the value of the *.password property.
 * If he does NOT want it to be encrypted automatically, he also inserts
 * *.password.isencrypted=false. - To change a password, the user changes the
 * *.password value to the plain text password. If he wants the password to be
 * automatically encrypted, he removes the *.password.isencrypted property. If
 * he wants the password to be left in plain text, he sets
 * *.password.isencrypted=false. - All password properties must be of the
 * pattern *.password. All properties of the form *.password must be passwords
 * that are subject to these encryption rules.
 * <p>
 * Legacy config file handling:
 * <p>
 * *.isplaintext=true means the password is plain text *.isplaintext=false means
 * the password is base64-encoded (which is now an obsolete representation)
 * *.isplaintext missing means the password is plain text (at least that's what
 * we'll assume) In this scenario, if the password looks like it might be
 * base64-encoded, a warning is written to the log by ConfigurationPassword.
 * <p>
 * The property *.password.isplaintext, if present, will determine whether the
 * utility reads the password as plain text (true) or base64-encoded (false). If
 * isplaintext=true was present, the password will be left in plain text, and
 * the isplaintext property will be replaced with isencrypted=false. - The
 * property *.password.isplaintext will be removed automatically. Once this
 * property is removed, the config file is no longer considered a legacy file. -
 * If the property *.password.isplaintext is missing, then the config file is
 * treated as a non-legacy file as described above. For plain text passwords,
 * this will work fine. For base64 encoded passwords, this will produce an
 * invalid encrypted password. When a utility encounters a non-encrypted
 * password that matches the base64-encoded pattern, it will write a warning to
 * the log which should make it easier to troubleshoot this scenario.
 * <p>
 * All isplaintext properties will be removed. If it does not exist, the
 * isencrypted property will be inserted. There is never any need to change the
 * value of an existing isencrypted property.
 *
 * @author sbillings
 *
 */
public class ConfigurationFile {
    private final Logger log = LoggerFactory.getLogger(this.getClass()
	    .getName());

    private final File file;

    private boolean inNeedOfUpdate = false; // are there any passwords that need
					    // to be encrypted?

    private List<String> lines; // The original file contents

    private EProperties props; // The original properties from the file

    private Map<String, ConfigurationPassword> configurationPasswords; // a list
								       // of
								       // passwords
								       // that
								       // appear
								       // in the
								       // file

    private static final String PASSWORD_LINE_PATTERN_STRING = "^[a-zA-Z_\\-0-9.]*\\."
	    + ConfigConstants.GENERIC_PASSWORD_PROPERTY_SUFFIX + "=.*";

    private static final String PASSWORD_ISPLAINTEXT_LINE_PATTERN_STRING = "^[a-zA-Z_\\-0-9.]*\\."
	    + ConfigConstants.GENERIC_PASSWORD_PROPERTY_SUFFIX
	    + "."
	    + ConfigConstants.PASSWORD_ISPLAINTEXT_SUFFIX + "=.*";

    private final List<Character> badChars;

    // TODO: Use Pattern and Matcher
    // private Pattern passwordLinePattern =
    // Pattern.compile(PASSWORD_LINE_PATTERN_STRING);

    /**
     * Construct a new ConfigurationFile given the file path. Load up a
     * Properties object, and the lines array.
     *
     * @param configFilePath
     *            the path to the config file.
     */
    public ConfigurationFile(String configFilePath) {
	file = new File(configFilePath);
	if (!file.exists()) {
	    String msg = "Configuration file: " + configFilePath
		    + " does not exist";
	    log.error(msg);
	    // A ConfigurationManager test depends on this message:
	    throw new IllegalArgumentException("File DNE @: " + configFilePath);
	}
	if (!file.canRead()) {
	    String msg = "Configuration file: " + configFilePath
		    + " is not readable";
	    log.error(msg);
	    throw new IllegalArgumentException(msg);
	}
	props = new EProperties();
	InputStream is = null;
	try {
	    is = new FileInputStream(file);
	    props.load(is);
	} catch (Exception e) {
	    String msg = "Error loading properties from file: "
		    + file.getAbsolutePath() + ": " + e.getMessage();
	    log.error(msg);
	    throw new IllegalArgumentException(msg);
	}

	try {
	    is.close();
	} catch (IOException e) {
	    String msg = "Error closing file: " + file.getAbsolutePath() + ": "
		    + e.getMessage();
	    log.error(msg);
	    throw new IllegalArgumentException(msg);
	}

	try {
	    lines = FileUtils.readLines(file, "UTF-8");
	} catch (IOException e) {
	    String msg = "Error reading file: " + file.getAbsolutePath() + ": "
		    + e.getMessage();
	    log.error(msg);
	    throw new IllegalArgumentException(msg);
	}
	configurationPasswords = loadConfigurationPasswords();
	inNeedOfUpdate = hasPasswordsNeedingEncryptionOrPropertyUpdate();

	badChars = Arrays.asList(Password.PROPERTY_VALUE_PROBLEMATIC_CHARS);
    }

    /**
     * Load the config file's properties into the given Properties object.
     *
     * @param targetProps
     */
    public void copyProperties(Properties targetProps) {
	targetProps.putAll(props);
    }

    /**
     * Updates the file on disk by encrypting all passwords that should be
     * encrypted. Passwords with *.password.isencrypted=false are not encrypted.
     *
     * @throws Exception
     */
    private List<String> encryptPasswords() throws Exception {
	List<String> updatedLines = new ArrayList<String>(lines.size() + 10);
	for (String line : lines) {
	    if (isPasswordIsPlainTextLine(line)) {
		continue; // omit *.password.isplaintext= lines from output
	    }
	    if (isPasswordLine(line)) {
		// TODO: It's overkill to re-create the ConfigurationPassword;
		// all you really need is the property name
		// then can look it up in the map. Maybe factor out the parsing
		// of the property name?
		// IF the psw lazy-loaded, then it wouldn't matter so much
		ConfigurationPassword psw = ConfigurationPassword
			.createFromLine(props, line);
		if (psw.isInNeedOfEncryption()) {
		    String encryptedLine = null;
		    try {
			// In file, backslashes must be escaped (with a
			// backslash)
			encryptedLine = psw.getPropertyName() + "="
				+ escape(psw.getEncrypted());
		    } catch (Exception e) {
			log.error("Error encrypting passwords in file: "
				+ file.getAbsolutePath() + ": "
				+ e.getMessage());
			throw e;
		    }
		    updatedLines.add(encryptedLine);

		} else {
		    updatedLines.add(line); // if no encryption needed: just
					    // leave the original line
		}
		if (psw.isInNeedOfNewEncryptionProperty()) {
		    String value = psw.isEncryptedAfterUpgrade() ? "true"
			    : "false";
		    updatedLines.add(psw.getPropertyName() + "."
			    + ConfigConstants.PASSWORD_ISENCRYPTED_SUFFIX + "="
			    + value);
		}
	    } else {
		updatedLines.add(line);
	    }
	}
	return updatedLines;
    }

    /**
     * Escape the given string, escaping any backslash characters with another
     * backslash
     *
     * @param s
     * @return
     */
    private String escape(String s) {

	byte[] bufferIn = s.getBytes();
	StringBuilder sb = new StringBuilder();
	for (int bufferInIndex = 0; bufferInIndex < bufferIn.length; bufferInIndex++) {

	    Character c = new Character(s.charAt(bufferInIndex));
	    if (badChars.contains(c)) {
		sb.append('\\');
	    }
	    sb.append(c);
	}

	String escapedString = sb.toString();
	return escapedString;
    }

    /**
     * Save the file, encrypting passwords that need it. Does not change the
     * state of this object (lines and props continue to contain the original
     * file contents).
     *
     * @return
     * @throws IllegalArgumentException
     *             to avoid the need to change ConfigurationManager too much.
     */
    public List<String> saveWithEncryptedPasswords() {
	if (!isInNeedOfUpdate()) {
	    return null;
	}
	log.info("Updating configuration file " + file.getAbsolutePath()
		+ "; encrypting passwords and adjusting password properties.");
	List<String> updatedLines = null;
	try {
	    updatedLines = encryptPasswords();
	} catch (Exception e) {
	    String msg = "Error encrypting passwords for file: "
		    + file.getAbsolutePath() + ": " + e.getMessage();
	    log.error(msg);
	    throw new IllegalArgumentException(msg);
	}
	try {
	    FileUtils.writeLines(file, updatedLines);
	} catch (IOException e) {
	    String msg = "Error saving file: " + file.getAbsolutePath() + ": "
		    + e.getMessage();
	    log.error(msg);
	    throw new IllegalArgumentException(msg);
	}
	return updatedLines;
    }

    /**
     * Get the original file contents.
     *
     * @return
     */
    List<String> getLines() {
	return lines;
    }

    /**
     * Is this a *.password= line?
     *
     * @param line
     * @return
     */
    private boolean isPasswordLine(String line) {
	return line.matches(PASSWORD_LINE_PATTERN_STRING);
    }

    private boolean isPasswordIsPlainTextLine(String line) {
	return line.matches(PASSWORD_ISPLAINTEXT_LINE_PATTERN_STRING);
    }

    /**
     * Get the list of passwords (and metadata) contained in this file
     *
     * @return
     */
    private Map<String, ConfigurationPassword> loadConfigurationPasswords() {
	Map<String, ConfigurationPassword> configurationPasswords = new HashMap<String, ConfigurationPassword>(
		8);
	for (String line : lines) {
	    if (isPasswordLine(line)) {
		// This is a *.password= line; does it need encrypting?
		ConfigurationPassword psw = ConfigurationPassword
			.createFromLine(props, line);
		configurationPasswords.put(psw.getPropertyName(), psw);
	    }
	}
	return configurationPasswords;
    }

    /**
     * Determine whether this file needs any password-related changes. Either
     * passwords that need encrypting or password meta-property changes. It
     * turns out the latter test also covers obsolete meta-property removal,
     * because if that's true, then "needs a new encryption property" is also
     * true.
     *
     * @return
     */
    private boolean hasPasswordsNeedingEncryptionOrPropertyUpdate() {
	for (String pswPropertyName : configurationPasswords.keySet()) {
	    ConfigurationPassword psw = configurationPasswords
		    .get(pswPropertyName);
	    if (psw.isInNeedOfEncryption()
		    || psw.isInNeedOfNewEncryptionProperty()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isInNeedOfUpdate() {
	return inNeedOfUpdate;
    }

}
