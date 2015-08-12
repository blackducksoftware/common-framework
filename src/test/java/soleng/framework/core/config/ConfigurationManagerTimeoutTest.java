package soleng.framework.core.config;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import soleng.framework.core.config.ConfigConstants;
import soleng.framework.core.config.testbeans.TestProtexConfigurationManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationManagerTimeoutTest.
 */
public class ConfigurationManagerTimeoutTest 
{
	
	/** The tcm. */
	private static TestProtexConfigurationManager tcm = null;	
	
	/** The test file. */
	private static String testFile = "src/test/resources/test_config_timeout.properties";
		
	/** The exception. */
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	 * Sets the up before class.
	 */
	@BeforeClass static public void setUpBeforeClass()	
	{

		
		tcm = new TestProtexConfigurationManager(testFile);		
	}
	
	/**
	 * Tests sdk timeout.
	 */
	@Test
	public void testTimeout()
	{	
		try{
		
			String timeOutString = tcm.getProperty("protex" + "." + ConfigConstants.SDK_TIMEOUT_SUFFIX);
			Long timeOut = tcm.getSdkTimeOut();
			
			Assert.assertEquals("123", timeOutString);
			Assert.assertEquals(123L, timeOut.longValue());

		} catch (Exception e)
		{
			Assert.fail(e.getMessage());
		}
	}
	
}

