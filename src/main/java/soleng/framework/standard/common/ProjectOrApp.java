/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.common;

import java.util.Map;

import soleng.framework.core.exception.CommonFrameworkException;

public interface ProjectOrApp {
	// Clone
	public ProjectOrApp clone(String newName) throws CommonFrameworkException ;
	
	/**
	 * Clone the app and it's project assoc (if any), 
	 * and change some app custom attributes in the clone
	 * The final two args are ignored if its a Protex project
	 * @param newName
	 * @param appAttrUpdates
	 * @param associatedProjectId
	 * @return
	 * @throws CommonFrameworkException
	 */
	public ProjectOrApp clone(String newName, Map<String, String> appAttrUpdates,
			String associatedProjectId) throws CommonFrameworkException ;
	
	public void rename(String newName) throws CommonFrameworkException ;
	public void lock() throws CommonFrameworkException;
	public String getName();
	public String getId();
	
	/**
	 * Get the ID of a different project/app, or null if it doesn't exist.
	 * For Code Center apps, the version used is the same as this app's version
	 * @param name
	 * @return
	 */
	public String getId(String name);
}
