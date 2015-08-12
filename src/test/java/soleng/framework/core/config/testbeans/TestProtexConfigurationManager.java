package soleng.framework.core.config.testbeans;

import java.io.InputStream;
import java.util.Properties;

import soleng.framework.core.config.ConfigurationManager;
import soleng.framework.core.config.user.CommonUser;

// TODO: Auto-generated Javadoc
/**
 * Extended test config class for testing purposes only.
 * Since we cannot test an abstract class, we will test its inherited class.
 * 
 * This tests the Protex Config Manager only
 * 
 * @author akamen
 *
 */
public class TestProtexConfigurationManager extends ConfigurationManager {

	/**
	 * Instantiates a new test protex configuration manager.
	 *
	 * @param configFileLocation the config file location
	 */
	public TestProtexConfigurationManager(String configFileLocation)  {
		super(configFileLocation, APPLICATION.PROTEX);
	}
	
	
	/**
	 * Instantiates a new test protex configuration manager.
	 *
	 * @param user the user
	 */
	public TestProtexConfigurationManager(CommonUser user)  {
		super(user, APPLICATION.PROTEX);
	}
	
	/**
	 * Instantiates a new test protex configuration manager.
	 *
	 * @param is the is
	 */
	public TestProtexConfigurationManager(InputStream is) {
		super(is, APPLICATION.PROTEX);
	}

	/**
	 * Instantiates a new test protex configuration manager.
	 *
	 * @param props the props
	 */
	public TestProtexConfigurationManager(Properties props) {
		super(props, APPLICATION.PROTEX);
	}
}
