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

package com.blackducksoftware.tools.commonframework.core.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.core.config.server.ServerBean;
import com.blackducksoftware.tools.commonframework.core.config.server.ServerConfigurationParser;
import com.blackducksoftware.tools.commonframework.core.config.user.CommonUser;
import com.blackducksoftware.tools.commonframework.standard.protex.report.template.TemplateReader;

/**
 * Abstract ConfigManager that either: <li>Loads basic properties used for most
 * projects. or <li>Accepts a user object with the necessary populated fields
 *
 * @author Ari Kamen
 *
 */
public abstract class ConfigurationManager extends ConfigConstants implements IConfigurationManager {
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * For parsing, processing and retaining server beans and lists
	 */
	private ServerConfigurationParser serverConfigParser;

	private ProxyBean proxyBean;

	private SSOBean ssoBean;

	private String serverListLocation;

	// List of all the possible connections
	private List<ServerBean> serverList = new ArrayList<ServerBean>();

	// Map of connections, used in the event where multipl connections are
	// specified in one config file.
	private final Map<APPLICATION, ServerBean> serverMap = new HashMap<APPLICATION, ServerBean>();

	private EmailBean emailConfiguration;

	private NotificationRulesConfig notificationRulesConfig;

	// Derived from either the user or the API wrapper
	/** The server url. */
	private String serverURL;

	private CommonUser user;

	/** The sdk time out. */
	private Long sdkTimeOut;

	/**
	 * CXF child element override
	 */
	private Long childElementCount;

	// Template mappings
	private final Map<String, String> mappings = new HashMap<String, String>();

	/** The props. */
	private ConfigurationProperties props = new ConfigurationProperties();

	// IF the config is read from a file, the following two props
	// will be set, and the file may be modified, encrypting
	// passwords. See ConfigurationFile class header comment
	// for more details.
	// The location of this configuration file
	private String configFileLocation;

	private ConfigurationFile configFile;

	/**
	 * Selects a component of a URL.
	 */
	public static enum URL_INFORMATION {
		/** The host. */
		HOST, /** The port. */
		PORT, /** The protocol. */
		PROTOCOL
	};

	/**
	 * Default constructor. Used by utilities that set all options via the
	 * command line.
	 */
	protected ConfigurationManager() {
	}

	/**
	 * Extensible to provide custom properties. Use {@link}
	 * {@link #getProperty(String)} method. Deprecated: Application Type no
	 * longer needed
	 *
	 * @param configFileLocation
	 *            the config file location
	 * @param applicationType
	 *            the application type {@link ConfigConstants.APPLICATION}
	 *
	 */
	@Deprecated
	protected ConfigurationManager(final String configFileLocation, final APPLICATION applicationName) {
		this.configFileLocation = configFileLocation;
		loadPropertiesFromFile(configFileLocation);
		initApplication();
	}

	/**
	 * Extensible to provide custom properties. Use {@link}
	 * {@link #getProperty(String)} method.
	 *
	 * @param configFileLocation
	 */
	protected ConfigurationManager(final String configFileLocation) {
		this.configFileLocation = configFileLocation;
		loadPropertiesFromFile(configFileLocation);
		initApplication();
	}

	protected ConfigurationManager(final File configFile) {
		this.configFileLocation = configFile.getAbsolutePath();
		loadPropertiesFromFile(configFile);
		initApplication();
	}

	/**
	 * Instantiates a new configuration manager.
	 *
	 * This constructor should be used only by tests, since it introduces some
	 * risk of incorrect handling of special characters and escaped characters.
	 *
	 * @param props
	 *            the props
	 * @param applicationType
	 *            the application type {@link ConfigConstants.APPLICATION}
	 */
	protected ConfigurationManager(final Properties props, final APPLICATION applicationName) {
		this.props = new ConfigurationProperties(); // start clean just in case
		this.props.addAll(props);
		initApplication();
	}

	/**
	 * Initializer for stream instead of specific file Used when file's
	 * classpath is not obvious (web context).
	 *
	 * Deprecated: Use ConfigrationManger(File file) instead. The underlying
	 * properties library (Apache Commons Configuration) cannot load from an
	 * InputStream, so this constructor introduces extra processing of property
	 * values as they are read, and therefore a slight risk of inconsistent
	 * handling of escaped characters.
	 *
	 * @param is
	 *            the is
	 * @param applicationType
	 *            the application type {@link ConfigConstants.APPLICATION}
	 */
	@Deprecated
	protected ConfigurationManager(final InputStream is, final APPLICATION applicationName) {
		loadPropertiesFromStream(is);
		initApplication();
	}

