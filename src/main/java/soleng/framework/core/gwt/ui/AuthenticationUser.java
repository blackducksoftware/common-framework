package soleng.framework.core.gwt.ui;

import soleng.framework.core.config.user.CommonUser;

// TODO: Auto-generated Javadoc
/**
 * A user login info object that also contains a flag indicating whether user has been authenticated.
 * @author sbillings
 *
 */
public interface AuthenticationUser extends CommonUser {
	
	/**
	 * Checks if is authenticated.
	 *
	 * @return true, if is authenticated
	 */
	public boolean isAuthenticated();
	
	/**
	 * Sets the authenticated.
	 *
	 * @param authenticated the new authenticated
	 */
	public void setAuthenticated(boolean authenticated);
	
	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage();
	
	/**
	 * Sets the error message.
	 *
	 * @param msg the new error message
	 */
	public void setErrorMessage(String msg);
}
