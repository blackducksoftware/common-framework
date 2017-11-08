/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.tools.commonframework.standard.codecenter.pojo;

import java.util.List;

/**
 * Manages where-used information for a component.
 * 
 * @author sbillings
 * 
 */
public class ComponentImpactImpl implements ComponentImpact {

    private ComponentPojo component;

    private List<ApplicationPojo> applicationList;

    public ComponentImpactImpl(ComponentPojo component) {
	this.component = component;
    }

    public ComponentImpactImpl(ComponentPojo component,
	    List<ApplicationPojo> applicationList) {
	this.component = component;
	this.applicationList = applicationList;
    }

    @Override
    public ComponentPojo getComponent() {
	return component;
    }

    @Override
    public void setComponent(ComponentPojo component) {
	this.component = component;
    }

    @Override
    public List<ApplicationPojo> getApplicationList() {
	return applicationList;
    }

    @Override
    public void setApplicationList(List<ApplicationPojo> applicationList) {
	this.applicationList = applicationList;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder("ComponentImpact:  component=");
	sb.append(component);
	sb.append(";  applicationList=");
	sb.append(applicationList);
	return sb.toString();
    }
}
