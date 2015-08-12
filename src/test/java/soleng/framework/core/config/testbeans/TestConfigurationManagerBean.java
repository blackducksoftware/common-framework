package soleng.framework.core.config.testbeans;

import soleng.framework.core.config.ConfigConstants;
import soleng.framework.core.config.ConfigurationManager;
import soleng.framework.core.config.ConfigConstants.APPLICATION;

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