	/**
	 * CommonUser based instantiation Used by those applications that do not
	 * have a property file.
	 *
	 * @param user
	 * @param applicationName
	 */
	protected ConfigurationManager(final CommonUser user, final APPLICATION applicationName) {
		this.user = user;
		initApplication();
	}

	/**
	 * Load properties from file.
	 *
	 * @param configFileLocation
	 *            the config file location
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void loadPropertiesFromFile(final String configFileLocation) {
		configFile = new ConfigurationFile(configFileLocation);
		initProperties();
	}

	private void loadPropertiesFromFile(final File configFile) {
		this.configFile = new ConfigurationFile(configFile);
		initProperties();
	}

	private void initProperties() {
		props = this.configFile.getProperties();
		configFile.saveWithEncryptedPasswords();
	}

	/**
	 * Load properties from stream.
	 *
	 * @param is
	 *            the is
	 */
	private void loadPropertiesFromStream(final InputStream is) {
		final Properties tempProps = new Properties();
		try {
			tempProps.load(is);
			is.close();
		} catch (final IOException e) {
			throw new IllegalArgumentException("Unable to load properties from stream!");
		}
		props.addAll(tempProps);
	}

	/**
	 * Initializes all the properties depending on APPLICATION name
	 */
	private void initApplication() {
		initCommonProperties();
		final boolean processedList = processServerListConfiguration();

		// For all possible conection types check to see if we can find
		// something

		for (final APPLICATION appType : APPLICATION.values()) {
			final ServerBean bean = processServerBeanConfiguration(appType, processedList);
			if (bean != null) {
				serverMap.put(appType, bean);
			}
		}

		if (serverMap.keySet().size() == 0) {
			throw new RuntimeException("No Suitable connections found");
			// loadAppSpecificProperties(suppliedAppNamePropertyName);
		}

	}

	/**
	 * Creates a server bean (or multiple) and populates the server bean map. If
	 * the user specified a known application type (cc, protex, hub, etc) then
	 * we add it to the map. If no suitable connections found, throw a fatal.
	 *
	 * @param appType
	 * @param processedList
	 */
	private ServerBean processServerBeanConfiguration(final APPLICATION appType, final boolean processedList) {
		ServerBean sb = null;

		// If there is a processed list, then attempt to search by application
		// type
		if (processedList) {
			final List<ServerBean> filteredList = getServerListByApplication(appType);
			if (filteredList.size() == 0) {
				log.warn("Server list processed, but no configuration for application type: " + appType);
			} else {
				sb = filteredList.get(0);
				return sb;
			}
		}

		if (user != null) {
			// Check if user object exists
			sb = new ServerBean(user.getServer(), user.getUserName(), user.getPassword(), appType);
		} else {
			// If not, then populate the serverbean
			sb = createAppSpecificServerBean(appType);
		}

		if (sb == null) {
			log.debug("No configuration information for: " + appType);
		} else {
			log.debug("Server configuration available for: " + appType);
		}

		return sb;

	}

