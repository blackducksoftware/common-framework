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
package com.blackducksoftware.tools.commonframework.core.gwt.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;

/**
 * The standard login screen, with a logo, server/username/password fields, and
 * a login button.
 * 
 * @author sbillings
 *
 */
public class StandardLoginScreen {

    /** The Constant debugMode. */
    private static final boolean debugMode = false; // in debug mode, version
						    // shown is of this class,
						    // not app

    /** The Constant LOGIN_SCREEN_VERSION_STRING. */
    private static final String LOGIN_SCREEN_VERSION_STRING = "SLS v1.0.1";

    /** The Constant COPYRIGHT_STRING. */
    private static final String COPYRIGHT_STRING = "\u00A9 2014 Black Duck\u00AE and the Black Duck logo are "
	    + "registered trademarks of Black Duck Software, Inc. in the United States and/or other jurisdictions. ";

    /** The browser element id. */
    private final String browserElementId;

    /** The login button. */
    private Button loginButton;

    /** The error label. */
    private Label errorLabel;

    /** The server box. */
    private TextBox serverBox;

    /** The user name box. */
    private TextBox userNameBox;

    /** The password box. */
    private PasswordTextBox passwordBox;

    /** The remember me check box. */
    private CheckBox rememberMeCheckBox;

    /** The user. */
    private final AuthenticationUser user;

    /** The login service. */
    private final LoginService loginService;

    /** The login handler. */
    private final LoginHandler loginHandler;

    /** The server cookie. */
    private final String serverCookie;

    /** The user cookie. */
    private final String userCookie;

    /** The password cookie. */
    private final String passwordCookie;

    /** The Constant SERVER_ERROR. */
    private static final String SERVER_ERROR = "An error occurred while "
	    + "attempting to contact the server. Please check your network "
	    + "connection and try again.";

    /**
     * Construct the login screen.
     *
     * @param clientBrowserElementId
     *            Location in HTML of login screen
     * @param clientLoginService
     *            An object we can use to make the login() RPC call
     * @param clientLoginHandler
     *            The object we should notify upon successful login
     * @param loginInfo
     *            The object to populate with login details and pass back and
     *            forth to server
     * @param title
     *            the title
     * @param appVersionString
     *            A version string for the application
     * @param serverCookie
     *            Cookie: server
     * @param userCookie
     *            Cookie: user
     * @param passwordCookie
     *            Cookie: password
     * @param logoPath
     *            Path to the logo to display
     */
    public StandardLoginScreen(String clientBrowserElementId,
	    LoginService clientLoginService, LoginHandler clientLoginHandler,
	    AuthenticationUser loginInfo, String title,
	    String appVersionString, String serverCookie, String userCookie,
	    String passwordCookie, String logoPath) {
	this.browserElementId = clientBrowserElementId;
	this.user = loginInfo;
	this.loginService = clientLoginService;
	this.loginHandler = clientLoginHandler;
	this.serverCookie = serverCookie;
	this.userCookie = userCookie;
	this.passwordCookie = passwordCookie;

	createLoginScreen(logoPath, appVersionString, title);

	// Add a handler to send the name to the server
	LoginRequestHandler handler = new LoginRequestHandler();
	loginButton.addClickHandler(handler);
	serverBox.addKeyPressHandler(handler);
	userNameBox.addKeyPressHandler(handler);
	passwordBox.addKeyPressHandler(handler);

    }

    /**
     * Sets the error message.
     *
     * @param errorMessage
     *            the new error message
     */
    public void setErrorMessage(String errorMessage) {
	errorLabel.setText(errorMessage);
	errorLabel.setVisible(true);
    }

    /**
     * Creates the login screen.
     *
     * @param logoPath
     *            the logo path
     * @param appVersionString
     *            the app version string
     * @param title
     *            the title
     */
    private void createLoginScreen(String logoPath, String appVersionString,
	    String title) {
	Label titleLabel = buildTitleLabel(title);
	loginButton = buildLoginButton();
	errorLabel = buildErrorLabel();
	RootPanel rootPanel = configureRootPanel(logoPath, titleLabel,
		loginButton, errorLabel);
	AbsolutePanel absolutePanel = buildAbsolutePanel();
	rootPanel.add(absolutePanel, 162, 311);

	Label serverFieldLabel = buildServerLabel();
	absolutePanel.add(serverFieldLabel, 79, 34);

	serverBox = buildServerBox();
	absolutePanel.add(serverBox, 136, 34);

	userNameBox = buildUserNameBox();
	absolutePanel.add(userNameBox, 136, 70);

	Label userFieldLabel = buildUserNameLabel();
	absolutePanel.add(userFieldLabel, 92, 70);

	Label passwordFieldLabel = buildPasswordLabel();
	absolutePanel.add(passwordFieldLabel, 57, 104);

	passwordBox = buildPasswordBox();
	absolutePanel.add(passwordBox, 136, 104);

	rememberMeCheckBox = buildRememberMeCheckBox();
	absolutePanel.add(rememberMeCheckBox, 143, 139);

	Label versionLabel = buildVersionLabel(appVersionString);
	absolutePanel.add(versionLabel, 365, 164);

	Label lblCopyrightBlack = buildCopyrightLabel();
	rootPanel.add(lblCopyrightBlack, 98, 688);

	// Load cookie values, but do not memorize
	loadCookies(false);

    }

