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
package com.blackducksoftware.tools.commonframework.core.config.testbeans;

import com.blackducksoftware.tools.commonframework.core.config.user.CommonUser;

/**
 * The Class TestCommonUser.
 */
public class TestCommonUser implements CommonUser {

    /** The server. */
    private String server;

    /** The user name. */
    private String userName;

    /** The password. */
    private String password;

    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #getServer()
     */
    public String getServer() {
	return server;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #setServer(java.lang.String)
     */
    public void setServer(String server) {
	this.server = server;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #getUserName()
     */
    public String getUserName() {
	return userName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #setUserName(java.lang.String)
     */
    public void setUserName(String user) {
	this.userName = user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #getPassword()
     */
    public String getPassword() {
	return password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.core.config.user.CommonUser
     * #setPassword(java.lang.String)
     */
    public void setPassword(String password) {
	this.password = password;
    }

}