	/**
	 * Optional properties that are end point agnostic.
	 *
	 * @param suppliedAppNamePropertyName
	 */
	protected void initCommonProperties() {
		// Potential serverList
		setServerListLocation(getOptionalProperty(SERVER_LIST_LOCATION));

		// Optional email
		emailConfiguration = new EmailBean();
		emailConfiguration.setSmtpAddress(getOptionalProperty(EMAIL_SMTP_ADDRESS));
		emailConfiguration.setSmtpTo(getOptionalProperty(EMAIL_SMTP_TO_FIELD));
		emailConfiguration.setSmtpFrom(getOptionalProperty(EMAIL_SMTP_FROM_FIELD));
		emailConfiguration.setUseAuth(getOptionalProperty(EMAIL_SMTP_USE_AUTH, false, Boolean.class));

		notificationRulesConfig = new NotificationRulesConfig(
				getOptionalProperty(EMAIL_TRIGGER_RULES, "", String.class));

		// The messy work of interpreting the possibly-plain-text,
		// possibly-encrypted, possibly-base64-encoded password
		// is delegated to ConfigurationPassword
		final ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(
				props.getProperties(),
				EMAIL_AUTH_PASSWORD_PREFIX);
		emailConfiguration.setAuthPassword(configurationPassword.getPlainText());

		emailConfiguration.setAuthUserName(getOptionalProperty(EMAIL_SMTP_AUTH_LOGIN, "", String.class));
		emailConfiguration.setEmailProtocol(getOptionalProperty(EMAIL_PROTOCOL, "smtp", String.class));
		emailConfiguration.setSmtpPort(getOptionalProperty(EMAIL_SMTP_PORT, 25, Integer.class));

		// This could go into a separate ProxyObject
		proxyBean = new ProxyBean();
		proxyBean.setProxyServer(getOptionalProperty(PROXY_SERVER));
		proxyBean.setProxyPort(getOptionalProperty(PROXY_PORT));
		proxyBean.setProxyServerHttps(getOptionalProperty(PROXY_HTTPS_SERVER));
		proxyBean.setProxyPortHttps(getOptionalProperty(PROXY_HTTPS_PORT));

		// Optional SSO related Information
		ssoBean = new SSOBean();
		ssoBean.setAuthenticationMethods(getOptionalProperty(SSOBean.SSO_AUTH_METHODS));
		ssoBean.setKeyStorePassword(getOptionalProperty(SSOBean.SSO_KEY_STORE_PASSWORD));
		ssoBean.setKeyStorePath(getOptionalProperty(SSOBean.SSO_KEY_STORE_PATH));
		ssoBean.setKeyStoreType(getOptionalProperty(SSOBean.SSO_KEY_STORE_TYPE));
		ssoBean.setTrustStorePath(getOptionalProperty(SSOBean.SSO_TRUST_STORE_PATH));
		ssoBean.setTrustStorePassword(getOptionalProperty(SSOBean.SSO_TRUST_STORE_PASSWORD));
		ssoBean.setTrustStoreType(getOptionalProperty(SSOBean.SSO_TRUST_STORE_TYPE));

		// Optional child element overrwrite
		final Integer childCount = getOptionalProperty(SDK_CHILD_COUNT, 50000, Integer.class);
		log.debug("Setting CXF timeout: " + childCount);
		setChildElementCount(new Long(childCount));
	}

	/**
	 * Based on specified application name we will extract the necessary
	 * properties
	 */
	private ServerBean createAppSpecificServerBean(final APPLICATION suppliedApplication) {
		String propertyPrefix;
		String userName;
		String server;
		// We want to perform a small translation here. Most users 'cc' as a
		// prefix, so we map it here.
		if (suppliedApplication == APPLICATION.CODECENTER) {
			propertyPrefix = "cc";
		} else {
			propertyPrefix = suppliedApplication.toString().toLowerCase();
		}

		try {
			userName = getProperty(propertyPrefix + "." + GENERIC_USER_NAME_PROPERTY_SUFFIX);
			server = getProperty(propertyPrefix + "." + GENERIC_SERVER_NAME_PROPERTY_SUFFIX);
		} catch (final Exception e) {
			log.info("No connection information available for: " + suppliedApplication);
			return null;
		}

		// The messy work of interpreting the possibly-plain-text,
		// possibly-encrypted, possibly-base64-encoded password
		// is delegated to ConfigurationPassword
		final ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(
				props.getProperties(),
				propertyPrefix);

		final ServerBean serverBean = new ServerBean(server, userName, configurationPassword.getPlainText(),
				suppliedApplication);

		log.debug("Configured custom server bean: " + serverBean);

		return serverBean;
	}

