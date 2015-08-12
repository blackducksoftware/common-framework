package soleng.framework.core.config.testbeans;

import soleng.framework.core.config.user.CommonUser;

// TODO: Auto-generated Javadoc
/**
 * The Class TestCommonUser.
 */
public class TestCommonUser implements CommonUser {
	
	/** The server. */
	private String server;
	
	/** The user name. */
	private String userName;
	
	/** The password. */
	private String password;
	
	/** The Constant serialVersionUID. */
	static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see soleng.framework.core.config.user.CommonUser#getServer()
	 */
	public String getServer() {
		return server;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.core.config.user.CommonUser#setServer(java.lang.String)
	 */
	public void setServer(String server) {
		this.server = server;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.core.config.user.CommonUser#getUserName()
	 */
	public String getUserName() {
		return userName;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.core.config.user.CommonUser#setUserName(java.lang.String)
	 */
	public void setUserName(String user) {
		this.userName = user;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.core.config.user.CommonUser#getPassword()
	 */
	public String getPassword() {
		return password;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.core.config.user.CommonUser#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
