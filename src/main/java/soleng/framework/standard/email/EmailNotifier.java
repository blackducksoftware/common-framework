package soleng.framework.standard.email;

import java.util.List;

import soleng.framework.core.exception.CommonFrameworkException;
import soleng.framework.standard.common.ProjectPojo;
public interface EmailNotifier {
	
	public EmailTemplate sendEmail(ProjectPojo projectInfo, EmailContentMap content, List<EmailTriggerRule> rules) throws CommonFrameworkException;
	public EmailContentMap configureContentMap(String templateFileLocation) throws CommonFrameworkException;
	public void sendErrorNotification(Exception e, String message, String errNotifierFile, String projectName);
}
