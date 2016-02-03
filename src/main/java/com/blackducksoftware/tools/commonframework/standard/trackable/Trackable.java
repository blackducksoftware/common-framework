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
package com.blackducksoftware.tools.commonframework.standard.trackable;

import com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder.ProgressRecorder;

/**
 * Worker classes that execute long-running tasks can implement this interface
 * to enable clients to monitor progress of that task.
 *
 * @author sbillings
 *
 */
public interface Trackable {
    /**
     * The TrackableRunner object will call this method to provide the worker
     * with the ProgressRecorder. The worker should provide periodic updates on
     * progress to the ProgressRecorder.
     *
     * @param pr
     *            The progress recorder.
     */
    void setProgressRecorder(ProgressRecorder pr);

    /**
     * The TrackableRunner object will call this method to run the task.
     *
     * @throws Exception
     *             the exception
     */
    void runAndTrack() throws Exception;
}
