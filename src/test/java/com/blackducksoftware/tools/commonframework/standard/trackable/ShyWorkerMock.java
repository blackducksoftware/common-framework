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
package com.blackducksoftware.tools.commonframework.standard.trackable;

import com.blackducksoftware.tools.commonframework.standard.trackable.Trackable;
import com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder.ProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class ShyWorkerMock.
 */
public class ShyWorkerMock implements Trackable {

    /** The progress recorder. */
    private ProgressRecorder progressRecorder;

    /** The configuration. */
    private String configuration = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.standard.trackable.Trackable
     * #setProgressRecorder(com.blackducksoftware
     * .tools.commonframework.standard
     * .trackable.progressrecorder.ProgressRecorder)
     */
    public void setProgressRecorder(ProgressRecorder progressRecorder) {
	this.progressRecorder = progressRecorder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.standard.trackable.Trackable
     * #runAndTrack()
     */
    public void runAndTrack() {
	// The worker must initialize the recorder
	progressRecorder.setTotalToComplete(10); // Define "done"; how many are
						 // we doing?
	progressRecorder.setGranularity(5); // Set the granulary (5 = every 5%)

	// Simulate the (slow) work
	for (int i = 0; i < 10; i++) {
	    try {
		Thread.sleep(10L);
	    } catch (InterruptedException e) {
		break;
	    }
	    // Normally: Each time we complete one, tell the recorder how many
	    // we've completed
	    // However, this worker will neglect the updates
	    // We want to degrade the progress service gracefully (be
	    // innaccurate) in this case, rather than failing
	    // progressRecorder.setCompletedSoFar(i+1);
	}
	progressRecorder.setDone(); // Declare all the work done (even though we
				    // neglected the updates
    }

    // The reset of the methods are not part of the Trackable interface
    // but real worker classes may need other methods (or
    // constructors/factories) to
    // enable them to be configured.
    /**
     * Sets the configuration.
     *
     * @param configuration
     *            the new configuration
     */
    public void setConfiguration(String configuration) {
	this.configuration = configuration;
    }

    /**
     * Gets the configuration.
     *
     * @return the configuration
     */
    public String getConfiguration() {
	return configuration;
    }
}
