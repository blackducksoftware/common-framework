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

/**
 * A plain old java object class for a Component.
 * 
 * @author sbillings
 * 
 */
public interface ComponentPojo extends Comparable<ComponentPojo> {

    /**
     * Get component ID.
     * 
     * @return
     */
    String getId();

    /**
     * Get component name.
     * 
     * @return
     */
    String getName();

    /**
     * Get component version.
     * 
     * @return
     */
    String getVersion();

    /**
     * Get the KB ID for the component. Might return null
     * 
     * @return
     */
    String getKbComponentId();

}
