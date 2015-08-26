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
     * Gets the proxy server.
     *
     * @return the proxy server
     */
    String getProxyServer();

    /**
     * Sets the proxy server.
     *
     * @param proxyServer
     *            the new proxy server
     */
    void setProxyServer(String proxyServer);

    /**
     * Gets the proxy port.
     *
     * @return the proxy port
     */
    String getProxyPort();

    /**
     * Sets the proxy port.
     *
     * @param proxyPort
     *            the new proxy port
     */
    void setProxyPort(String proxyPort);

    /**
     * Gets the proxy server https.
     *
     * @return the proxy server https
     */
    String getProxyServerHttps();

    /**
     * Sets the proxy server https.
     *
     * @param proxyServerHttps
     *            the new proxy server https
     */
    void setProxyServerHttps(String proxyServerHttps);

    /**
     * Gets the proxy port https.
     *
     * @return the proxy port https
     */
    String getProxyPortHttps();

    /**
     * Sets the proxy port https.
     *
     * @param proxyPortHttps
     *            the new proxy port https
     */
    void setProxyPortHttps(String proxyPortHttps);

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

    APPLICATION getApplicationType();

    void setApplicationType(APPLICATION applicationName);

    /**
     * Returns this ConfigurationManager's server bean. This may either be the
     * explicitly configured application_name.property_name settings <br>
     * or the first element the server.list configuration.
     *
     * @return
     */
    ServerBean getServerBean();

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
