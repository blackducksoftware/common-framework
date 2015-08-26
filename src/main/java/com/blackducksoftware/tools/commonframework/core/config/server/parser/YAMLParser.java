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
package com.blackducksoftware.tools.commonframework.core.config.server.parser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBeanList;

/**
 * Engine to process the supplied YAML configuration file Returns a list of
 * processed beans, throws run time exception upon failure.
 *
 * NOTE: YAML does not allow for XStream-style annotations. Thus it is currently
 * not possible to map our methods to property names. So the YAML config file
 * must have properties that can be reflectively determined. TODO: Find a way
 * around this if YAML is to be used effectively.
 *
 * @author akamen
 *
 */
public class YAMLParser extends ConfigConstants implements IGenericServerParser {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public List<ServerBean> processServerConfiguration(FileReader fileReader) {
	List<ServerBean> servers = new ArrayList<ServerBean>();
	Yaml yamlLoader = null;

	try {

	    TypeDescription descr = new TypeDescription(ServerBeanList.class);
	    descr.putListPropertyType(ConfigConstants.SERVERS_PROPERTY,
		    ServerBean.class);
	    yamlLoader = new Yaml(new Constructor(descr));
	    ServerBeanList serverList = yamlLoader.loadAs(fileReader,
		    ServerBeanList.class);

	    servers = serverList.getServers();

	} catch (Exception e) {
	    log.error("Configuration pasing error", e);
	    throw new IllegalArgumentException(
		    "Failed to parse configuration file: " + e.getMessage());
	}

	return servers;

    }

}
