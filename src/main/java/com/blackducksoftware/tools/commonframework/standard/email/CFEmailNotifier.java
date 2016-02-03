/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.standard.email;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.blackducksoftware.tools.commonframework.core.config.ConfigurationManager;
import com.blackducksoftware.tools.commonframework.core.config.EmailBean;
import com.blackducksoftware.tools.commonframework.core.exception.CommonFrameworkException;
import com.blackducksoftware.tools.commonframework.standard.common.ProjectPojo;

/**
 * Generic Email Notifier implementation for the Common Framework,
 *
 * Usage: Instantiate class with configuration manager Invoke configuration for
 * your template file and get a content map Send email with populated content
 * map
 *
 * @author Ari Kamen
 * @date June 15th, 2015
 *
 *
 */
public class CFEmailNotifier implements EmailNotifier {
    private final Logger log = LoggerFactory.getLogger(this.getClass()
	    .getName());

    private String smtpProtocol;

    private String smtpHost;

    private int smtpPort;

    private boolean smtpUseAuth;

    private String smtpUsername;

    private String smtpPassword;

    private String smtpFrom;

    private String smtpTo;

    private boolean ready = false;

    // Location of the user, one per location
    private String templateFileLocation;

    // This internal map is of all the variables defined in the template.
    // The user should retrieve this, set their values there and pass it back.
    private EmailContentMap emailContentMap;

    public CFEmailNotifier(ConfigurationManager config)
	    throws Base64DecodingException {
	init(config);
    }

    /**
     * Populates the internal value map containing only those variables that
     * have specifically been provided in the template.
     *
     * @param projectInfo
     * @param templateString
     *            **Absolute Path** of the template file.
     * @throws CommonFrameworkException
     */
    @Override
    public EmailContentMap configureContentMap(String templateFileLocation)
	    throws CommonFrameworkException {
	emailContentMap = new EmailContentMap();
	this.templateFileLocation = templateFileLocation;
	try {

	    String templateBody = getBodyTemplate();

	    // Run through our template and find all the variables
	    // We are looking for all Velocity templates defined as: ${var}
	    String regExPattern = "\\$\\{(\\s*?.*?)*?\\}";
	    Pattern pattern = Pattern.compile(regExPattern);
	    Matcher matcher = pattern.matcher(templateBody);

	    // Find all matches
	    while (matcher.find()) {
		// Get the matching string
		String match = matcher.group();
		String strippedDownVariable = StringUtils.substringBetween(
			match, "${", "}");
		log.debug("Found template match: " + strippedDownVariable);
		strippedDownVariable = strippedDownVariable.trim();
		emailContentMap.put(strippedDownVariable, null);
	    }

	} catch (Exception e) {
	    ready = false;
	    log.error("Problem parsing template: " + e.getMessage());
	}

	return emailContentMap;

    }

    private void init(ConfigurationManager config) {
	EmailBean emailConfig = config.getEmailConfiguration();
	if (!emailConfig.isEmailConfigured()) {
	    log.warn("Email configuration fields not set, no email configured.");
	    return; // User didn't configure email, so no email notifications
	}
	// These are the basic required fields
	smtpHost = emailConfig.getSmtpAddress();
	smtpFrom = emailConfig.getSmtpFrom();
	smtpTo = emailConfig.getSmtpTo();

	smtpPassword = null;
	smtpUsername = null;
	smtpUseAuth = emailConfig.isUseAuth();
	// only obtain username and password if useAuth set to true
	if (smtpUseAuth) {
	    smtpUsername = emailConfig.getAuthUserName();
	    smtpPassword = emailConfig.getAuthPassword();
	}

	smtpPort = emailConfig.getSmtpPort();
	smtpProtocol = emailConfig.getEmailProtocol();

	ready = true;
    }

    /**
     * Informs the user whether the Emailer is ready to be used!
     *
     * @return
     */
    public boolean isConfigured() {
	return ready;
    }

