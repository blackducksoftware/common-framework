package soleng.framework.core.config.server;

import soleng.framework.core.config.ConfigConstants;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Simple bean to hold server information
 * @author akamen
 *
 */
@XStreamAlias(ConfigConstants.SERVER_PROPERTY)
public class ServerBean extends ConfigConstants
{
	
	/** The server url. */
	@XStreamAlias(ConfigConstants.GENERIC_SERVER_NAME_PROPERTY_SUFFIX)
	private String serverName;
	
	/** The user name. */
	@XStreamAlias(ConfigConstants.GENERIC_USER_NAME_PROPERTY_SUFFIX)
	private String userName;
	
	/** The password. */
	@XStreamAlias(ConfigConstants.GENERIC_PASSWORD_PROPERTY_SUFFIX)
	private String password;
	
	/** Optional Alias */
	@XStreamAlias(ConfigConstants.GENERIC_ALIAS_PROPERTY_SUFFIX)
	private String alias;
	


	/** The suite type. */
	@XStreamAlias(ConfigConstants.APPLICATION_NAME_PROPERTY)
	private APPLICATION application;
	
	/**
	 * Instantiates a new server bean.
	 */
	public ServerBean() 
	{
		
	}
	
	/**
	 * Creates a plain server bean
	 * @param server
	 * @param user
	 * @param password
	 */
	public ServerBean(String server, String user, String password, APPLICATION appName)
	{
		setServerName(server);
		setUserName(user);
		setPassword(password);
		setApplication(appName);
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public APPLICATION getApplication() {
		return application;
	}
	public void setApplication(APPLICATION suiteType) {
		this.application = suiteType;
	}
	
	public String getAlias()
	{
	    return alias;
	}

	public void setAlias(String alias)
	{
	    this.alias = alias;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("Server URL: " + getServerName());
		sb.append("\n");
		sb.append("User Name: " + getUserName());
		sb.append("\n");
		sb.append("Application Name: " + getApplication());
		sb.append("\n");
		sb.append("Alias: " + this.getAlias());
		sb.append("\n");
		
		return sb.toString();
	}
}
