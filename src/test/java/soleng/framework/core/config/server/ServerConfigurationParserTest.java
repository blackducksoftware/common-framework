package soleng.framework.core.config.server;

import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import soleng.framework.core.config.server.parser.IGenericServerParser;
import soleng.framework.core.config.server.parser.JSONParser;
import soleng.framework.core.config.server.parser.XMLParser;
import soleng.framework.core.config.server.parser.YAMLParser;

// TODO: Auto-generated Javadoc
/**
 * Tests the parsing of various types of configuration files.
 * The basic configuration will test the number of individual sever configurations to determine
 * success fail.
 * 
 * We expect 2,3,4 types of configs for yaml, json, xml respectively.
 * @author akamen
 *
 */
public class ServerConfigurationParserTest {

	/** The test yaml file. */
	public static String testYAMLFile = "server/test_server_config.yaml";
	
	/** The test json file. */
	public static String testJSONFile = "server/test_server_config.json";
	
	/** The test xml file. */
	public static String testXMLFile = "server/test_server_config.xml";

	/** The parser. */
	private ServerConfigurationParser parser = null;
	
	/** The exception. */
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	
	/**
	 * Sets the up before class.
	 */
	@BeforeClass static public void setUpBeforeClass()	
	{
		
	}
	
	/**
	 * Test basic yaml configuration file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testBasicYamlConfigurationFile() throws Exception
	{
		String fullLocation = ClassLoader.getSystemResource(testYAMLFile).getFile();
		parser = new ServerConfigurationParser(fullLocation);
		List<ServerBean> serverList = parser.processServerConfiguration();
		Assert.assertEquals(2, serverList.size());
	}
	
	/**
	 * Test basic json configuration file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testBasicJSONConfigurationFile() throws Exception
	{
		String fullLocation = ClassLoader.getSystemResource(testJSONFile).getFile();
		parser = new ServerConfigurationParser(fullLocation);
		List<ServerBean> serverList = parser.processServerConfiguration();
		Assert.assertEquals(3, serverList.size());
	}
	
	/**
	 * Test basic xml configuration file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testBasicXMLConfigurationFile() throws Exception
	{
		String fullLocation = ClassLoader.getSystemResource(testXMLFile).getFile();
		parser = new ServerConfigurationParser(fullLocation);
		List<ServerBean> serverList = parser.processServerConfiguration();
		Assert.assertEquals(4, serverList.size());
	}

}
