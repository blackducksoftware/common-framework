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
package com.blackducksoftware.tools.commonframework.core.config;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants.APPLICATION;
import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager.URL_INFORMATION;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;
import com.blackducksoftware.tools.commonframework.standard.protex.report.template.TemplateReader;

/**
 * An interface representing ConfigurationManager functionality. When we need to
 * create an interface with additional config mgr functionality (such as
 * com.blackducksoftware
 * .tools.commonframework.standard.codecenter.dao.CodeCenterDaoConfigManager),
 * that interface can extend this one.
 */
public interface IConfigurationManager {

    /**
     * Returns a list of ServerBeans based on Application Name only
     * 
     * @param appName
     * @return
     * @throws Exception
     */
    List<ServerBean> getServerListByApplication(APPLICATION appName)
            throws IllegalArgumentException;

    /**
     * Returns the value for the property resource loaded by the
     * ConfigurationManager.
     * 
     * @param propertyKey
     *            the property key
     * @return the property
     */
    String getProperty(String propertyKey);

    /**
     * Retrieves optional properties and sets them to the config.
     * 
     * @param propertyKey
     *            the property key
     * @return the optional property
     */
    String getOptionalProperty(String propertyKey);

    /**
     * Retrieves optional property, if property is missing returns default
     * value.
     * 
     * @param <T>
     * @param key
     * @param defaultValue
     * @return
     * @return
     */
    <T> T getOptionalProperty(String propertyKey, T defaultValue,
            Class<T> theClass);

    /**
     * Consumes user specified mappings from the property file. User must have
     * "template.mapping.x=column,value" specified for this to be populated. <br>
     * Used by {@link TemplateReader}
     * 
     * @return the mappings
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
    Map<String, String> getMappings() throws IllegalArgumentException;

    /**
     * Gets the sdk time out.
     * 
     * @return the sdk time out
     */
    Long getSdkTimeOut();

    /**
     * Sets the sdk time out.
     * 
     * @param sdkTimeOut
     *            the new sdk time out
     */
    void setSdkTimeOut(Long sdkTimeOut);

    /**
     * Provides information from the server URL.
     * 
     * @param urlInfo
     *            the url info
     * @return the string
     */
    String findURLInformation(URL_INFORMATION urlInfo);

    /**
     * Fully resolved URL, used to instantiate SDK Proxy Objects.
     * 
     * @return the server url
     */
    String getServerURL();

    /**
     * Sets the internal server URL so that it can determine host and port
     * information.
     * 
     * @param serverURL
     *            the new server url
     */
    void setServerURL(String serverURL);

    /**
     * Gets the props.
     * 
     * @return the props
     */
    Properties getProps();

    /**
     * Returns this ConfigurationManager's server bean. This may either be the
     * explicitly configured application_name.property_name settings <br>
     * or the first element the server.list configuration.
     * 
     * @return
     */
    ServerBean getServerBean(APPLICATION app);

    List<ServerBean> getServerList();

    /**
     * Allows the user to add server beans Useful in the case of a non-standard
     * configuration.
     * 
     * @param bean
     */
    void addServerBean(ServerBean bean);

    /**
     * Returns the email configuration object. This contains the required email
     * protocol information - SMTP address - SMTP TO - SMTP From
     * 
     * @return
     */
    EmailBean getEmailConfiguration();

}