	/**
	 * Returns a list of ServerBeans based on Application Name only
	 *
	 * @param appName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ServerBean> getServerListByApplication(final APPLICATION appName) throws IllegalArgumentException {
		if (serverConfigParser == null || serverList.isEmpty()) {
			throw new IllegalArgumentException("Cannot retrieve server beans without proper server list processing!");
		}
		return serverConfigParser.getServerListByApplication(appName);
	}

	/**
	 * If the user provides a server list, process it here.
	 *
	 * @return the list
	 */
	protected boolean processServerListConfiguration() {
		final String serverListLocationStr = getServerListLocation();
		if (serverListLocationStr == null) {
			log.warn("Server List location property exists, but empty");
			return false;
		}

		try {
			File serverListLocation = null;

			if (configFileLocation == null) {
				log.warn("Loading server list using class loader");
				serverListLocation = new File(this.getClass().getClassLoader().getResource(serverListLocationStr)
						.getFile());
			} else {
				log.info("Loading server configuration relative to configuration file: " + configFileLocation);
				final File configFileLocationPath = new File(configFileLocation);
				serverListLocation = new File(configFileLocationPath.getParent() + File.separator
						+ serverListLocationStr);
			}

			if (serverListLocation.exists()) {
				serverConfigParser = new ServerConfigurationParser(serverListLocation.toString());
				try {
					serverList = serverConfigParser.processServerConfiguration();
					return true;
				} catch (final Exception e) {
					log.error("Unable to parse server file", e);
					throw new IllegalArgumentException("Unable to parse server file!" + e.getMessage());
				}
			} else {
				throw new IllegalArgumentException("Server list location does not exist:" + serverListLocationStr);
			}
		} catch (final Exception e) {
			throw new IllegalArgumentException("Unable to load server list file", e);
		}

	}

	/**
	 * Returns the value for the property resource loaded by the
	 * ConfigurationManager.
	 *
	 * @param propertyKey
	 *            the property key
	 * @return the property
	 */
	@Override
	public String getProperty(final String propertyKey) {
		String value = null;

		if (props.containsKey(propertyKey)) {
			value = props.getProperty(propertyKey);
			value = value.trim();
			if (value == null || value.length() == 0) {
				throw new IllegalArgumentException("Value DNE for key: " + propertyKey);
			}

			value = value.trim();
		} else {
			throw new IllegalArgumentException("Property key DNE: " + propertyKey);
		}

		return value;
	}

	/**
	 * Check to make sure this UNsupported (obsolete) property is not set. If it
	 * is set, throw an IllegalArgumentException.
	 *
	 * @param propertyKey
	 *            the property key
	 * @return the property
	 */
	public void checkForUnsupportedProperty(final String propertyKey, final String newConfigMessage) {

		if (props.containsKey(propertyKey)) {
			throw new IllegalArgumentException("Property key: " + propertyKey + " is no longer supported. "
					+ newConfigMessage + ". Please refer to the documentation for the new configuration instructions.");
		}
	}

