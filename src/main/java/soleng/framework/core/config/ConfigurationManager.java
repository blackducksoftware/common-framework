/**
 * 
 */
package soleng.framework.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.jmatrix.eproperties.EProperties;

import java.util.Set;




import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.core.config.server.ServerBean;
import soleng.framework.core.config.server.ServerConfigurationParser;
import soleng.framework.core.config.user.CommonUser;


/**
 * Abstract ConfigManager that either: <li>Loads basic properties used for most
 * projects. or <li>Accepts a user object with the necessary populated fields
 * 
 * @author Ari Kamen
 * 
 */
public abstract class ConfigurationManager extends ConfigConstants implements IConfigurationManager
{
	/** The log. */
    static Logger log = LoggerFactory.getLogger(ConfigurationManager.class);

    /**
     * For parsing, processing and retaining server beans and lists
     */
    private ServerConfigurationParser serverConfigParser = null;
    private APPLICATION applicationType = null;
    private ServerBean serverBean = null;
    private String serverListLocation = null;
    private List<ServerBean> serverList = new ArrayList<ServerBean>();
    private EmailBean emailConfiguration = null;
    
    /**
     * TODO: Refactor out to a separate class
     */
    private String hostName;

    /** The port. */
    private Integer port;
    // Derived from either the user or the API wrapper
    /** The server url. */
    private String serverURL = null;

    private CommonUser user = null;

    /** The proxy server. */
    private String proxyServer = null;

    /** The proxy port. */
    private String proxyPort = null;

    /** The proxy server https. */
    protected String proxyServerHttps = null;

    /** The proxy port https. */
    protected String proxyPortHttps = null;

    /** The sdk time out. */
    private Long sdkTimeOut = null;
    
    /**
     * CXF child element override
     */
    private Long childElementCount = null;
    
    // Template mappings
    /** The mappings. */
    private Map<String, String> mappings = new HashMap<String, String>();

    /** The props. */
    private EProperties props = new EProperties();

    // IF the config is read from a file, the following two props
    // will be set, and the file may be modified, encrypting
    // passwords. See ConfigurationFile class header comment
    // for more details.
    // The location of this configuration file
    private String configFileLocation = null;
    private ConfigurationFile configFile = null;

    /**
     * The Enum URL_INFORMATION.
     */
    public static enum URL_INFORMATION
    {
	/** The host. */
	HOST, /** The port. */
	PORT, /** The protocol. */
	PROTOCOL
    };

    /**
     * Instantiates a new configuration manager.
     */
    protected ConfigurationManager()
    {
    }

    /**
     * Extensible to provide custom properties. Use {@link}
     * {@link #getProperty(String)} method.
     * 
     * @param configFileLocation
     *            the config file location
     * @param applicationType
     *            the application type {@link ConfigConstants.APPLICATION}
     */
    protected ConfigurationManager(String configFileLocation,
	    APPLICATION applicationName)
    {
	this.configFileLocation = configFileLocation;
	this.applicationType = applicationName;
	loadPropertiesFromFile(configFileLocation);
	init();
    }


	/**
     * Instantiates a new configuration manager.
     * 
     * @param props
     *            the props
     * @param applicationType
     *            the application type {@link ConfigConstants.APPLICATION}
     */
    protected ConfigurationManager(Properties props, APPLICATION applicationName)
    {
	this.applicationType = applicationName;
	this.props = new EProperties(); // start clean just in case
	this.props.addAll(props);
	init();
    }

    /**
     * Initializer for stream instead of specific file Used when file's
     * classpath is not obvious (web context).
     * 
     * @param is
     *            the is
     * @param applicationType
     *            the application type {@link ConfigConstants.APPLICATION}
     */
    protected ConfigurationManager(InputStream is, APPLICATION applicationName)
    {
	this.applicationType = applicationName;
	loadPropertiesFromStream(is);
	init();
    }

    /**
     * CommonUser based instantiation Used by those applications that do not
     * have a property file.
     * 
     * @param user
     * @param applicationName
     */
    protected ConfigurationManager(CommonUser user, APPLICATION applicationName)
    {
	this.applicationType = applicationName;
	this.user = user;
	init();
    }

