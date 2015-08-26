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

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.standard.trackable.ProgressInfo;

/**
 * This ProgressRecorder writes progress to the given queue.
 *
 * @author sbillings
 *
 */
public class QueuingProgressRecorder extends AbstractProgressRecorder {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass()
	    .getName());

    // Unique to this subclass
    /** The queue. */
    private final Queue<ProgressInfo> queue;

    /**
     * Instantiates a new queuing progress recorder.
     *
     * @param queue
     *            the queue
     */
    public QueuingProgressRecorder(Queue<ProgressInfo> queue) {
	super();
	this.queue = queue;
    }

    @Override
    protected void reportProgress(int percentComplete, int completedSoFar,
	    int totalToComplete) {
	ProgressInfo progressInfo = new ProgressInfo(percentComplete,
		completedSoFar, totalToComplete);
	queue.add(progressInfo);
	log.debug("lastReportedPercentComplete: " + percentComplete);
    }
}
