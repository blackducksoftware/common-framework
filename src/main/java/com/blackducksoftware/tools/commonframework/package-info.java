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
/**
 * CommonFramework is a collection of non-SDK-based classes that are useful in building Black Duck SDK-based utilities.
 * For general information, please see the <a href="https://github.com/blackducksoftware/common-framework/wiki" target="_blank">wiki</a>.
 * For package- and class-specific information, please keep reading these javadocs.
 * <p>
 * <h2>High-Level Package Structure</h2>
 * The Common Framework is now organized as follows:
 * <ul>
 * <li>commonframework.framework.core : GWT-client-compatible classes, including classes shared across GWT-client and other code.
 * <li>commonframework.framework.core.gwt: GWT-client-specific classes (UI widgets, etc.)
 * <li>commonframework.framework.standard: GWT-client-INcompatible classes. (In reality some 
 * GWT-compatible classes have probably ended up here.)
 * </ul>
 * All classes added under soleng.framework.core must be GWT-client-compatible.
 * In short, this means using the GWT-compatible subset of Java,
 * documented here: http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsCompatibility.html
 */
package com.blackducksoftware.tools.commonframework;