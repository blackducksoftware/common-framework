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

/**
 * Little bean representing email trigger rule
 *
 * @author akamen
 *
 */
public class EmailTriggerRule {

    private String ruleName;

    private Boolean ruleTriggered = false;

    public EmailTriggerRule(String name) {
	ruleName = name;
    }

    public Boolean isRuleTriggered() {
	return ruleTriggered;
    }

    public void setRuleTriggered(Boolean ruleTriggered) {
	this.ruleTriggered = ruleTriggered;
    }

    public String getRuleName() {
	return ruleName;
    }

    public void setRuleName(String ruleName) {
	this.ruleName = ruleName;
    }

}