    /**
     * Load properties from file.
     * 
     * @param configFileLocation
     *            the config file location
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    private void loadPropertiesFromFile(String configFileLocation)
    {
    	configFile = new ConfigurationFile(configFileLocation);
    	configFile.copyProperties(props);
    	configFile.saveWithEncryptedPasswords();
    }

    /**
     * Load properties from stream.
     * 
     * @param is
     *            the is
     */
    private void loadPropertiesFromStream(InputStream is)
    {
	try
	{
	    props.load(is);
	    is.close();
	} catch (IOException e)
	{
	    throw new IllegalArgumentException(
		    "Unable to load properties from stream!");
	}
    }

    /**
     * Initializes all the properties depending on APPLICATION name
     */
    private void init()
    {
	String suppliedAppNamePropertyName = "";
	if (applicationType == APPLICATION.CODECENTER)
	    suppliedAppNamePropertyName = ConfigConstants.CODE_CENTER_PREFIX_PROPERTY;
	else if (applicationType == APPLICATION.PROTEX)
	    suppliedAppNamePropertyName = ConfigConstants.PROTEX_PREFIX_PROPERTY;

	initCommonProperties();
	processServerConfigurationInformation();
	loadAppSpecificProperties(suppliedAppNamePropertyName);

    }

    /**
     * Optional properties that are end point agnostic.
     * @param suppliedAppNamePropertyName 
     */
    protected void initCommonProperties()
    {
	// Potential serverList
	setServerListLocation(getOptionalProperty(SERVER_LIST_LOCATION));

	// Optional email
	emailConfiguration = new EmailBean();
	emailConfiguration.setSmtpAddress(getOptionalProperty(EMAIL_SMTP_ADDRESS));
	emailConfiguration.setSmtpTo(getOptionalProperty(EMAIL_SMTP_TO_FIELD));
	emailConfiguration.setSmtpFrom(getOptionalProperty(EMAIL_SMTP_FROM_FIELD));
	emailConfiguration.setUseAuth(getOptionalProperty(EMAIL_SMTP_USE_AUTH, false, Boolean.class));
	emailConfiguration.setTriggerRules(getOptionalProperty(EMAIL_TRIGGER_RULES, "", String.class));
	
	
	// The messy work of interpreting the possibly-plain-text, possibly-encrypted, possibly-base64-encoded password
	// is delegated to ConfigurationPassword
	ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(getProps(), EMAIL_AUTH_PASSWORD_PREFIX);
	emailConfiguration.setAuthPassword(configurationPassword.getPlainText());
	
	emailConfiguration.setAuthUserName(getOptionalProperty(EMAIL_SMTP_AUTH_LOGIN, "", String.class));
	emailConfiguration.setEmailProtocol(getOptionalProperty(EMAIL_PROTOCOL, "smtp", String.class));
	emailConfiguration.setSmtpPort(getOptionalProperty(EMAIL_SMTP_PORT, 25, Integer.class));
	
	// Optional Proxy
	// TODO:  Change this to a ProxyBean just like above
	setProxyServer(getOptionalProperty(PROXY_SERVER));
	setProxyPort(getOptionalProperty(PROXY_PORT));
	proxyServerHttps = getOptionalProperty(PROXY_HTTPS_SERVER);
	proxyPortHttps = getOptionalProperty(PROXY_HTTPS_PORT);

    }

