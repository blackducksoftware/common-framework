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
package com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder;

/**
 * A ProgressRecorder is used to record progress updates and provide them to
 * users/clients.
 *
 * @author sbillings
 *
 */
public interface ProgressRecorder {

    /**
     * Call this method to tell the progress recorder what "done" is. "Done" is
     * defined by a pre-determined set of subtasks that must be completed. Be
     * careful not to set this too low; you don't want completion to be declared
     * until everything is done. For example, if the worker task needs to read
     * 100 licenses, you might want to set this number to 101 to give the worker
     * time to finish up after the last license is read. Then, when everything
     * is done and ready for the user, call setDone().
     *
     * @param totalToExpect
     *            the new total to complete
     */
    void setTotalToComplete(int totalToExpect);

    /**
     * Set the number of subtasks completed so far.
     *
     * @param completedSoFar
     *            the number of subtasks completed so far.
     */
    void setCompletedSoFar(int completedSoFar);

    /**
     * Call this method to increment progress (to tell the ProgressRecorder that
     * more subtasks are done).
     *
     * @param incrementBy
     *            the number to increment the "done so far" number by.
     */
    void incrementCompletedSoFar(int incrementBy);

    /**
     * Call this method to adjust how frequently the ProgressReporter reports
     * progress (say, every 5%).
     *
     * @param granularity
     *            Granularity. For example: 5 means: report every 5%.
     */
    void setGranularity(int granularity);

    /**
     * Call this method to declare all subtasks done. It's a good idea to call
     * this, but wait until the last possible second.
     */
    void setDone();
}
