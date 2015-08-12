package soleng.framework.core.config.testbeans;

import java.util.Properties;

import soleng.framework.core.config.ConfigurationManager;

// TODO: Auto-generated Javadoc
/**
 * The Class TestCodeCenterConfigurationManager.
 */
public class TestCodeCenterConfigurationManager extends ConfigurationManager {

	/**
	 * Instantiates a new test code center configuration manager.
	 *
	 * @param configFileLocation the config file location
	 */
	public TestCodeCenterConfigurationManager(String configFileLocation) {
		super(configFileLocation, APPLICATION.CODECENTER);
	}
	/**
	 * Instantiates a new test code center configuration manager.
	 *
	 * @param props the props
	 */
	public TestCodeCenterConfigurationManager(Properties props) {
		super(props, APPLICATION.CODECENTER);
	}
}
