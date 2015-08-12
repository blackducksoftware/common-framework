package soleng.framework.test;

import soleng.framework.core.config.ConfigConstants;
import soleng.framework.core.config.ConfigurationManager;

/**
 * Concrete configmanager bean
 * @author akamen
 *
 */
public class TestConfigurationManagerBean extends ConfigurationManager {

	public TestConfigurationManagerBean(String configFileLocation, ConfigConstants.APPLICATION app)  {
		super(configFileLocation, app);
	}	
}
