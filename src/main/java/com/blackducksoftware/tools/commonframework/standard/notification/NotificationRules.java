package com.blackducksoftware.tools.commonframework.standard.notification;

import java.util.List;

import com.blackducksoftware.tools.commonframework.standard.email.EmailTriggerRule;

public class NotificationRules {
    private List<EmailTriggerRule> rules;

    public NotificationRules(List<EmailTriggerRule> rules) {
	super();
	this.rules = rules;
    }

}
