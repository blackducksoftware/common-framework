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
package com.blackducksoftware.tools.commonframework.core.gwt.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * An object that the StandardLoginScreen can use to call the login() RPC call
 * to the server. The StandardLoginScreen provides the callback (and will use it
 * to handle the response from the server).
 *
 * @author sbillings
 *
 */
public interface LoginService {

    /**
     * Login user.
     *
     * @param loginInfo
     *            the login info
     * @param callback
     *            the callback
     */
    void loginUser(AuthenticationUser loginInfo,
	    AsyncCallback<AuthenticationUser> callback);
}
