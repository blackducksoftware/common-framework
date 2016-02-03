/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.config.server.parser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;
import com.google.gson.Gson;

/**
 * The Class JSONParser.
 */
public class JSONParser extends ConfigConstants implements IGenericServerParser {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public List<ServerBean> processServerConfiguration(FileReader fileReader) {
	List<ServerBean> servers = new ArrayList<ServerBean>();
	Gson gson = new Gson();

	try {
	    ServerBean[] serverArray = gson.fromJson(fileReader,
		    ServerBean[].class);
	    for (ServerBean bean : serverArray) {
		servers.add(bean);
	    }

	} catch (Exception e) {
	    log.error("Unable to parse json", e);
	}

	return servers;
    }

}
