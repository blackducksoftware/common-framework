package soleng.framework.core.exception;

import java.io.Serializable;

import soleng.framework.core.config.ConfigurationManager;
import soleng.framework.core.config.server.ServerBean;

public class CommonFrameworkException extends Exception implements Serializable {

	private static final long serialVersionUID = -638853136815473675L;
	private ConfigurationManager cf = null;
	
	public CommonFrameworkException(String message)
	{
		super(message);
	}
	
	public CommonFrameworkException(ConfigurationManager cf, String message)
	{
		super(message);
		this.cf = cf;
	}
	
	public String getConfigurationInformation()
	{
		StringBuilder sb = new StringBuilder();
		
		ServerBean server = cf.getServerBean();
		sb.append("Server: " + server.getServerName());
		sb.append("\n");
		sb.append("User: " + server.getUserName());
		
		return sb.toString();
	}
}
