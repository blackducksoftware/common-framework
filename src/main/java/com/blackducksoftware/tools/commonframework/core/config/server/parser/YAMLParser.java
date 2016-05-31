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
