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
package com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder;

/**
 * This ProgressRecorder does nothing. It is useful when your class sometimes
 * uses a ProgressRecorder (say, when it's run from a web app) and sometimes
 * doesn't (when it's run standalone). Initialize the ProgressRecorder field to
 * an instance of NOPProgressRecorder to ensure the code will work even when no
 * one calls the setProgressRecorder method.
 * 
 * @author sbillings
 *
 */
public class NOPProgressRecorder extends AbstractProgressRecorder {

    protected void reportProgress(int percentComplete, int completedSoFar,
	    int totalToComplete) {
    }
}
