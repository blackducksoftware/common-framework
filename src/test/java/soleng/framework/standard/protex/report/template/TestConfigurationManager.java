/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.protex.report.template;

import java.io.File;

import soleng.framework.core.config.ConfigurationManager;
import soleng.framework.core.config.GenericConfigurationManager;
import soleng.framework.core.config.ConfigConstants.APPLICATION;

public class TestConfigurationManager extends ConfigurationManager {
	public TestConfigurationManager(File configFile) {
		super(configFile.getAbsolutePath(), APPLICATION.PROTEX);
	}
}
