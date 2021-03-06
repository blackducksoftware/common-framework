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
package com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder;

/**
 * An abstract ProgressRecorder that implements everything except the actual
 * recording of the progress.
 */
public abstract class AbstractProgressRecorder implements ProgressRecorder {

    /** The total to complete. */
    private int totalToComplete = 0;

    /** The completed so far. */
    private int completedSoFar = 0;

    /** The granularity. */
    private int granularity = 10;

    /** The last reported percent complete. */
    private int lastReportedPercentComplete = 0;

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

    public synchronized void setTotalToComplete(int totalToExpect) {
	this.totalToComplete = totalToExpect;
    }

    /**
     * Set the number of subtasks completed so far.
     * 
     * @param completedSoFar
     *            the number of subtasks completed so far.
     */

    public synchronized void setCompletedSoFar(int completedSoFar) {
	this.completedSoFar = completedSoFar;
	if ((calculatePercentComplete() - lastReportedPercentComplete) >= granularity) {
	    reportProgress(calculatePercentComplete(), completedSoFar,
		    totalToComplete);
	    lastReportedPercentComplete = calculatePercentComplete();
	}
    }

    /**
     * The progress reporting mechanism goes here, implemented by the concrete
     * class. That could be writing to a log, sending to a different thread,
     * etc.
     * 
     * @param percentComplete
     *            the percent complete
     * @param completedSoFar
     *            the completed so far
     * @param totalToComplete
     *            the total to complete
     */
    protected abstract void reportProgress(int percentComplete,
	    int completedSoFar, int totalToComplete);

    /**
     * Call this method to increment progress (to tell the ProgressRecorder that
     * more subtasks are done).
     * 
     * @param incrementBy
     *            the number to increment the "done so far" number by.
     */

    public synchronized void incrementCompletedSoFar(int incrementBy) {
	setCompletedSoFar(completedSoFar + incrementBy);

    }

    /**
     * Call this method to adjust how frequently the ProgressReporter reports
     * progress (say, every 5%).
     * 
     * @param granularity
     *            Granularity. For example: 5 means: report every 5%.
     */

    public synchronized void setGranularity(int granularity) {
	this.granularity = granularity;

    }

    /**
     * Getter for the percent completed so far. Used for testing.
     * 
     * @return the percentage of the subtasks completed so far.
     */
    public int getPercentComplete() {
	return calculatePercentComplete();
    }

    /**
     * Call this method to declare all subtasks done. It's a good idea to call
     * this, but wait until the last possible second.
     */

    public void setDone() {
	setCompletedSoFar(this.totalToComplete);
    }

    /**
     * Calculate the percent complete.
     * 
     * @return percent complete
     */
    private int calculatePercentComplete() {
	int percentComplete;
	if ((totalToComplete <= 0) || (completedSoFar <= 0)) {
	    percentComplete = 0;
	} else {
	    percentComplete = (int) (100 * ((float) (completedSoFar) / (float) totalToComplete));
	}
	return percentComplete;
    }
}