	/**
	 * Retrieves optional properties and sets them to the config.
	 *
	 * @param propertyKey
	 *            the property key
	 * @return the optional property
	 */
	@Override
	public String getOptionalProperty(final String propertyKey) {
		String value = null;

		if (props.containsKey(propertyKey)) {
			value = props.getProperty(propertyKey);
			value = value.trim();
		}

		if (value == null) {
			log.debug("[Optional] property: " + propertyKey + ", is missing or blank");
		}
		return value;
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOptionalProperty(final String propertyKey, final T defaultValue, final Class<T> theClass) {
		final T classType = theClass.cast(defaultValue);
		String value = null;
		T returnValue = null;

		if (props.containsKey(propertyKey)) {
			String propStr = props.getProperty(propertyKey);

			if (propStr != null) {
				propStr = propStr.trim();
			}

			value = propStr;
		}

		if (value == null) {
			value = defaultValue.toString();
		}

		if (classType instanceof Boolean) {
			returnValue = (T) new Boolean(value);
		} else if (classType instanceof Integer) {
			returnValue = (T) new Integer(value);
		} else {
			returnValue = (T) new String(value);
		}

		return returnValue;
	}

	/**
	 * Consumes user specified mappings from the property file. User must have
	 * "template.mapping.x=column,value" specified for this to be populated. <br>
	 * Used by {@link TemplateReader}
	 *
	 * @return the mappings
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	@Override
	public Map<String, String> getMappings() throws IllegalArgumentException {

		@SuppressWarnings("unchecked")
		final
		Set<Object> keys = props.keySet();
		for (final Object o : keys) {
			final String key = (String) o;
			if (key.startsWith(PROPERTY_TEMPLATE_MAPPING)) {
				final String mappingPair = props.getProperty(key);
				if (mappingPair != null) {
					final String[] keyValuePair = mappingPair.split(",");
					if (keyValuePair.length == 2) {
						final String columnKey = keyValuePair[0].trim();
						final String mappingMethod = keyValuePair[1].trim();

						// Check to make sure that if the Column exists, the
						// method is the same
						// Otherwise throw Exception alerting the user.
						// TODO: Figure out a way to allow user to map same
						// column names to different methods.
						if (mappings.containsKey(columnKey)) {
							final String existingMethod = mappings.get(columnKey);
							if (!mappingMethod.equals(existingMethod)) {
								throw new IllegalArgumentException(columnKey
										+ " is mapped more than once to non-unique methods.");
							}
						}

						mappings.put(columnKey, mappingMethod);

						log.debug("Added value mapping for key" + mappingPair);
					}
				}
			}
		}

		return mappings;
	}

	/**
	 * Gets the sdk time out.
	 *
	 * @return the sdk time out
	 */
	@Override
	public Long getSdkTimeOut() {
		return sdkTimeOut;
	}

	/**
	 * Sets the sdk time out.
	 *
	 * @param sdkTimeOut
	 *            the new sdk time out
	 */
	@Override
	public void setSdkTimeOut(final Long sdkTimeOut) {
		this.sdkTimeOut = sdkTimeOut;
	}

	/**
	 * Provides information from the server URL.
	 *
	 * @param urlInfo
	 *            the url info
	 * @return the string
	 */
	@Override
	public String findURLInformation(final URL_INFORMATION urlInfo) {
		String returnString = null;
		try {
			final URIBuilder builder = new URIBuilder(serverURL);

			if (urlInfo == URL_INFORMATION.HOST) {
				returnString = builder.getHost();
			} else if (urlInfo == URL_INFORMATION.PORT) {
				returnString = Integer.toString(builder.getPort());
			} else if (urlInfo == URL_INFORMATION.PROTOCOL) {
				returnString = builder.getScheme();
			}
		} catch (final Exception e) {
			log.warn("Unable to determine host name for: " + serverURL, e);
		}

		return returnString;
	}

	/**
	 * Fully resolved URL, used to instantiate SDK Proxy Objects.
	 *
	 * @return the server url
	 */
	@Override
	public String getServerURL() {
		return serverURL;
	}

	/**
	 * Sets the internal server URL so that it can determine host and port
	 * information.
	 *
	 * @param serverURL
	 *            the new server url
	 */
	@Override
	public void setServerURL(final String serverURL) {
		this.serverURL = serverURL;
	}

	/**
	 * Gets the props.
	 *
	 * @return the props
	 */
	@Override
	public Properties getProps() {
		return props.getProperties();
	}

	/**
	 * Gets the server list location.
	 *
	 * @return the server list location
	 */
	private String getServerListLocation() {
		return serverListLocation;
	}

	/**
	 * Sets the server list location.
	 *
	 * @param serverListLocation
	 *            the new server list location
	 */
	private void setServerListLocation(final String serverListLocation) {
		this.serverListLocation = serverListLocation;
	}

	@Override
	public ServerBean getServerBean(final APPLICATION app) {
		return serverMap.get(app);
	}

	/**
	 * Deprecated, use serverSerbean(bean, APPLICATION)
	 *
	 * @param serverBean
	 */

	@Override
	public List<ServerBean> getServerList() {
		return serverList;
	}

	/**
	 * Allows the user to add server beans Useful in the case of a non-standard
	 * configuration.
	 *
	 * @param bean
	 */
	@Override
	public void addServerBean(final ServerBean bean) {
		if (serverList != null) {
			serverList.add(bean);
			log.debug("Added bean : " + bean.toString());
			log.debug("Server list size: " + serverList.size());
		}
	}

	// /// EMAIL ////

	/**
	 * Returns the email configuration object. This contains the required email
	 * protocol information - SMTP address - SMTP TO - SMTP From
	 *
	 * @return
	 */
	@Override
	public EmailBean getEmailConfiguration() {
		return emailConfiguration;
	}

	public NotificationRulesConfig getNotificationRulesConfiguration() {
		return notificationRulesConfig;
	}

	public Long getChildElementCount() {
		return childElementCount;
	}

	public void setChildElementCount(final Long childElementCount) {
		this.childElementCount = childElementCount;
	}

	// SSO
	public SSOBean getSsoBean() {
		return ssoBean;
	}

	// Proxy
	public ProxyBean getProxyBean() {
		return proxyBean;
	}

	/**
	 * Set the proxy bean explicitly, use only if Proxy settings are not being
	 * derived by way of inputstream.
	 *
	 * @param pb
	 */
	public void setProxyBean(final ProxyBean pb) {
		this.proxyBean = pb;
	}

}
