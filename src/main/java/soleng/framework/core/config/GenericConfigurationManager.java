/**
 * 
 */
package soleng.framework.core.config;

import java.io.InputStream;

// TODO: Auto-generated Javadoc
/**
 * Generic non-server Configuration Manager
 * Use this for all property files that do not contain server information.
 *
 * @author akamen
 */
public abstract class GenericConfigurationManager extends ConfigurationManager {

	/**
	 * Instantiates a new generic configuration manager.
	 *
	 * @param propertyFile the property file
	 */
	public GenericConfigurationManager(String propertyFile)
	{
		super(propertyFile, APPLICATION.GENERIC);
	}
	
	/**
	 * Instantiates a new generic configuration manager.
	 *
	 * @param is the is
	 */
	public GenericConfigurationManager(InputStream is)
	{
		super(is, APPLICATION.GENERIC);
	}
}
