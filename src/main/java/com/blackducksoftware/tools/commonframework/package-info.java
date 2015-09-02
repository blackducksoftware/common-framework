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