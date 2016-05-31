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

package com.blackducksoftware.tools.commonframework.standard.common;

import java.util.Map;

import com.blackducksoftware.tools.commonframework.core.exception.CommonFrameworkException;

public interface ProjectOrApp {
    // Clone
    ProjectOrApp clone(String newName) throws CommonFrameworkException;

    /**
     * Clone the app and it's project assoc (if any), and change some app custom
     * attributes in the clone The final two args are ignored if its a Protex
     * project
     *
     * @param newName
     * @param appAttrUpdates
     * @param associatedProjectId
     * @return
     * @throws CommonFrameworkException
     */
    ProjectOrApp clone(String newName, Map<String, String> appAttrUpdates,
	    String associatedProjectId) throws CommonFrameworkException;

    void rename(String newName) throws CommonFrameworkException;

    void lock() throws CommonFrameworkException;

    String getName();

    String getId();

    /**
     * Get the ID of a different project/app, or null if it doesn't exist. For
     * Code Center apps, the version used is the same as this app's version
     *
     * @param name
     * @return
     */
    String getId(String name);
}
