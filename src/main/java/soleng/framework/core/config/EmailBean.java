/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

/**
 * 
 */
package soleng.framework.core.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import soleng.framework.standard.email.EmailTriggerRule;

/**
 * Basic email config bean to hold email information, if provided
 * 
 * @author Ari Kamen
 * @date Aug 27, 2014
 * 
 */
public class EmailBean
{
    // These are basic requirements for simple email
    private String smtpAddress = new String("");
    private String smtpTo = new String("");
    private String smtpFrom = new String("");

    // These are optional
    private Boolean useAuth;
    private String authUserName;
    private String authPassword;
    private Integer smtpPort;
    private String emailProtocol;
	private List<EmailTriggerRule> rules = new ArrayList<EmailTriggerRule>();
    
    
    public EmailBean()
    {
	
    }
    
    public boolean isEmailConfigured()
    {
	if (!StringUtils.isEmpty(smtpAddress) && !StringUtils.isEmpty(smtpTo) && !StringUtils.isEmpty(smtpFrom))
	    return true;
	else
	    return false;
    }

    public String getSmtpAddress()
    {
	return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress)
    {
	this.smtpAddress = smtpAddress;
    }

    public String getSmtpTo()
    {
	return smtpTo;
    }

    public void setSmtpTo(String smtpTo)
    {
	this.smtpTo = smtpTo;
    }

    public String getSmtpFrom()
    {
	return smtpFrom;
    }

    public void setSmtpFrom(String smtpFrom)
    {
	this.smtpFrom = smtpFrom;
    }

    public Boolean isUseAuth()
    {
	return useAuth;
    }

    public void setUseAuth(Boolean useAuth)
    {
	this.useAuth = useAuth;
    }

    public String getAuthUserName()
    {
	return authUserName;
    }

    public void setAuthUserName(String authUserName)
    {
	this.authUserName = authUserName;
    }

    public String getAuthPassword()
    {
	return authPassword;
    }

    public void setAuthPassword(String authPassword)
    {
	this.authPassword = authPassword;
    }

    public Integer getSmtpPort()
    {
	return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort)
    {
	this.smtpPort = smtpPort;
    }

    public String getEmailProtocol()
    {
	return emailProtocol;
    }

    public void setEmailProtocol(String emailProtocol)
    {
	this.emailProtocol = emailProtocol;
    }

	public List<EmailTriggerRule> getTriggerRules() 
	{
		return rules;		
	}

	/**
	 * Parse the comma separated rules into a map
	 * @param triggerRules
	 */
	public void setTriggerRules(String triggerRules) 
	{
		if(triggerRules != null && triggerRules.length() > 0)
		{
			String[] rulesStrings = triggerRules.split(",");
			for(String ruleStr : rulesStrings)
			{
				if(ruleStr.length() > 0)
				{
					EmailTriggerRule newRule = new EmailTriggerRule(ruleStr);
					rules.add(newRule);
				}
			}
		}
	}

}
