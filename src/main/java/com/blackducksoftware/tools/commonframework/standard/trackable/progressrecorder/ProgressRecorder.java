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
package com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder;

/**
 * A ProgressRecorder is used to record progress updates and provide them to
 * users/clients.
 *
 * @author sbillings
 *
 */
public interface ProgressRecorder {

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
    void setTotalToComplete(int totalToExpect);

    /**
     * Set the number of subtasks completed so far.
     *
     * @param completedSoFar
     *            the number of subtasks completed so far.
     */
    void setCompletedSoFar(int completedSoFar);

    /**
     * Call this method to increment progress (to tell the ProgressRecorder that
     * more subtasks are done).
     *
     * @param incrementBy
     *            the number to increment the "done so far" number by.
     */
    void incrementCompletedSoFar(int incrementBy);

    /**
     * Call this method to adjust how frequently the ProgressReporter reports
     * progress (say, every 5%).
     *
     * @param granularity
     *            Granularity. For example: 5 means: report every 5%.
     */
    void setGranularity(int granularity);

    /**
     * Call this method to declare all subtasks done. It's a good idea to call
     * this, but wait until the last possible second.
     */
    void setDone();
}
