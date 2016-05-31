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
/**
 * 
 */
package com.blackducksoftware.tools.commonframework.core.config;

/**
 * Simple bean holding Proxy details
 * 
 * @author akamen
 * 
 */
public class ProxyBean {

    /** The proxy server. */
    private String proxyServer;

    public String getProxyServer() {
        return proxyServer;
    }

    public void setProxyServer(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyServerHttps() {
        return proxyServerHttps;
    }

    public void setProxyServerHttps(String proxyServerHttps) {
        this.proxyServerHttps = proxyServerHttps;
    }

    public String getProxyPortHttps() {
        return proxyPortHttps;
    }

    public void setProxyPortHttps(String proxyPortHttps) {
        this.proxyPortHttps = proxyPortHttps;
    }

    /** The proxy port. */
    private String proxyPort;

    /** The proxy server https. */
    protected String proxyServerHttps;

    /** The proxy port https. */
    protected String proxyPortHttps;

}
