package soleng.framework.core.gwt.ui;

// TODO: Auto-generated Javadoc
/**
 * An object that the StandardLoginScreen should notify upon successful login.
 * It should initiate whatever comes next after login (typically displaying the next screen).
 * @author sbillings
 *
 */
public interface LoginHandler {
	
	/**
	 * On success.
	 *
	 * @param user the user
	 */
	public void onSuccess(AuthenticationUser user);
}
