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
/**
 * The config package is a collection of classes used to manage the user-specified
 * configuration for a utility. In general, utilities are configured via a .properties file.
 * A utility will extend the ConfigurationManager class; that subclass is responsible
 * for reading and managing the current configuration (specified in the properties file).
 * In most cases, it reads the configuration in the constructor, and provides configuration
 * details to the utility via getter methods. The ConfigurationManager class itself manages
 * common configuration properties such as server URL, username, and password. The utility-specific
 * subclass manages utility-specific configration properties.
 */
package com.blackducksoftware.tools.commonframework.core.config;