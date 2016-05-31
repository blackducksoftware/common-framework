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
package com.blackducksoftware.tools.commonframework.core.config.user;

import java.io.Serializable;

/**
 * An interface for classes that provide getters/setters for server-specific
 * user credentials.
 */
public interface CommonUser extends Serializable {

    /**
     * Sets the server.
     * 
     * @param servername
     *            the new server
     */
    void setServer(String servername);

    /**
     * Sets the user name.
     * 
     * @param username
     *            the new user name
     */
    void setUserName(String username);

    /**
     * Sets the password.
     * 
     * @param password
     *            the new password
     */
    void setPassword(String password);

    /**
     * Gets the server.
     * 
     * @return the server
     */
    String getServer();

    /**
     * Gets the user name.
     * 
     * @return the user name
     */
    String getUserName();

    /**
     * Gets the password.
     * 
     * @return the password
     */
    String getPassword();
}
