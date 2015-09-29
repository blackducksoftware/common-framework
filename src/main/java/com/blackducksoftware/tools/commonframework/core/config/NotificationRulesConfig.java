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
