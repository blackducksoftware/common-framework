/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.config.server;

import java.util.ArrayList;
import java.util.List;

import com.blackducksoftware.tools.commonframework.core.config.ConfigConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Class that holds the list of server objects (used by parsers to generate
 * implicit lists)
 * 
 * @author akamen
 *
 */
@XStreamAlias(ConfigConstants.SERVERS_PROPERTY)
public class ServerBeanList extends ConfigConstants {

    /** The server list. */
    @XStreamImplicit(itemFieldName = SERVER_PROPERTY)
    private List<ServerBean> serverList = new ArrayList<ServerBean>();

    /**
     * Instantiates a new server bean list.
     */
    public ServerBeanList() {

    }

    /**
     * Adds the.
     *
     * @param bean
     *            the bean
     */
    public void add(ServerBean bean) {
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
     * @param serverList
     *            the new servers
     */
    public void setServers(List<ServerBean> serverList) {
	this.serverList = serverList;
    }
}
