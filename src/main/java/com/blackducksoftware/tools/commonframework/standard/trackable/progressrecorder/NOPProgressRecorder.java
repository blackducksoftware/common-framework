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
package com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder;

/**
 * This ProgressRecorder does nothing. It is useful when your class sometimes
 * uses a ProgressRecorder (say, when it's run from a web app) and sometimes
 * doesn't (when it's run standalone). Initialize the ProgressRecorder field to
 * an instance of NOPProgressRecorder to ensure the code will work even when no
 * one calls the setProgressRecorder method.
 * 
 * @author sbillings
 *
 */
public class NOPProgressRecorder extends AbstractProgressRecorder {

    protected void reportProgress(int percentComplete, int completedSoFar,
	    int totalToComplete) {
    }
}
