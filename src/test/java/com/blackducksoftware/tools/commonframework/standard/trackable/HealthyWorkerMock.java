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
package com.blackducksoftware.tools.commonframework.standard.trackable;

import com.blackducksoftware.tools.commonframework.standard.trackable.Trackable;
import com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder.ProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthyWorkerMock.
 */
public class HealthyWorkerMock implements Trackable {

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
	progressRecorder.setTotalToComplete(60); // Define "done"; how many are
						 // we doing?
	progressRecorder.setGranularity(5); // Set the granulary (5 = every 5%)

	// Simulate the (slow) work
	for (int i = 0; i < 60; i++) {
	    try {
		Thread.sleep(10L);
	    } catch (InterruptedException e) {
		break;
	    }
	    // Each time we complete one, tell the recorder how many we've
	    // completed
	    progressRecorder.setCompletedSoFar(i + 1);
	}
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
