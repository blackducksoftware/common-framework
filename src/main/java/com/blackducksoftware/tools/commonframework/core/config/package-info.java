/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
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