    private void loadAppSpecificProperties(String suppliedAppNamePropertyName)
	    throws IllegalArgumentException
    {

	// Optional Time Out
	String sdkTimeOutStr = getOptionalProperty(suppliedAppNamePropertyName
		+ "." + SDK_TIMEOUT_SUFFIX);
	if (sdkTimeOutStr != null)
	{
	    setSdkTimeOut(Long.parseLong(sdkTimeOutStr));
	}

	// Optional child element overrwrite
	Integer childCount = getOptionalProperty(suppliedAppNamePropertyName
			+ "." + SDK_CHILD_COUNT, 50000, Integer.class);
	log.debug("Setting CXF timeout: " + childCount);
	setChildElementCount(new Long(childCount));

	// If a user specified a list, then there is no reason to proceed
	// further.
	if (serverList.size() > 0)
	{
	    // We wil set the internal bean of the first element as a fail-safe
	    List<ServerBean> filteredList = getServerListByApplication(applicationType);
	    if(filteredList.size() == 0)
		throw new IllegalArgumentException("Found no server configuration for type [" + applicationType + "] please check your server configuration file!");
	    setServerBean(filteredList.get(0));
	} else if (user != null)
	{
	    // Check if user object exists
	    serverBean = new ServerBean(user.getServer(), user.getUserName(),
		    user.getPassword(), applicationType);
	} else
	{
	    // If not, then populate the serverbean
	    createAppSpecificServerBean(suppliedAppNamePropertyName);
	}

    }

    /**
     * Based on specified application name we will extract the necessary
     * properties
     */
    private void createAppSpecificServerBean(String suppliedAppNamePropertyName)
    {
	String userName = getProperty(suppliedAppNamePropertyName + "."
		+ GENERIC_USER_NAME_PROPERTY_SUFFIX);
	String server = getProperty(suppliedAppNamePropertyName + "."
		+ GENERIC_SERVER_NAME_PROPERTY_SUFFIX);

	// The messy work of interpreting the possibly-plain-text, possibly-encrypted, possibly-base64-encoded password
	// is delegated to ConfigurationPassword
	ConfigurationPassword configurationPassword = ConfigurationPassword.createFromProperty(getProps(), suppliedAppNamePropertyName);

	serverBean = new ServerBean(server, userName, configurationPassword.getPlainText(), applicationType);

	log.debug("Configured custom server bean: " + serverBean);
    }

    /**
     * Returns a list of ServerBeans based on Application Name only
     * 
     * @param appName
     * @return
     * @throws Exception
     */
    public List<ServerBean> getServerListByApplication(APPLICATION appName)
	    throws IllegalArgumentException
    {
	if (serverConfigParser == null || serverList.isEmpty())
	    throw new IllegalArgumentException(
		    "Cannot retrieve server beans without proper server list processing!");
	return serverConfigParser.getServerListByApplication(appName);
    }

