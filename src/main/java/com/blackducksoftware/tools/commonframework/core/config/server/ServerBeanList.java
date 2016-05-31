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
package com.blackducksoftware.tools.commonframework.core.config.server;

import java.util.ArrayList;
import java.util.List;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Class that holds the list of server objects (used by parsers to generate
 * implicit lists)
 * 
 * @author akamen
 *
 */
@XStreamAlias(ConfigConstants.SERVERS_PROPERTY)
public class ServerBeanList extends ConfigConstants {

    /** The server list. */
    @XStreamImplicit(itemFieldName = SERVER_PROPERTY)
    private List<ServerBean> serverList = new ArrayList<ServerBean>();

    /**
     * Instantiates a new server bean list.
     */
    public ServerBeanList() {

    }

    /**
     * Adds the.
     *
     * @param bean
     *            the bean
     */
    public void add(ServerBean bean) {
	serverList.add(bean);
    }

    /**
     * Gets the servers.
     *
     * @return the servers
     */
    public List<ServerBean> getServers() {
	return serverList;
    }

    /**
     * Sets the servers.
     *
     * @param serverList
     *            the new servers
     */
    public void setServers(List<ServerBean> serverList) {
	this.serverList = serverList;
    }
}
