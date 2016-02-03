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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBeanList;
import com.thoughtworks.xstream.XStream;

/**
 * The Class XMLParser.
 */
public class XMLParser implements IGenericServerParser {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public List<ServerBean> processServerConfiguration(FileReader fileReader) {
	ServerBeanList serverBeanList = null;

	XStream xstream = new XStream();

	try {
	    xstream.processAnnotations(ServerBean.class);
	    xstream.processAnnotations(ServerBeanList.class);

	    serverBeanList = (ServerBeanList) xstream.fromXML(fileReader);

	    log.debug("Deserialized XML");

	} catch (Exception e) {
	    log.error("Unable to process XML", e);
	}

	return serverBeanList.getServers();
    }

}
