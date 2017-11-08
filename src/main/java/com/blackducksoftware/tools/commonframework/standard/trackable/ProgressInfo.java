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
package com.blackducksoftware.tools.commonframework.standard.trackable;

/**
 * The Class ProgressInfo.
 */
public class ProgressInfo {

    /** The total subtasks to complete. */
    private int totalSubtasksToComplete;

    /** The subtasks completed so far. */
    private int subtasksCompletedSoFar;

    /** The percent complete. */
    private int percentComplete;

    /**
     * Instantiates a new progress info.
     *
     * @param percentComplete
     *            the percent complete
     * @param subtasksCompletedSoFar
     *            the subtasks completed so far
     * @param totalSubtasksToComplete
     *            the total subtasks to complete
     */
    public ProgressInfo(int percentComplete, int subtasksCompletedSoFar,
	    int totalSubtasksToComplete) {
	this.percentComplete = percentComplete;
	this.subtasksCompletedSoFar = subtasksCompletedSoFar;
	this.totalSubtasksToComplete = totalSubtasksToComplete;
    }

    /**
     * Gets the total subtasks to complete.
     *
     * @return the total subtasks to complete
     */
    public int getTotalSubtasksToComplete() {
	return totalSubtasksToComplete;
    }

    /**
     * Sets the total subtasks to complete.
     *
     * @param totalSubtasksToComplete
     *            the new total subtasks to complete
     */
    public void setTotalSubtasksToComplete(int totalSubtasksToComplete) {
	this.totalSubtasksToComplete = totalSubtasksToComplete;
    }

    /**
     * Gets the subtasks completed so far.
     *
     * @return the subtasks completed so far
     */
    public int getSubtasksCompletedSoFar() {
	return subtasksCompletedSoFar;
    }

    /**
     * Sets the subtasks completed so far.
     *
     * @param subtasksCompletedSoFar
     *            the new subtasks completed so far
     */
    public void setSubtasksCompletedSoFar(int subtasksCompletedSoFar) {
	this.subtasksCompletedSoFar = subtasksCompletedSoFar;
    }

    /**
     * Gets the percent complete.
     *
     * @return the percent complete
     */
    public int getPercentComplete() {
	return percentComplete;
    }

    /**
     * Sets the percent complete.
     *
     * @param percentComplete
     *            the new percent complete
     */
    public void setPercentComplete(int percentComplete) {
	this.percentComplete = percentComplete;
    }
}
