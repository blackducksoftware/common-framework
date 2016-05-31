/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 *  under the License.
 *
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
