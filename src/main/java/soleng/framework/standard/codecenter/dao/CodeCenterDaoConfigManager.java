/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.codecenter.dao;

import java.util.List;

import soleng.framework.core.config.IConfigurationManager;

public interface CodeCenterDaoConfigManager extends IConfigurationManager {

	@Deprecated
	public int getEstNumApps();
	
	@Deprecated
	public void setEstNumApps(int estNumApps);

	public void addApplicationAttribute(String attrName);

	public List<String> getApplicationAttributeNames();

	@Deprecated
	public String getCcDbServerName();

	@Deprecated
	public void setCcDbServerName(String dbServer);

	@Deprecated
	public int getCcDbPort();

	@Deprecated
	public void setCcDbPort(int dbPort);

	@Deprecated
	public String getCcDbUserName();

	@Deprecated
	public void setCcDbUserName(String dbUser);

	@Deprecated
	public String getCcDbPassword();

	@Deprecated
	public void setCcDbPassword(String dbPassword);
	
	public boolean isSkipNonKbComponents();

}