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
package com.blackducksoftware.tools.commonframework.core.config.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.blackducksoftware.tools.commonframework.core.config.server.parser.IGenericServerParser;
import com.blackducksoftware.tools.commonframework.core.config.server.parser.JSONParser;
import com.blackducksoftware.tools.commonframework.core.config.server.parser.XMLParser;
import com.blackducksoftware.tools.commonframework.core.config.server.parser.YAMLParser;
import com.google.gson.JsonParser;

/**
 * The Class ServerConfigurationParser.
 */
public class ServerConfigurationParser {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass()
	    .getName());

    /** The file name. */
    private final String fileName;

    private List<ServerBean> serverList = new ArrayList<ServerBean>();

    /**
     * The Enum FILE_TYPE.
     */
    private enum FILE_TYPE {
	/** The json. */
	JSON,
	/** The xml. */
	XML,
	/** The yaml. */
	YAML
    };

    /**
     * Instantiates a new server configuration parser.
     *
     * @param fileName
     *            the file name
     */
    public ServerConfigurationParser(String fileName) {
	this.fileName = fileName;
    }

    /**
     * Process server configuration.
     *
     * @return the list
     * @throws Exception
     *             the exception
     */
    public List<ServerBean> processServerConfiguration() throws Exception {
	IGenericServerParser serverParser = null;

	try (FileReader fileReader = new FileReader(fileName)) {
	    FILE_TYPE fileType = determineFileType();

	    if (fileType == FILE_TYPE.XML) {
		serverParser = new XMLParser();
	    } else if (fileType == FILE_TYPE.YAML) {
		serverParser = new YAMLParser();
	    } else if (fileType == FILE_TYPE.JSON) {
		serverParser = new JSONParser();
	    }

	    serverList = serverParser.processServerConfiguration(fileReader);
	    fileReader.close();

	} catch (Exception e) {
	    log.error("Unable to process server configuration file", e);
	}

	return serverList;
    }

    public List<ServerBean> getServerListByApplication(
	    ConfigConstants.APPLICATION appName) {
	List<ServerBean> serverListByName = new ArrayList<ServerBean>();

	for (ServerBean bean : serverList) {
	    if (bean.getApplication() == appName) {
		serverListByName.add(bean);
	    }
	}

	return serverListByName;
    }

    /**
     * Determine file type.
     *
     * @return the file type
     */
    private FILE_TYPE determineFileType() {
	// Our default return
	FILE_TYPE returnType = FILE_TYPE.XML;
	try (FileReader fileReader = new FileReader(fileName)) {
	    // Replacing java Files nio package with Tika
	    // Part of https://jira.blackducksoftware.com/browse/SECF-113
	    File f = new File(fileName);
	    Tika tika = new Tika();
	    String fileTypeStr = tika.detect(f);
	    log.debug("Tika detected configuration content as: " + fileTypeStr);
	    if (fileTypeStr != null) {
		if (fileTypeStr
			.contains(FILE_TYPE.XML.toString().toLowerCase())) {
		    returnType = FILE_TYPE.XML;
		}
		// If that did not work, it may be either JSON or YAML
		else if (fileTypeStr.contains(FILE_TYPE.JSON.toString()
			.toLowerCase())) {
		    try {
			new JsonParser().parse(fileReader);
			returnType = FILE_TYPE.JSON;
		    } catch (Exception jsonException) {
			// ignore
		    }
		} else if (fileTypeStr.contains(FILE_TYPE.YAML.toString()
			.toLowerCase())) {
		    // If we are still here it is most likely YAML
		    log.debug("Detected file type: " + fileTypeStr);
		    returnType = FILE_TYPE.YAML;
		}
	    } else {
		// Default, go with XML
		return returnType;
	    }

	} catch (IOException ioe) {
	    throw new IllegalArgumentException("Unable to determine file type",
		    ioe);
	}

	return returnType;
    }

}
