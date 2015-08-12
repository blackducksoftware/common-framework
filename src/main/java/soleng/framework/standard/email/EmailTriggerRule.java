package soleng.framework.standard.email;

/**
 * Little bean representing email trigger rule
 * @author akamen
 *
 */
public class EmailTriggerRule {

	private String ruleName = null;
	private Boolean ruleTriggered = false;
	
	public EmailTriggerRule(String name)
	{
		this.ruleName = name;
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
