/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/

package com.blackducksoftware.tools.commonframework.standard.codecenter.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;

/**
 * A ConfigurationManager subclass that is consumable by a CodeCenterDao object.
 * It manages a list of application custom attribute names.
 * 
 * @author sbillings
 * 
 */
public class CodeCenterDaoConfigManagerImpl extends ConfigurationManager
	implements CodeCenterDaoConfigManager {

    private final Logger log = LoggerFactory.getLogger(this.getClass()
	    .getName());

    private int estNumApps = 100;

    private final List<String> applicationAttributeNames = new ArrayList<String>(
	    8);

    private String ccDbServerName;

    private int ccDbPort = 0;

    private String ccDbUser;

    private String ccDbPassword;

    private boolean skipNonKbComponents = true;

    public CodeCenterDaoConfigManagerImpl(Properties props) {
	super(props, APPLICATION.CODECENTER);
	loadAdditionalProperties();
	loadApplicationAttributeNames();
    }

    public CodeCenterDaoConfigManagerImpl(String configFilename) {
	super(configFilename, APPLICATION.CODECENTER);
	loadAdditionalProperties();
	loadApplicationAttributeNames();
    }

    private void loadAdditionalProperties() {
	try {
	    ccDbServerName = super
		    .getOptionalProperty("cc.database.server.name");
	    String ccDbPortString = super
		    .getOptionalProperty("cc.database.port");
	    if (ccDbPortString != null) {
		ccDbPort = Integer.parseInt(ccDbPortString);
	    }
	    ccDbUser = super.getOptionalProperty("cc.database.user.name");
	    ccDbPassword = super.getOptionalProperty("cc.database.password");

	    String estNumApplications = super
		    .getOptionalProperty("est.number.applications");
	    if (estNumApplications != null) {
		estNumApps = Integer.parseInt(estNumApplications);
	    }

	    String skipNonKbComponentsString = super
		    .getOptionalProperty("skip.non.kb.components");
	    if ("false".equalsIgnoreCase(skipNonKbComponentsString)) {
		skipNonKbComponents = false;
	    }
	} catch (IllegalArgumentException e) {
	    log.error("A required attribute is missing from the configuration file: "
		    + e.getMessage());
	    throw e;
	}
    }

    private void loadApplicationAttributeNames() {

	String appCustAttrsString = super
		.getOptionalProperty("custom.attributes.application");
	if (appCustAttrsString == null) {
	    return;
	}
	String[] appCustAttrs = appCustAttrsString.split(",\\s*");

	for (String appCustAttr : appCustAttrs) {
	    addApplicationAttribute(appCustAttr);
	}
    }

    @Deprecated
    public int getEstNumApps() {
	return estNumApps;
    }

    @Deprecated
    public void setEstNumApps(int estNumApps) {
	this.estNumApps = estNumApps;
    }

    /**
     * Add an application custom attribute to the list of attributes to track.
     * 
     * @param attrName
     */
    @Override
    public void addApplicationAttribute(String attrName) {
	applicationAttributeNames.add(attrName);
    }

    /**
     * Get the list of names of application custom attributes to track.
     * 
     * @return
     */
    @Override
    public List<String> getApplicationAttributeNames() {
	return applicationAttributeNames;
    }

    @Deprecated
    public String getCcDbServerName() {
	return ccDbServerName;
    }

    @Deprecated
    public void setCcDbServerName(String dbServer) {
	ccDbServerName = dbServer;
    }

    @Deprecated
    public int getCcDbPort() {
	return ccDbPort;
    }

    @Deprecated
    public void setCcDbPort(int dbPort) {
	ccDbPort = dbPort;
    }

    @Deprecated
    public String getCcDbUserName() {
	return ccDbUser;
    }

    @Deprecated
    public void setCcDbUserName(String dbUser) {
	ccDbUser = dbUser;
    }

    @Deprecated
    public String getCcDbPassword() {
	return ccDbPassword;
    }

    @Deprecated
    public void setCcDbPassword(String dbPassword) {
	ccDbPassword = dbPassword;
    }

    /**
     * Get the value of the "should ignore non-KB components" flag.
     * 
     * @return
     */
    @Override
    public boolean isSkipNonKbComponents() {
	return skipNonKbComponents;
    }

}
