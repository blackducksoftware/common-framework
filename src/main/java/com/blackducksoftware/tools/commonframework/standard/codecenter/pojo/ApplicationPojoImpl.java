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
package com.blackducksoftware.tools.commonframework.standard.codecenter.pojo;

import java.util.Map;

/**
 * A plain old Java object representing a Code Center application.
 * 
 * @author sbillings
 * 
 */
public class ApplicationPojoImpl implements Comparable<ApplicationPojo>,
	ApplicationPojo {
    private final String id;

    private final String name;

    private final String version;

    private final String description;

    private final Map<String, String> appAttrNameValueMap;

    public ApplicationPojoImpl(String id, String name, String version,
	    String description, Map<String, String> appAttrNameValueMap) {
	this.id = id;
	this.name = name.trim();
	this.version = version.trim();
	this.description = description.trim();

	this.appAttrNameValueMap = appAttrNameValueMap;
    }

    public ApplicationPojoImpl(String id) {
	this.id = id;
	name = "";
	version = "";
	description = "";
	appAttrNameValueMap = null;
    }

    @Override
    public String getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public String getVersion() {
	return version;
    }

    @Override
    public String getDescription() {
	return description;
    }

    @Override
    public Map<String, String> getAppAttrNameValueMap() {
	return appAttrNameValueMap;
    }

    @Override
    public String getCustomAttributeValue(String customAttributeName) {
	return appAttrNameValueMap.get(customAttributeName);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	ApplicationPojoImpl other = (ApplicationPojoImpl) obj;
	if (id == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!id.equals(other.id)) {
	    return false;
	}
	return true;
    }

    @Override
    public int compareTo(ApplicationPojo o) {
	ApplicationPojo otherAppPojo = o;
	return name.compareTo(otherAppPojo.getName());
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder("ApplicationPojo:  id=");
	sb.append(id);
	sb.append(";  name=");
	sb.append(name);
	sb.append(";  description");
	sb.append(description);
	sb.append(";  version=");
	sb.append(version);
	return sb.toString();
    }
}
