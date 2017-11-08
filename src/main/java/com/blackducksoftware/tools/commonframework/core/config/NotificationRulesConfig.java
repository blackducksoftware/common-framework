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
package com.blackducksoftware.tools.commonframework.core.config;

import java.util.ArrayList;
import java.util.List;

import com.blackducksoftware.tools.commonframework.standard.email.EmailTriggerRule;

/**
 * Notification rules specified in the configuration, if any. If none are
 * configured, the rules list will be empty. Notifiers can use these rules to
 * determine whether or not to issue their notification.
 *
 * @author sbillings
 *
 */
public class NotificationRulesConfig {
    private final List<EmailTriggerRule> rules = new ArrayList<EmailTriggerRule>();

    /**
     * Construct the rules set from a comma-separated list of rule names. There
     * must be no embedded spaces in the list.
     *
     * @param triggerRules
     */
    NotificationRulesConfig(String triggerRules) {
	if (triggerRules != null && triggerRules.length() > 0) {
	    String[] rulesStrings = triggerRules.split(",");
	    for (String ruleStr : rulesStrings) {
		if (ruleStr.length() > 0) {
		    EmailTriggerRule newRule = new EmailTriggerRule(ruleStr);
		    rules.add(newRule);
		}
	    }
	}
    }

    /**
     * Get the list of rules.
     *
     * @return
     */
    public List<EmailTriggerRule> getRules() {
	return rules;
    }
}
