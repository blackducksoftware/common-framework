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

import com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder.ProgressRecorder;

/**
 * Worker classes that execute long-running tasks can implement this interface
 * to enable clients to monitor progress of that task.
 *
 * @author sbillings
 *
 */
public interface Trackable {
    /**
     * The TrackableRunner object will call this method to provide the worker
     * with the ProgressRecorder. The worker should provide periodic updates on
     * progress to the ProgressRecorder.
     *
     * @param pr
     *            The progress recorder.
     */
    void setProgressRecorder(ProgressRecorder pr);

    /**
     * The TrackableRunner object will call this method to run the task.
     *
     * @throws Exception
     *             the exception
     */
    void runAndTrack() throws Exception;
}
