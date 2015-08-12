package soleng.framework.core.config.server;

import java.util.ArrayList;
import java.util.List;

import soleng.framework.core.config.ConfigConstants;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Class that holds the list of server objects (used by parsers to generate implicit lists)
 * @author akamen
 *
 */
@XStreamAlias(ConfigConstants.SERVERS_PROPERTY)
public class ServerBeanList extends ConfigConstants
{
	
	/** The server list. */
	@XStreamImplicit(itemFieldName = SERVER_PROPERTY)
	private List<ServerBean> serverList = new ArrayList<ServerBean>();

	/**
	 * Instantiates a new server bean list.
	 */
	public ServerBeanList()
	{
		
	}
	
	/**
	 * Adds the.
	 *
	 * @param bean the bean
	 */
	public void add(ServerBean bean)
	{
		serverList.add(bean);
	}
	
	/**
	 * Gets the servers.
	 *
	 * @return the servers
	 */
	public List<ServerBean> getServers() {
		return serverList;
	}

	/**
	 * Sets the servers.
	 *
	 * @param serverList the new servers
	 */
	public void setServers(List<ServerBean> serverList) {
		this.serverList = serverList;
	}
}