    /**
     * Sends the email.
     *
     * @param projectInfo
     *            - Protex project
     * @param content
     *            - Content map retrieved from configuration step
     * @param rules
     *            - Optional rules that govern whether an email should be sent
     * @return
     * @throws CommonFrameworkException
     */
    @Override
    public EmailTemplate sendEmail(ProjectPojo projectInfo,
	    EmailContentMap content, List<EmailTriggerRule> rules)
	    throws CommonFrameworkException {
	if (!ready) {
	    return null;
	}

	boolean areRulesTriggered = checkRules(rules);

	if (areRulesTriggered) {
	    EmailTemplate emailTemplate = generateEmail(projectInfo, content);

	    send(emailTemplate);
	    log.info("Email sent complete to user: " + smtpTo);

	    return emailTemplate;
	} else {
	    log.info("Trigger rules were created, but none triggered - no email[s] sent.");
	    return null;
	}

    }

    private boolean checkRules(List<EmailTriggerRule> userProvidedRules) {
	boolean ruleValidator = false;
	if (userProvidedRules == null || userProvidedRules.size() == 0) {
	    log.debug("Rules are empty, defaulting to true for all rules");
	    ruleValidator = true;
	} else {
	    for (EmailTriggerRule rule : userProvidedRules) {
		if (rule.isRuleTriggered()) {
		    ruleValidator = true;
		    log.info("Email rule triggered, for rule name: "
			    + rule.getRuleName());
		}
	    }
	}

	return ruleValidator;
    }

    private EmailTemplate generateEmail(ProjectPojo projectInfo,
	    EmailContentMap map) {
	Reader reader = null;

	try {
	    reader = new InputStreamReader(new FileInputStream(
		    templateFileLocation));
	} catch (FileNotFoundException e1) {
	    log.error(e1.getMessage());
	}

	VelocityContext velContext = initializeVelocityContext(projectInfo, map);

	EmailTemplate emailTemplate = evaluateVelocityContext(velContext,
		reader);

	try {
	    reader.close();
	} catch (IOException e) {
	    log.error("Error closing reader", e);
	}

	return emailTemplate;
    }

    /**
     * Deprecating. There should not be a whole new method just for this One
     * method should be able to handle ALL template files.
     */
    @Override
    @Deprecated
    public void sendErrorNotification(Exception e, String message,
	    String templateFile, String projectName) {
	if (!ready) {
	    return;
	}
	String errorNotificationsString = null;
	try {
	    errorNotificationsString = getBodyTemplate();
	} catch (Exception e1) {
	    log.error("Unable to convert template to string: "
		    + e1.getMessage());
	    return;
	}
	if (errorNotificationsString == null) {
	    log.warn("Unable to initialize email notifications." + templateFile
		    + " was not readable.");
	    return; // Won't try to do email notifications without both of these
	}

	Reader reader = null;

	reader = new InputStreamReader(
		IOUtils.toInputStream(errorNotificationsString));

	VelocityContext velContext = initializeVelocityContext(e, message,
		projectName);

	EmailTemplate notification = evaluateVelocityContext(velContext, reader);

	send(notification);
    }

    // initializes variables for the project completion notification
    private VelocityContext initializeVelocityContext(ProjectPojo projectInfo,
	    EmailContentMap contentMap) {
	VelocityContext velContext = new VelocityContext();

	// Preload non dynamic values
	contentMap.put("smtpFrom", smtpFrom);
	contentMap.put("smtpTo", smtpTo);
	if (projectInfo.getProjectName() != null) {
	    contentMap.put("projectName", projectInfo.getProjectName());
	}

	// Load all the values

	Set<String> ks = contentMap.keySet();

	for (String key : ks) {
	    String value = contentMap.get(key);
	    velContext.put(key, value);

	}

	return velContext;
    }

    // initializes variables for the error notification
    private VelocityContext initializeVelocityContext(Exception e,
	    String message, String projectName) {
	VelocityContext velContext = new VelocityContext();

	velContext.put("projectName", projectName);
	velContext.put("errorMessage", message);
	velContext.put("stackTrace", getStackTraceAsString(e));

	return velContext;

    }

