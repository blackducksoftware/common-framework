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
