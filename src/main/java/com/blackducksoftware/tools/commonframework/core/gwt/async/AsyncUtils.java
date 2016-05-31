/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 *  under the License.
 *
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.core.gwt.async;

import com.google.gwt.user.client.Timer;

/**
 * A utility class for managing long-running server-side tasks. Let's you
 * schedule a future polling event, and convert a % complete value into a crude
 * progress bar string.
 */
public class AsyncUtils {

    /** The timer. */
    private Timer timer;

    /**
     * Schedule a task for some future time. Useful for polling to check
     * progress after starting an asynchronous task.
     * 
     * @param numSeconds
     *            The task will be run this many seconds from now
     * @param taskToSchedule
     *            The task to run (when it's time)
     */
    public void scheduleTask(int numSeconds, final ScheduledTask taskToSchedule) {
	if (timer == null) {
	    timer = new Timer() {
		public void run() {
		    taskToSchedule.runAfterTimer();
		}
	    };
	}
	timer.schedule(numSeconds * 1000);
    }

    /**
     * Build a "progress bar-like" message.
     * 
     * @param percentComplete
     *            The percentage of the task that has been completed
     * @return A string representing a crude progress bar, like:
     *         "|xxxxx     |50%"
     */
    public static String generateProgressBarString(int percentComplete) {
	if (percentComplete > 100) {
	    percentComplete = 100;
	} else if (percentComplete < 0) {
	    percentComplete = 0;
	}
	int numDots = percentComplete / 5;
	StringBuilder progressMessage = new StringBuilder("|");
	for (int i = 0; i < 20; i++) {
	    if (i < numDots) {
		progressMessage.append(">");
	    } else {
		progressMessage.append("_");
	    }
	}
	progressMessage.append("|");
	progressMessage.append(percentComplete);
	progressMessage.append("%");

	return progressMessage.toString();
    }

}
