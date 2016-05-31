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

import org.apache.commons.lang.StringUtils;

/**
 * Basic email config bean to hold email information, if provided
 *
 * @author Ari Kamen
 * @date Aug 27, 2014
 *
 */
public class EmailBean {
    // These are basic requirements for simple email
    private String smtpAddress = new String();

    private String smtpTo = new String();

    private String smtpFrom = new String();

    // These are optional
    private Boolean useAuth;

    private String authUserName;

    private String authPassword;

    private Integer smtpPort;

    private String emailProtocol;

    public EmailBean() {

    }

    public boolean isEmailConfigured() {
	if (!StringUtils.isEmpty(smtpAddress) && !StringUtils.isEmpty(smtpTo)
		&& !StringUtils.isEmpty(smtpFrom)) {
	    return true;
	} else {
	    return false;
	}
    }

    public String getSmtpAddress() {
	return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
	this.smtpAddress = smtpAddress;
    }

    public String getSmtpTo() {
	return smtpTo;
    }

    public void setSmtpTo(String smtpTo) {
	this.smtpTo = smtpTo;
    }

    public String getSmtpFrom() {
	return smtpFrom;
    }

    public void setSmtpFrom(String smtpFrom) {
	this.smtpFrom = smtpFrom;
    }

    public Boolean isUseAuth() {
	return useAuth;
    }

    public void setUseAuth(Boolean useAuth) {
	this.useAuth = useAuth;
    }

    public String getAuthUserName() {
	return authUserName;
    }

    public void setAuthUserName(String authUserName) {
	this.authUserName = authUserName;
    }

    public String getAuthPassword() {
	return authPassword;
    }

    public void setAuthPassword(String authPassword) {
	this.authPassword = authPassword;
    }

    public Integer getSmtpPort() {
	return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
	this.smtpPort = smtpPort;
    }

    public String getEmailProtocol() {
	return emailProtocol;
    }

    public void setEmailProtocol(String emailProtocol) {
	this.emailProtocol = emailProtocol;
    }

}
