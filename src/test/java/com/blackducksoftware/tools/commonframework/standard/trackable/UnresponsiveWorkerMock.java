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
 * The Class UnresponsiveWorkerMock.
 */
public class UnresponsiveWorkerMock implements Trackable {

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
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.standard.trackable.Trackable
     * #runAndTrack()
     */
    public void runAndTrack() throws InterruptedException {
	try {
	    Thread.sleep(15L * 1000L); // Be unresponsive for intolerably long
				       // (15 seconds)
	} catch (InterruptedException e) {
	    throw e;
	}
    }
}
