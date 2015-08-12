/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.codecenter.pojo;

public interface ComponentPojo extends Comparable<ComponentPojo> {

	public String getId();

	public String getName();

	public String getVersion();

	/**
	 * Might return null
	 * @return
	 */
	public String getKbComponentId();

}