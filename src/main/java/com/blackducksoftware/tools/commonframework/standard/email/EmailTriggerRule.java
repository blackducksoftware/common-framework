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
