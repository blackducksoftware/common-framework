package soleng.framework.core.config;

import java.io.ByteArrayInputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import soleng.framework.core.config.testbeans.TestProtexConfigurationManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationManagerIsTest.
 */
public class ConfigurationManagerIsTest 
{
	
	/** The tcm. */
	private static TestProtexConfigurationManager tcm = null;	
		
	/** The exception. */
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass static public void setUpBeforeClass() throws Exception
	{
		String propsString = "protex.server.name=myserver\n" +
							"protex.user.name=userName\n" +
							"protex.password=blackDuck\n";
		
		tcm = new TestProtexConfigurationManager(new ByteArrayInputStream(propsString.getBytes("UTF-8")));		
	}
	
	/**
	 * Tests configuration manager when an input stream is provided.
	 */
	@Test
	public void testInitializerWithGoodFileAndGoodParams()
	{	
		try{
			String server = tcm.getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
			String user = tcm.getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
			String password = tcm.getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);
			
			Assert.assertEquals("myserver", server);
			Assert.assertEquals("userName", user);
			Assert.assertEquals("blackDuck", password);
			
		} catch (Exception e)
		{
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * Tests getProps() method.
	 */
	@Test
	public void testGetProps()
	{	
		try{
			Properties props = tcm.getProps();
			
			String server = props.getProperty(ConfigConstants.PROTEX_SERVER_NAME_PROPERTY);
			String user = props.getProperty(ConfigConstants.PROTEX_USER_NAME_PROPERTY);
			String password = props.getProperty(ConfigConstants.PROTEX_PASSWORD_PROPERTY);
			
			Assert.assertEquals("myserver", server);
			Assert.assertEquals("userName", user);
			Assert.assertEquals("blackDuck", password);
			
		} catch (Exception e)
		{
			Assert.fail(e.getMessage());
		}
	}
}

