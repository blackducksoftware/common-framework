/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.tools.commonframework.core.exception;

import java.io.Serializable;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants.APPLICATION;
import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;

/**
 * Exception thrown by CommonFramework classes.
 * 
 * @author sbillings
 * 
 */
public class CommonFrameworkException extends Exception implements Serializable {

    private static final long serialVersionUID = -638853136815473675L;

    private ConfigurationManager cf;

    public CommonFrameworkException(String message) {
        super(message);
    }

    public CommonFrameworkException(ConfigurationManager cf, String message) {
        super(message);
        this.cf = cf;
    }

    /**
     * Get a string containing the server and username, if the thrower provided
     * it.
     * 
     * @return
     */
    public String getConfigurationInformation() {
        if (cf == null) {
            return "<server and user information not available>";
        }
        StringBuilder sb = new StringBuilder();

        // TODO: This is obviously not a good way to do this.
        ServerBean server = cf.getServerBean(APPLICATION.PROTEX);
        if (server == null) {
            server = cf.getServerBean(APPLICATION.CODECENTER);
        }
        sb.append("Server: " + server.getServerName());
        sb.append("\n");
        sb.append("User: " + server.getUserName());

        return sb.toString();
    }
}
