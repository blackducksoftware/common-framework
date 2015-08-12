/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.codecenter.pojo;

import java.util.Map;

public interface ApplicationPojo {

	public String getId();

	public String getName();

	public String getVersion();

	public String getDescription();

	public Map<String, String> getAppAttrNameValueMap();

	public String getCustomAttributeValue(String customAttributeName);

}