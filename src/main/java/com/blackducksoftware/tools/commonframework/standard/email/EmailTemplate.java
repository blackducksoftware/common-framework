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
package com.blackducksoftware.tools.commonframework.standard.email;

public class EmailTemplate {
    private String from;

    private String to;

    private String subject;

    private String style;

    private String body;

    public void setFrom(String from) {
	this.from = from;

    }

    public void setTo(String to) {
	this.to = to;

    }

    public void setSubject(String subject) {
	this.subject = subject;

    }

    public void setStyle(String style) {
	this.style = style;

    }

    public void setBody(String body) {
	this.body = body;

    }

    public String getTo() {
	return to;
    }

    public String getFrom() {
	return from;
    }

    public String getSubject() {
	return subject;
    }

    public String getStyle() {
	return style;
    }

    public String getBody() {
	return body;
    }

}
