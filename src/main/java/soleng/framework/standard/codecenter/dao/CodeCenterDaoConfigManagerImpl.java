/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.codecenter.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.core.config.ConfigurationManager;
import soleng.framework.core.config.ConfigConstants.APPLICATION;
import soleng.framework.standard.datatable.FieldType;

public class CodeCenterDaoConfigManagerImpl extends ConfigurationManager implements CodeCenterDaoConfigManager {

	private static Logger log = LoggerFactory.getLogger(CodeCenterDaoConfigManagerImpl.class);
	private int estNumApps = 100;
	private List<String> applicationAttributeNames = new ArrayList<String>(8);
	private String ccDbServerName;
	private int ccDbPort=0;
	private String ccDbUser;
	private String ccDbPassword;
	private boolean skipNonKbComponents=true;
	
	
	public CodeCenterDaoConfigManagerImpl(Properties props) {
		super(props, APPLICATION.CODECENTER);
		loadAdditionalProperties();
		loadApplicationAttributeNames();
	}
	
	public CodeCenterDaoConfigManagerImpl(String configFilename) {
		super(configFilename, APPLICATION.CODECENTER);
		loadAdditionalProperties();
		loadApplicationAttributeNames();
	}
	
	private void loadAdditionalProperties() {
		try {
			ccDbServerName = super.getOptionalProperty("cc.database.server.name");
			String ccDbPortString = super.getOptionalProperty("cc.database.port");
			if (ccDbPortString != null) {
				ccDbPort = Integer.parseInt(ccDbPortString);
			}
			ccDbUser = super.getOptionalProperty("cc.database.user.name");
			ccDbPassword = super.getOptionalProperty("cc.database.password");
			
			String estNumApplications = super.getOptionalProperty("est.number.applications");
			if (estNumApplications != null) {
				this.estNumApps = Integer.parseInt(estNumApplications);
			}
			
			String skipNonKbComponentsString = super.getOptionalProperty("skip.non.kb.components");
			if ("false".equalsIgnoreCase(skipNonKbComponentsString)) {
				skipNonKbComponents = false;
			}
		} catch (IllegalArgumentException e) {
			log.error("A required attribute is missing from the configuration file: " + e.getMessage());
			throw e;
		}
	}
	
	private void loadApplicationAttributeNames() {

		String appCustAttrsString = super.getOptionalProperty("custom.attributes.application");
		if (appCustAttrsString == null) {
			return;
		}
		String[] appCustAttrs = appCustAttrsString.split(",\\s*");
		
		for (String appCustAttr : appCustAttrs) {
			addApplicationAttribute(appCustAttr);
		}
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#getEstNumApps()
	 */
	public int getEstNumApps() {
		return estNumApps;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#setEstNumApps(int)
	 */
	public void setEstNumApps(int estNumApps) {
		this.estNumApps = estNumApps;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#addApplicationAttribute(java.lang.String)
	 */
	public void addApplicationAttribute(String attrName) {
		applicationAttributeNames.add(attrName);
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#getApplicationAttributeNames()
	 */
	public List<String> getApplicationAttributeNames() {
		return applicationAttributeNames;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#getCcDbServerName()
	 */
	public String getCcDbServerName() {
		return ccDbServerName;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#setCcDbServerName(java.lang.String)
	 */
	public void setCcDbServerName(String dbServer) {
		this.ccDbServerName = dbServer;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#getCcDbPort()
	 */
	public int getCcDbPort() {
		return ccDbPort;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#setCcDbPort(int)
	 */
	public void setCcDbPort(int dbPort) {
		this.ccDbPort = dbPort;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#getCcDbUserName()
	 */
	public String getCcDbUserName() {
		return ccDbUser;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#setCcDbUserName(java.lang.String)
	 */
	public void setCcDbUserName(String dbUser) {
		this.ccDbUser = dbUser;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#getCcDbPassword()
	 */
	public String getCcDbPassword() {
		return ccDbPassword;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.dao.CodeCenterDaoConfigManager#setCcDbPassword(java.lang.String)
	 */
	public void setCcDbPassword(String dbPassword) {
		this.ccDbPassword = dbPassword;
	}

	public boolean isSkipNonKbComponents() {
		return skipNonKbComponents;
	}
	
}
