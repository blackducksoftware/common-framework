package com.blackducksoftware.tools.commonframework.core.config;

import java.util.ArrayList;
import java.util.List;

import com.blackducksoftware.tools.commonframework.standard.email.EmailTriggerRule;

public class NotificationRulesConfig {
    private final List<EmailTriggerRule> rules = new ArrayList<EmailTriggerRule>();

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

    public List<EmailTriggerRule> getRules() {
	return rules;
    }
}
