package soleng.framework.core.gwt.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * An object that the StandardLoginScreen can use to call the login() RPC call to the server.
 * The StandardLoginScreen provides the callback (and will use it to handle the response from the server).
 * @author sbillings
 *
 */
public interface LoginService {
	
	/**
	 * Login user.
	 *
	 * @param loginInfo the login info
	 * @param callback the callback
	 */
	public void loginUser(AuthenticationUser loginInfo, AsyncCallback<AuthenticationUser> callback);
}
