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

package com.blackducksoftware.tools.commonframework.standard.codecenter.dao;

import java.util.List;

import com.blackducksoftware.tools.commonframework.core.config.IConfigurationManager;

/**
 * An interface for ConfigurationManager subclasses that need to be consumable
 * by a CodeCenterDao object by managing a list of application custom attribute
 * names.
 * 
 * @author sbillings
 * 
 */
public interface CodeCenterDaoConfigManager extends IConfigurationManager {

    @Deprecated
    int getEstNumApps();

    @Deprecated
    void setEstNumApps(int estNumApps);

    /**
     * Add an application custom attribute to the list of attributes to track.
     * 
     * @param attrName
     */
    void addApplicationAttribute(String attrName);

    /**
     * Get the list of names of application custom attributes to track.
     * 
     * @return
     */
    List<String> getApplicationAttributeNames();

    @Deprecated
    String getCcDbServerName();

    @Deprecated
    void setCcDbServerName(String dbServer);

    @Deprecated
    int getCcDbPort();

    @Deprecated
    void setCcDbPort(int dbPort);

    @Deprecated
    String getCcDbUserName();

    @Deprecated
    void setCcDbUserName(String dbUser);

    @Deprecated
    String getCcDbPassword();

    @Deprecated
    void setCcDbPassword(String dbPassword);

    /**
     * Get the value of the "should ignore non-KB components" flag.
     * 
     * @return
     */
    boolean isSkipNonKbComponents();

}
