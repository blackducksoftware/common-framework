/**
 * Handles comparison, which imposes ordering on collection of ProtexProjectPojo objects. 
 * It can be passed to a sort method (such as Collections.sort or Arrays.sort) to allow precise control over the sort order. 
 *  
 * @author garoushanian
 * @date April 23, 2015
 *
 * Copyright (C) 2015 Black Duck Software Inc.
 *
 * http://www.blackducksoftware.com/
 * All rights reserved. 
 */
package soleng.framework.standard.protex;

import java.util.Comparator;

import soleng.framework.standard.protex.ProtexProjectPojo;

public class ProtexProjectPojoComparator implements Comparator<ProtexProjectPojo> {

	@Override
	public int compare(ProtexProjectPojo project1, ProtexProjectPojo project2) {
		return project1.getProjectName().compareToIgnoreCase(project2.getProjectName());
	}
}