    /**
     * Builds the login button.
     *
     * @return the button
     */
    private Button buildLoginButton() {
	Button loginButton = new Button("Send");
	loginButton.setStyleName("gwt-Login-Button");
	loginButton.setText("Login");
	loginButton.setSize("166px", "44px");
	loginButton.addStyleName("sendButton");
	return loginButton;
    }

    /**
     * Builds the error label.
     *
     * @return the label
     */
    private Label buildErrorLabel() {
	Label errorLabel = new Label();
	errorLabel.setStyleName("error-Label");
	errorLabel.setVisible(false);
	errorLabel.setSize("459px", "64px");
	return errorLabel;
    }

    /**
     * Builds the title label.
     *
     * @param title
     *            the title
     * @return the label
     */
    private Label buildTitleLabel(String title) {
	Label titleLabel = new Label();
	titleLabel.setStyleName("appTitle");
	titleLabel.setVisible(true);
	titleLabel.setSize("459px", "64px");
	titleLabel.setText(title);
	return titleLabel;
    }

    /**
     * Configure root panel.
     *
     * @param logoPath
     *            the logo path
     * @param titleLabel
     *            the title label
     * @param loginButton
     *            the login button
     * @param errorLabel
     *            the error label
     * @return the root panel
     */
    private RootPanel configureRootPanel(String logoPath, Label titleLabel,
	    Button loginButton, Label errorLabel) {
	RootPanel rootPanel = RootPanel.get(browserElementId);
	rootPanel.setStyleName("RootPanel");
	rootPanel.setSize("1280", "960");
	Image image = new Image(logoPath);
	rootPanel.add(image, 170, 38);
	rootPanel.add(titleLabel, 162, 260);
	rootPanel.add(loginButton, 309, 520); // was 309, 511
	rootPanel.add(errorLabel, 162, 600); // was 162, 590

	return rootPanel;
    }

    /**
     * Builds the absolute panel.
     *
     * @return the absolute panel
     */
    private AbsolutePanel buildAbsolutePanel() {
	AbsolutePanel absolutePanel = new AbsolutePanel();
	absolutePanel.setStyleName("gwt-Login-MainBox");
	absolutePanel.setSize("450px", "180px"); // was "450px", "174px"
	return absolutePanel;
    }

    /**
     * Builds the server label.
     *
     * @return the label
     */
    private Label buildServerLabel() {
	Label serverFieldLabel = new Label("Server:");
	serverFieldLabel.setStyleName("gwt-Login-Label");
	serverFieldLabel.setSize("51px", "21px");
	return serverFieldLabel;
    }

    /**
     * Builds the server box.
     *
     * @return the text box
     */
    private TextBox buildServerBox() {
	TextBox serverBox = new TextBox();
	serverBox.setStyleName("h1");
	serverBox.setFocus(true);
	String serverValue = debugMode ? "http://seneca.blackducksoftware.com"
		: ".blackducksoftware.com";
	serverBox.setText(serverValue);
	serverBox.setAlignment(TextAlignment.LEFT);
	serverBox.setSize("265px", "20px");
	serverBox.setFocus(true);
	return serverBox;
    }

    /**
     * Builds the user name box.
     *
     * @return the text box
     */
    private TextBox buildUserNameBox() {
	TextBox userNameBox = new TextBox();
	userNameBox.setStyleName("h1");
	userNameBox.setFocus(true);
	String userNameValue = debugMode ? "unitTester@blackducksoftware.com"
		: "@blackducksoftware.com";
	userNameBox.setText(userNameValue);
	userNameBox.setAlignment(TextAlignment.LEFT);
	userNameBox.setSize("265px", "20px");
	return userNameBox;
    }

    /**
     * Builds the user name label.
     *
     * @return the label
     */
    private Label buildUserNameLabel() {
	Label userFieldLabel = new Label("User:");
	userFieldLabel.setStyleName("gwt-Login-Label");
	userFieldLabel.setSize("38px", "21px");
	return userFieldLabel;
    }

    /**
     * Builds the password label.
     *
     * @return the label
     */
    private Label buildPasswordLabel() {
	Label passwordFieldLabel = new Label("Password:");
	passwordFieldLabel.setStyleName("gwt-Login-Label");
	passwordFieldLabel.setSize("73px", "21px");
	return passwordFieldLabel;
    }

