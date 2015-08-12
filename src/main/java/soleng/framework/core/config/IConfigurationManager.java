/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.core.config;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import soleng.framework.core.config.ConfigConstants.APPLICATION;
import soleng.framework.core.config.ConfigurationManager.URL_INFORMATION;
import soleng.framework.core.config.server.ServerBean;

/**
 * An interface representing ConfigurationManager functionality.
 * When we need to create an interface with additional config mgr functionality (such
 * as soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager), that interface can extend this one.
 */
public interface IConfigurationManager {

	/**
	 * Returns a list of ServerBeans based on Application Name only
	 * 
	 * @param appName
	 * @return
	 * @throws Exception
	 */
	public List<ServerBean> getServerListByApplication(APPLICATION appName)
			throws IllegalArgumentException;

	/**
	 * Returns the value for the property resource loaded by the
	 * ConfigurationManager.
	 * 
	 * @param propertyKey
	 *            the property key
	 * @return the property
	 */
	public String getProperty(String propertyKey);

	/**
	 * Retrieves optional properties and sets them to the config.
	 * 
	 * @param propertyKey
	 *            the property key
	 * @return the optional property
	 */
	public String getOptionalProperty(String propertyKey);

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
	public <T> T getOptionalProperty(String propertyKey, T defaultValue,
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
	public Map<String, String> getMappings() throws IllegalArgumentException;

	/**
	 * Gets the proxy server.
	 * 
	 * @return the proxy server
	 */
	public String getProxyServer();

	/**
	 * Sets the proxy server.
	 * 
	 * @param proxyServer
	 *            the new proxy server
	 */
	public void setProxyServer(String proxyServer);

	/**
	 * Gets the proxy port.
	 * 
	 * @return the proxy port
	 */
	public String getProxyPort();

	/**
	 * Sets the proxy port.
	 * 
	 * @param proxyPort
	 *            the new proxy port
	 */
	public void setProxyPort(String proxyPort);

	/**
	 * Gets the proxy server https.
	 * 
	 * @return the proxy server https
	 */
	public String getProxyServerHttps();

	/**
	 * Sets the proxy server https.
	 * 
	 * @param proxyServerHttps
	 *            the new proxy server https
	 */
	public void setProxyServerHttps(String proxyServerHttps);

	/**
	 * Gets the proxy port https.
	 * 
	 * @return the proxy port https
	 */
	public String getProxyPortHttps();

	/**
	 * Sets the proxy port https.
	 * 
	 * @param proxyPortHttps
	 *            the new proxy port https
	 */
	public void setProxyPortHttps(String proxyPortHttps);

	/**
	 * Gets the sdk time out.
	 * 
	 * @return the sdk time out
	 */
	public Long getSdkTimeOut();

	/**
	 * Sets the sdk time out.
	 * 
	 * @param sdkTimeOut
	 *            the new sdk time out
	 */
	public void setSdkTimeOut(Long sdkTimeOut);

	/**
	 * Provides information from the server URL.
	 * 
	 * @param urlInfo
	 *            the url info
	 * @return the string
	 */
	public String findURLInformation(URL_INFORMATION urlInfo);

	/**
	 * Fully resolved URL, used to instantiate SDK Proxy Objects.
	 * 
	 * @return the server url
	 */
	public String getServerURL();

	/**
	 * Sets the internal server URL so that it can determine host and port
	 * information.
	 * 
	 * @param serverURL
	 *            the new server url
	 */
	public void setServerURL(String serverURL);

	/**
	 * Gets the props.
	 * 
	 * @return the props
	 */
	public Properties getProps();

	public APPLICATION getApplicationType();

	public void setApplicationType(APPLICATION applicationName);

	/**
	 * Returns this ConfigurationManager's server bean. This may either be the
	 * explicitly configured application_name.property_name settings <br>
	 * or the first element the server.list configuration.
	 * 
	 * @return
	 */
	public ServerBean getServerBean();

	public List<ServerBean> getServerList();

	/**
	 * Allows the user to add server beans Useful in the case of a non-standard
	 * configuration.
	 * 
	 * @param bean
	 */
	public void addServerBean(ServerBean bean);

	/**
	 * Returns the email configuration object.
	 * This contains the required email protocol information 
	 *  - SMTP address
	 *  - SMTP TO
	 *  - SMTP From
	 * @return
	 */
	public EmailBean getEmailConfiguration();

}