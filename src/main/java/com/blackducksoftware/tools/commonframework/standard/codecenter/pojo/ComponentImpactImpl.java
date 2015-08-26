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
package com.blackducksoftware.tools.commonframework.standard.codecenter.pojo;

import java.util.List;

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
