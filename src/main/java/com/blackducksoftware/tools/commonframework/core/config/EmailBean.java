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