    /**
     * Builds the password box.
     *
     * @return the password text box
     */
    private PasswordTextBox buildPasswordBox() {
	PasswordTextBox passwordBox = new PasswordTextBox();
	passwordBox.setStyleName("h1");
	passwordBox.setAlignment(TextAlignment.LEFT);
	passwordBox.setSize("265px", "20px");
	String passwordValue = debugMode ? "blackduck" : "";
	passwordBox.setText(passwordValue);
	return passwordBox;
    }

    /**
     * Builds the remember me check box.
     *
     * @return the check box
     */
    @SuppressWarnings("deprecation")
    private CheckBox buildRememberMeCheckBox() {
	CheckBox rememberMeCheckBox = new CheckBox("Remember me please!");
	rememberMeCheckBox.setStyleName("gwt-Login-RememberMe");
	rememberMeCheckBox.setChecked(true);
	rememberMeCheckBox.setSize("157px", "20px");
	return rememberMeCheckBox;
    }

    /**
     * Builds the version label.
     *
     * @param appVersionString
     *            the app version string
     * @return the label
     */
    private Label buildVersionLabel(String appVersionString) {
	Label versionLabel = new Label("");
	versionLabel.setStyleName("gwt-Version-Label");
	versionLabel.setSize("93px", "36px"); // was "93px", "18px"
	String versionToDisplay = debugMode ? LOGIN_SCREEN_VERSION_STRING
		: appVersionString;
	versionLabel.setText(versionToDisplay);
	return versionLabel;
    }

    /**
     * Builds the copyright label.
     *
     * @return the label
     */
    private Label buildCopyrightLabel() {
	Label lblCopyrightBlack = new Label(COPYRIGHT_STRING);
	lblCopyrightBlack.setSize("592px", "44px");
	return lblCopyrightBlack;
    }

    /**
     * Handler for the clicks that initiate login.
     * 
     * @author sbillings
     *
     */
    private class LoginRequestHandler implements ClickHandler, KeyUpHandler,
	    KeyPressHandler {

	public void onClick(ClickEvent event) {
	    login();
	}

	public void onKeyUp(KeyUpEvent event) {
	    if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		login();
	    }
	}

	public void onKeyPress(KeyPressEvent event) {
	    if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
		login();
	}

	/**
	 * Send the login request to the server and wait for a response.
	 */
	@SuppressWarnings("deprecation")
	public void login() {
	    GWT.log("Login handler login() called");

	    // Load the cookies, but this time memorize (provided checkbox is
	    // marked)
	    loadCookies(true);

	    errorLabel.setText("");
	    user.setServer(serverBox.getValue());
	    user.setUserName(userNameBox.getValue());
	    user.setPassword(passwordBox.getValue());

	    loginButton.setEnabled(false);

	    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");

	    // The client-provided service actually makes the RPC call, but we
	    // get the callback
	    loginService.loginUser(user, new LoginCompleteCallback());

	    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor",
		    "default");
	}
    }

    /**
     * Called when the login() RPC call is complete.
     * 
     * @author sbillings
     *
     */
    private class LoginCompleteCallback implements
	    AsyncCallback<AuthenticationUser> {

	public void onFailure(Throwable caught) {
	    GWT.log("Login screen: loginUser onFailure called");
	    // Show the RPC error message to the user
	    errorLabel.setVisible(true);
	    errorLabel.setText(SERVER_ERROR + caught.getMessage());
	    loginButton.setEnabled(true);
	    GWT.log("Error logging in: " + caught.getMessage());
	}

	public void onSuccess(AuthenticationUser user) {
	    errorLabel.setText("");
	    errorLabel.setVisible(false);
	    GWT.log("Login screen: loginUser onSuccess called");
	    if (user.isAuthenticated()) {
		RootPanel.get(browserElementId).clear();
		// Call the user-provided handler; let 'em know login succeeded
		loginHandler.onSuccess(user);

	    } else {
		GWT.log("Authentication failure: " + user.getErrorMessage());
		errorLabel.setVisible(true);
		errorLabel.setText("Authentication failure: "
			+ user.getErrorMessage());
		loginButton.setEnabled(true);
	    }
	}
    }

    /**
     * Load cookies.
     *
     * @param memorize
     *            the memorize
     */
    private void loadCookies(boolean memorize) {
	loadFieldFromCookie(this.serverBox, serverCookie, memorize);
	loadFieldFromCookie(this.userNameBox, userCookie, memorize);
	loadFieldFromCookie(this.passwordBox, passwordCookie, memorize);
    }

    /**
     * Load field from cookie.
     *
     * @param field
     *            the field
     * @param cookieName
     *            the cookie name
     * @param memorize
     *            the memorize
     */
    private void loadFieldFromCookie(TextBox field, String cookieName,
	    boolean memorize) {
	String cookieValue = Cookies.getCookie(cookieName);
	if (memorize) {
	    if (rememberMeCheckBox.isEnabled()) {
		Cookies.setCookie(cookieName, field.getValue());
	    }
	} else if (cookieValue != null) {
	    field.setValue(cookieValue);
	}
    }
}
