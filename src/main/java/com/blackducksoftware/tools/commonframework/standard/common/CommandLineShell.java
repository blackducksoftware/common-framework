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
package com.blackducksoftware.tools.commonframework.standard.common;

import java.util.List;

import com.blackducksoftware.tools.commonframework.standard.protex.ProtexProjectPojo;

/**
 * The Interface for classes that can launch an interactive command line window
 * that asks the user to select a project from a list.
 */
public interface CommandLineShell {

    /**
     * Launches an interactive command line window Asks the user to select the
     * project and returns the selection.
     * 
     * User
     * {@link com.blackducksoftware.tools.commonframework.protex.ProtexWrapper }
     * to retrieve the list of projects.
     * 
     * @param projectList
     *            the project list
     * @return the project pojo
     * @throws Exception
     *             the exception
     */
    ProtexProjectPojo runProjectSelector(List<ProtexProjectPojo> projectList)
	    throws Exception;

}
