/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/

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
