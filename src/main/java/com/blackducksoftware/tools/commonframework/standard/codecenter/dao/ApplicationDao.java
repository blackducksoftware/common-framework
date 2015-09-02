/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.standard.codecenter.dao;

import java.util.List;
import java.util.SortedSet;

import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ApplicationPojo;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ComponentPojo;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ComponentUsePojo;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.VulnerabilityPojo;

/**
 * Manages components/vulnerabilities/metadata for a single application.
 * 
 * @author sbillings
 * 
 */
public interface ApplicationDao {

    /**
     * Get the application as a POJO.
     * 
     * @return the application as a POJO.
     * @throws Exception
     */
    ApplicationPojo getApplication() throws Exception;

    /**
     * Get the list of component uses.
     * 
     * @return
     * @throws Exception
     */
    List<ComponentUsePojo> getComponentUses() throws Exception;

    /**
     * Get the list of components.
     * 
     * @return
     * @throws Exception
     */
    List<ComponentPojo> getComponents() throws Exception;

    /**
     * Get the components as a sorted list.
     * 
     * @return
     * @throws Exception
     */
    SortedSet<ComponentPojo> getComponentsSorted() throws Exception;

    /**
     * Get a component given its component use.
     * 
     * @param componentUse
     * @return
     * @throws Exception
     */
    ComponentPojo getComponent(ComponentUsePojo componentUse) throws Exception;

    /**
     * Get the list of vulnerabilities for a component.
     * 
     * @param component
     * @param compUse
     * @return
     * @throws Exception
     */
    List<VulnerabilityPojo> getVulnerabilities(ComponentPojo component,
	    ComponentUsePojo compUse) throws Exception;

    /**
     * get the vulnerabilities for a component as a sorted list.
     * 
     * @param compPojo
     * @param compUsePojo
     * @return
     * @throws Exception
     */
    SortedSet<VulnerabilityPojo> getVulnerabilitiesSorted(
	    ComponentPojo compPojo, ComponentUsePojo compUsePojo)
	    throws Exception;

    /**
     * Update the vulnerability data for a component.
     * 
     * @param compUse
     * @param vuln
     * @throws Exception
     */
    void updateCompUseVulnData(ComponentUsePojo compUse, VulnerabilityPojo vuln)
	    throws Exception;
}
