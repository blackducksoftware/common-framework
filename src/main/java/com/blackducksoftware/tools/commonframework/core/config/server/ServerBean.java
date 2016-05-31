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

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Simple bean to hold server information
 * 
 * @author akamen
 *
 */
@XStreamAlias(ConfigConstants.SERVER_PROPERTY)
public class ServerBean extends ConfigConstants {

    /** The server url. */
    @XStreamAlias(ConfigConstants.GENERIC_SERVER_NAME_PROPERTY_SUFFIX)
    private String serverName;

    /** The user name. */
    @XStreamAlias(ConfigConstants.GENERIC_USER_NAME_PROPERTY_SUFFIX)
    private String userName;

    /** The password. */
    @XStreamAlias(ConfigConstants.GENERIC_PASSWORD_PROPERTY_SUFFIX)
    private String password;

    /** Optional Alias */
    @XStreamAlias(ConfigConstants.GENERIC_ALIAS_PROPERTY_SUFFIX)
    private String alias;

    /** The suite type. */
    @XStreamAlias(ConfigConstants.APPLICATION_NAME_PROPERTY)
    private APPLICATION application;

    /**
     * Instantiates a new server bean.
     */
    public ServerBean() {

    }

    /**
     * Creates a plain server bean
     * 
     * @param server
     * @param user
     * @param password
     */
    public ServerBean(String server, String user, String password,
	    APPLICATION appName) {
	setServerName(server);
	setUserName(user);
	setPassword(password);
	setApplication(appName);
    }

    public String getServerName() {
	return serverName;
    }

    public void setServerName(String serverName) {
	this.serverName = serverName;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public APPLICATION getApplication() {
	return application;
    }

    public void setApplication(APPLICATION suiteType) {
	this.application = suiteType;
    }

    public String getAlias() {
	return alias;
    }

    public void setAlias(String alias) {
	this.alias = alias;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("\n");
	sb.append("Server URL: " + getServerName());
	sb.append("\n");
	sb.append("User Name: " + getUserName());
	sb.append("\n");
	sb.append("Application Name: " + getApplication());
	sb.append("\n");
	sb.append("Alias: " + this.getAlias());
	sb.append("\n");

	return sb.toString();
    }
}