    /**
     * Process server configuration information.
     * 
     * @return the list
     */
    protected void processServerConfigurationInformation()
    {
	String serverListLocationStr = getServerListLocation();
	if (serverListLocationStr == null)
	{
	    log.warn("Server List location property exists, but empty");
	    return;
	}

	try
	{
	    File serverListLocation = null;

	    if (configFileLocation == null)
	    {
		log.warn("Loading server list using class loader");
		serverListLocation = new File(this.getClass().getClassLoader()
			.getResource(serverListLocationStr).getFile());
	    } else
	    {
		log.info("Loading server configuration relative to configuration file: "
			    + configFileLocation);
		File configFileLocationPath = new File(configFileLocation);
		serverListLocation = new File(
			configFileLocationPath.getParent() + File.separator
				+ serverListLocationStr);
	    }

	    if (serverListLocation.exists())
	    {
		serverConfigParser = new ServerConfigurationParser(
			serverListLocation.toString());
		try
		{
		    serverList = serverConfigParser
			    .processServerConfiguration();
		} catch (Exception e)
		{
		    log.error("Unable to parse server file", e);
		    throw new IllegalArgumentException(
			    "Unable to parse server file!" + e.getMessage());
		}
	    } else
		throw new IllegalArgumentException(
			"Server list location does not exist:"
				+ serverListLocationStr);
	} catch (Exception e)
	{
	    throw new IllegalArgumentException(
		    "Unable to load server list file", e);
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
    public String getProperty(String propertyKey)
    {
	String value = null;

	if (props.containsKey(propertyKey))
	{
	    value = (String) props.get(propertyKey);
	    value = value.trim();
	    if (value == null || value.length() == 0)
		throw new IllegalArgumentException("Value DNE for key: "
			+ propertyKey);

	    value = value.trim();
	} else
	    throw new IllegalArgumentException("Property key DNE: "
		    + propertyKey);

	return value;
    }
    
    /**
     * Check to make sure this UNsupported (obsolete) property is not set.
     * If it is set, throw an IllegalArgumentException.
     * 
     * @param propertyKey
     *            the property key
     * @return the property
     */
	public void checkForUnsupportedProperty(String propertyKey,
			String newConfigMessage) {

		if (props.containsKey(propertyKey)) {
			throw new IllegalArgumentException(
					"Property key: "
							+ propertyKey
							+ " is no longer supported. "
							+ newConfigMessage
							+ ". Please refer to the documentation for the new configuration instructions.");
		}
	}

    /**
     * Retrieves optional properties and sets them to the config.
     * 
     * @param propertyKey
     *            the property key
     * @return the optional property
     */
    public String getOptionalProperty(String propertyKey)
    {
	String value = null;

	if (props.containsKey(propertyKey))
	{
	    value = (String) props.get(propertyKey);
	    value = value.trim();
	}

	if (value == null)
	{
	    log.info("[Optional] property: " + propertyKey
		    + ", is missing or blank");
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
    public <T> T getOptionalProperty(String propertyKey, T defaultValue,
	    Class<T> theClass)
    {
	T classType = theClass.cast(defaultValue);
	String value = null;
	T returnValue = null;

	if (props.containsKey(propertyKey))
	{
	    String propStr = props.getProperty(propertyKey);

	    if (propStr != null)
		propStr = propStr.trim();

	    value = propStr;
	}

	if (value == null)
	{
	    value = defaultValue.toString();
	}

	if (classType instanceof Boolean)
	{
	    returnValue = (T) new Boolean(value);
	} else if (classType instanceof Integer)
	{
	    returnValue = (T) new Integer(value);
	} else
	{
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
    public Map<String, String> getMappings() throws IllegalArgumentException
    {

	Set<Object> keys = props.keySet();
	for (Object o : keys)
	{
	    String key = (String) o;
	    if (key.startsWith(PROPERTY_TEMPLATE_MAPPING))
	    {
		String mappingPair = props.getProperty(key);
		if (mappingPair != null)
		{
		    String[] keyValuePair = mappingPair.split(",");
		    if (keyValuePair.length == 2)
		    {
			String columnKey = keyValuePair[0].trim();
			String mappingMethod = keyValuePair[1].trim();

			// Check to make sure that if the Column exists, the
			// method is the same
			// Otherwise throw Exception alerting the user.
			// TODO: Figure out a way to allow user to map same
			// column names to different methods.
			if (mappings.containsKey(columnKey))
			{
			    String existingMethod = mappings.get(columnKey);
			    if (!mappingMethod.equals(existingMethod))
				throw new IllegalArgumentException(
					columnKey
						+ " is mapped more than once to non-unique methods.");
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
     * Gets the proxy server.
     * 
     * @return the proxy server
     */
    public String getProxyServer()
    {
	return proxyServer;
    }

    /**
     * Sets the proxy server.
     * 
     * @param proxyServer
     *            the new proxy server
     */
    public void setProxyServer(String proxyServer)
    {
	this.proxyServer = proxyServer;
    }

    /**
     * Gets the proxy port.
     * 
     * @return the proxy port
     */
    public String getProxyPort()
    {
	return proxyPort;
    }

    /**
     * Sets the proxy port.
     * 
     * @param proxyPort
     *            the new proxy port
     */
    public void setProxyPort(String proxyPort)
    {
	this.proxyPort = proxyPort;
    }

    /**
     * Gets the proxy server https.
     * 
     * @return the proxy server https
     */
    public String getProxyServerHttps()
    {
	return proxyServerHttps;
    }

    /**
     * Sets the proxy server https.
     * 
     * @param proxyServerHttps
     *            the new proxy server https
     */
    public void setProxyServerHttps(String proxyServerHttps)
    {
	this.proxyServerHttps = proxyServerHttps;
    }

    /**
     * Gets the proxy port https.
     * 
     * @return the proxy port https
     */
    public String getProxyPortHttps()
    {
	return proxyPortHttps;
    }

    /**
     * Sets the proxy port https.
     * 
     * @param proxyPortHttps
     *            the new proxy port https
     */
    public void setProxyPortHttps(String proxyPortHttps)
    {
	this.proxyPortHttps = proxyPortHttps;
    }

    /**
     * Gets the sdk time out.
     * 
     * @return the sdk time out
     */
    public Long getSdkTimeOut()
    {
	return sdkTimeOut;
    }

    /**
     * Sets the sdk time out.
     * 
     * @param sdkTimeOut
     *            the new sdk time out
     */
    public void setSdkTimeOut(Long sdkTimeOut)
    {
	this.sdkTimeOut = sdkTimeOut;
    }


    /**
     * Provides information from the server URL.
     * 
     * @param urlInfo
     *            the url info
     * @return the string
     */
    public String findURLInformation(URL_INFORMATION urlInfo)
    {
	String returnString = null;
	try
	{
	    URIBuilder builder = new URIBuilder(serverURL);

	    if (urlInfo == URL_INFORMATION.HOST)
		returnString = builder.getHost();
	    else if (urlInfo == URL_INFORMATION.PORT)
		returnString = Integer.toString(builder.getPort());
	    else if (urlInfo == URL_INFORMATION.PROTOCOL)
		returnString = builder.getScheme();
	} catch (Exception e)
	{
	    log.warn("Unable to determine host name for: " + serverURL, e);
	}

	return returnString;
    }

    /**
     * Fully resolved URL, used to instantiate SDK Proxy Objects.
     * 
     * @return the server url
     */
    public String getServerURL()
    {
	return serverURL;
    }

    /**
     * Sets the internal server URL so that it can determine host and port
     * information.
     * 
     * @param serverURL
     *            the new server url
     */
    public void setServerURL(String serverURL)
    {
	this.serverURL = serverURL;
    }

    /**
     * Gets the props.
     * 
     * @return the props
     */
    public Properties getProps()
    {
	return props;
    }

    /**
     * Gets the server list location.
     * 
     * @return the server list location
     */
    private String getServerListLocation()
    {
	return serverListLocation;
    }

    /**
     * Sets the server list location.
     * 
     * @param serverListLocation
     *            the new server list location
     */
    private void setServerListLocation(String serverListLocation)
    {
	this.serverListLocation = serverListLocation;
    }

    public APPLICATION getApplicationType()
    {
	return applicationType;
    }

    public void setApplicationType(APPLICATION applicationName)
    {
	this.applicationType = applicationName;
    }

    /**
     * Returns this ConfigurationManager's server bean. This may either be the
     * explicitly configured application_name.property_name settings <br>
     * or the first element the server.list configuration.
     * 
     * @return
     */
    public ServerBean getServerBean()
    {
	if (serverBean != null)
	    return serverBean;
	else
	{
	    serverBean = serverList.get(0);
	    return serverBean;
	}
    }

    private void setServerBean(ServerBean serverBean)
    {
	this.serverBean = serverBean;
    }

    public List<ServerBean> getServerList()
    {
	return serverList;
    }

    /**
     * Allows the user to add server beans Useful in the case of a non-standard
     * configuration.
     * 
     * @param bean
     */
    public void addServerBean(ServerBean bean)
    {
	if (serverList != null)
	{
	    serverList.add(bean);
	    log.debug("Added bean : " + bean.toString());
	    log.debug("Server list size: " + serverList.size());
	}
    }
    
    ///// EMAIL ////
    
    /**
     * Returns the email configuration object.
     * This contains the required email protocol information 
     *  - SMTP address
     *  - SMTP TO
     *  - SMTP From
     * @return
     */
    public EmailBean getEmailConfiguration()
    {
	return emailConfiguration;
    }

	public Long getChildElementCount() {
		return childElementCount;
	}

	public void setChildElementCount(Long childElementCount) {
		this.childElementCount = childElementCount;
	}
    
}
