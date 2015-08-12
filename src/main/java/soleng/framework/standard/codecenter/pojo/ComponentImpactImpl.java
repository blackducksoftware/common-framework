/**
 * Object to hold the component impact information
 * 
 * @author garoushanian
 * @date Oct 06, 2014
 *
 * Copyright (C) 2014 Black Duck Software Inc.
 *
 * http://www.blackducksoftware.com/
 * All rights reserved. 
 *
 */
package soleng.framework.standard.codecenter.pojo;

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
	
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentImpact#getComponent()
	 */
	public ComponentPojo getComponent() {
		return component;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentImpact#setComponent(soleng.framework.standard.codecenter.pojo.ComponentPojo)
	 */
	public void setComponent(ComponentPojo component) {
		this.component = component;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentImpact#getApplicationList()
	 */
	public List<ApplicationPojo> getApplicationList() {
		return applicationList;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentImpact#setApplicationList(java.util.List)
	 */
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
