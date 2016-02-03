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
package com.blackducksoftware.tools.commonframework.standard.codecenter.pojo;

/**
 * Plain old java object class for a Component.
 * 
 * @author sbillings
 * 
 */
public class ComponentPojoImpl implements ComponentPojo {

    private final String id;

    private final String name;

    private final String version;

    private final String kbComponentId; // Might be null

    public ComponentPojoImpl(String id, String name, String version,
	    String kbComponentId) {
	this.id = id;

	if (name == null) {
	    this.name = "";
	} else {
	    this.name = name.trim();
	}

	if (version == null) {
	    this.version = "";
	} else {
	    this.version = version.trim();
	}

	this.kbComponentId = kbComponentId;
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
    public String getKbComponentId() {
	return kbComponentId;
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
	ComponentPojoImpl other = (ComponentPojoImpl) obj;
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
    public int compareTo(ComponentPojo otherCompPojo) {
	return toString().compareTo(otherCompPojo.toString());
    }

    /**
     * compareTo() uses toString() for comparisons (for sorting)
     */
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Component: name = ");
	sb.append(name);
	sb.append(", version = ");
	sb.append(version);
	return sb.toString();
    }

}
