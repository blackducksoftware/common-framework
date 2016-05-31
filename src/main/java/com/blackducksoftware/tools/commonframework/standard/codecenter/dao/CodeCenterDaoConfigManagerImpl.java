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

    @Override
    @Deprecated
    public int getEstNumApps() {
        return estNumApps;
    }

    @Override
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

    @Override
    @Deprecated
    public String getCcDbServerName() {
        return ccDbServerName;
    }

    @Override
    @Deprecated
    public void setCcDbServerName(String dbServer) {
        ccDbServerName = dbServer;
    }

    @Override
    @Deprecated
    public int getCcDbPort() {
        return ccDbPort;
    }

    @Override
    @Deprecated
    public void setCcDbPort(int dbPort) {
        ccDbPort = dbPort;
    }

    @Override
    @Deprecated
    public String getCcDbUserName() {
        return ccDbUser;
    }

    @Override
    @Deprecated
    public void setCcDbUserName(String dbUser) {
        ccDbUser = dbUser;
    }

    @Override
    @Deprecated
    public String getCcDbPassword() {
        return ccDbPassword;
    }

    @Override
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