    private String getStackTraceAsString(Throwable aThrowable) {
	final Writer result = new StringWriter();
	final PrintWriter printWriter = new PrintWriter(result);
	aThrowable.printStackTrace(printWriter);
	return result.toString();
    }

    // substitutes the values for the variables that appear in the template file
    private EmailTemplate evaluateVelocityContext(VelocityContext velContext,
	    Reader reader) {
	EmailTemplate notification = null;
	StringWriter writer = new StringWriter();

	try {
	    boolean evaluted = Velocity
		    .evaluate(velContext, writer, "", reader);
	    if (!evaluted) {
		log.warn("Velocity unable to evaluate template");
	    }
	} catch (Exception e1) {
	    log.error(
		    "Failure to evaluate the Velocity expressions onto template file",
		    e1);
	    return null;
	}

	try {
	    notification = EmailTemplateDigester
		    .getEmailTemplate(new ByteArrayInputStream(writer
			    .toString().getBytes()));
	} catch (IOException e1) {
	    log.error("Failure to apply digester on email message", e1);
	    return null;
	} catch (SAXException e1) {
	    log.error("Failure to apply digester on email message", e1);
	    return null;
	}

	return notification;
    }

    /**
     * This is the actual java mail execution.
     *
     * @param notification
     */
    private void send(EmailTemplate notification) {

	String from = notification.getFrom();
	String to = notification.getTo();
	String subject = notification.getSubject();
	String style = notification.getStyle();

	// body of the email is set and all substitutions of variables are made
	String body = notification.getBody();

	// Get system properties
	Properties props = System.getProperties();

	// Setup mail server

	props.put("mail.smtp.host", smtpHost);
	props.put("mail.smtps.port", smtpPort);
	props.put("mail.smtps.auth", smtpUseAuth ? "true" : "false");

	// if (smtpUseAuth) {
	//
	// props.setProperty("mail.smtp.auth", smtpUseAuth + "");
	// props.setProperty("mail.username", smtpUsername);
	// props.setProperty("mail.password", smtpPassword);
	//
	// }

	// Get the default Session object.
	Session session = Session.getDefaultInstance(props);

	// Create a default MimeMessage object.
	MimeMessage message = new MimeMessage(session);

	try {
	    // Set the RFC 822 "From" header field using the
	    // value of the InternetAddress.getLocalAddress method.
	    message.setFrom(new InternetAddress(from));
	    // Add the given addresses to the specified recipient type.
	    message.addRecipients(Message.RecipientType.TO,
		    InternetAddress.parse(to));

	    // Set the "Subject" header field.
	    message.setSubject(subject);

	    // Sets the given String as this part's content,
	    // with a MIME type of "text/html".
	    message.setContent(style + body, "text/html");

	    if (smtpUseAuth) {
		// Send message
		Transport tr = session.getTransport(smtpProtocol);
		tr.connect(smtpHost, smtpPort, smtpUsername, smtpPassword);

		message.saveChanges();
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
	    } else {
		Transport.send(message);
	    }
	} catch (AddressException e) {
	    log.error("Email Exception: Cannot connect to email host: "
		    + smtpHost, e);
	} catch (MessagingException e) {
	    log.error("Email Exception: Cannot send email message", e);
	}

    }

    /**
     * Reads the user provided template file and converts to a string
     *
     * @return
     * @throws Exception
     */
    private String getBodyTemplate() throws Exception {

	String templateBody = null;
	try (FileInputStream fis = new FileInputStream(new File(
		templateFileLocation))) {
	    templateBody = IOUtils.toString(fis);
	} catch (IOException e) {
	    throw new Exception(
		    "Error converting template file to string, cannot send emails",
		    e);
	}

	return templateBody;
    }

    /**
     * Get the EmailContentMap that has been configured.
     *
     * @return
     * @throws CommonFrameworkException
     */
    public EmailContentMap getEmailContentMap() throws CommonFrameworkException {
	if (emailContentMap == null) {
	    throw new CommonFrameworkException("No content map configured!");
	}
	return emailContentMap;
    }
}
