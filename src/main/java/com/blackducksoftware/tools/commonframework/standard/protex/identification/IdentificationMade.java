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
package com.blackducksoftware.tools.commonframework.standard.protex.identification;

public class IdentificationMade {
    private final String path;

    private final int startLine;

    private final int numberOfLines;

    private final String componentId;

    private final String componentVersionId;

    private final String componentVersionString;

    private final int score;

    public IdentificationMade(String path, int startLine, int numberOfLines,
	    String componentId, String componentVersionId,
	    String componentVersionString, int score) {
	super();
	this.path = path;
	this.startLine = startLine;
	this.numberOfLines = numberOfLines;
	this.componentId = componentId;
	this.componentVersionId = componentVersionId;
	this.componentVersionString = componentVersionString;
	this.score = score;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((componentId == null) ? 0 : componentId.hashCode());
	result = prime
		* result
		+ ((componentVersionId == null) ? 0 : componentVersionId
			.hashCode());
	result = prime * result + numberOfLines;
	result = prime * result + ((path == null) ? 0 : path.hashCode());
	result = prime * result + score;
	result = prime * result + startLine;
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
	if (!(obj instanceof IdentificationMade)) {
	    return false;
	}
	IdentificationMade other = (IdentificationMade) obj;
	if (componentId == null) {
	    if (other.componentId != null) {
		return false;
	    }
	} else if (!componentId.equals(other.componentId)) {
	    return false;
	}
	if (componentVersionId == null) {
	    if (other.componentVersionId != null) {
		return false;
	    }
	} else if (!componentVersionId.equals(other.componentVersionId)) {
	    return false;
	}
	if (numberOfLines != other.numberOfLines) {
	    return false;
	}
	if (path == null) {
	    if (other.path != null) {
		return false;
	    }
	} else if (!path.equals(other.path)) {
	    return false;
	}
	if (score != other.score) {
	    return false;
	}
	if (startLine != other.startLine) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "IdentificationMade [path=" + path + ", startLine=" + startLine
		+ ", numberOfLines=" + numberOfLines + ", componentId="
		+ componentId + ", componentVersion=" + componentVersionId
		+ ", componentVersionString=" + componentVersionString
		+ ", score=" + score + "]";
    }

}
