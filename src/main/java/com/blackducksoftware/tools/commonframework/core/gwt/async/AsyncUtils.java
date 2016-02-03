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
