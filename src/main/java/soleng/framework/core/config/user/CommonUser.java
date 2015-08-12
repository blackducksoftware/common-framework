package soleng.framework.core.config.user;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Interface CommonUser.
 */
public interface CommonUser extends Serializable {

	/**
	 * Sets the server.
	 *
	 * @param servername the new server
	 */
	public void setServer(String servername);
	
	/**
	 * Sets the user name.
	 *
	 * @param username the new user name
	 */
	public void setUserName(String username);
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password);
	
	/**
	 * Gets the server.
	 *
	 * @return the server
	 */
	public String getServer();
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName();
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword();
}
