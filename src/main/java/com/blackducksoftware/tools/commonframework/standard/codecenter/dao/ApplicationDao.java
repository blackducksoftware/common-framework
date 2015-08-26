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

    ApplicationPojo getApplication() throws Exception;

    List<ComponentUsePojo> getComponentUses() throws Exception;

    List<ComponentPojo> getComponents() throws Exception;

    SortedSet<ComponentPojo> getComponentsSorted() throws Exception;

    ComponentPojo getComponent(ComponentUsePojo componentUse) throws Exception;

    List<VulnerabilityPojo> getVulnerabilities(ComponentPojo component,
	    ComponentUsePojo compUse) throws Exception;

    SortedSet<VulnerabilityPojo> getVulnerabilitiesSorted(
	    ComponentPojo compPojo, ComponentUsePojo compUsePojo)
	    throws Exception;

    void updateCompUseVulnData(ComponentUsePojo compUse, VulnerabilityPojo vuln)
	    throws Exception;
}
