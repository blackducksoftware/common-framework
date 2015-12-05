/*
 * Copyright (C) 2015 Black Duck Software Inc.
 * http://www.blackducksoftware.com/
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of
 * Black Duck Software ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Black Duck Software.
 */
package com.blackducksoftware.tools.commonframework.core.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds the various cookie and header constants needed for the Proxy Objects
 * to establish SSO connection.
 * 
 * The setters will perform the set at the System property level
 * 
 * @author akamen
 * 
 */
public class SSOBean {

    // Constants, placing them here so they can be picked up by the connectors later.
    // SSO Options
    // These are taken directly from the SSL standards, this guarantees compliance
    public final static String SSO_KEY_STORE_PATH = "javax.net.ssl.keyStore";

    public final static String SSO_KEY_STORE_PASSWORD = "javax.net.ssl.keyStorePassword";

    public final static String SSO_KEY_STORE_TYPE = "javax.net.ssl.keyStoreType";

    public final static String SSO_TRUST_STORE_PATH = "javax.net.ssl.trustStore";

    public final static String SSO_TRUST_STORE_TYPE = "javax.net.ssl.trustStoreType";

    public final static String SSO_TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";

    public final static String SSO_AUTH_METHODS = "sso.auth.methods";

    public final static String SSO_COOKIE_AUTH_METHODS = "SUPPORTED_AUTH_METHODS";

    // Initialize all of them so that we don't run into any nulls when setting our Proxies
    private List<String> authenticationMethods = new ArrayList<String>();

    private String keyStorePath = "";

    private String keyStorePassword = "";

    private String keyStoretype = "";

    private String trustStore = "";

    private String trustStoreType = "";

    private String trustStorePassword = "";

    private boolean isInitialized = false;

    public SSOBean()
    {
        String[] defaultSet = new String[] { "MSPKI", "PKI" };
        authenticationMethods = Arrays.asList(defaultSet);

    }

    public List<String> getAuthenticationMethods() {
        return authenticationMethods;
    }

    /**
     * Comma separated list of authentication methods
     * 
     * @param authMethods
     */
    public void setAuthenticationMethods(String authMethods) {
        if (authMethods != null) {
            authenticationMethods = Arrays.asList(authMethods);
        }
    }

    public String getKeyStorePath() {
        return keyStorePath;
    }

    public void setKeyStorePath(String sslKeyStorePath) {
        keyStorePath = sslKeyStorePath;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getTrustStorePath() {
        return trustStore;
    }

    public void setTrustStorePath(String trustStore) {
        this.trustStore = trustStore;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    /**
     * Returns true if bare minimum SSL information is set.
     * 
     * @return
     */
    public boolean isInitialized() {
        if (getKeyStorePassword() != null && getKeyStorePath() != null) {
            if (!getKeyStorePath().isEmpty() && !getKeyStorePassword().isEmpty()) {
                isInitialized = true;
            }
        }

        return isInitialized;
    }

    public String getTrustStoreType() {
        return trustStoreType;
    }

    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    public String getKeyStoreType() {
        return keyStoretype;
    }

    public void setKeyStoreType(String keyStoreStype) {
        keyStoretype = keyStoreStype;
    }

}
