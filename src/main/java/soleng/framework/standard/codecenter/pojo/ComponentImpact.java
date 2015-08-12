/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.codecenter.pojo;

import java.util.List;

public interface ComponentImpact {

	public ComponentPojo getComponent();

	public void setComponent(ComponentPojo component);

	public List<ApplicationPojo> getApplicationList();

	public void setApplicationList(List<ApplicationPojo> applicationList);

}