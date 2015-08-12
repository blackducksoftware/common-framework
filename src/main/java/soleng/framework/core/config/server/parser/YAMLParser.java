package soleng.framework.core.config.server.parser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import soleng.framework.core.config.ConfigConstants;
import soleng.framework.core.config.server.ServerBean;
import soleng.framework.core.config.server.ServerBeanList;


/**
 * Engine to process the supplied YAML configuration file
 * Returns a list of processed beans, throws run time exception upon failure.
 * 
 * NOTE:  YAML does not allow for XStream-style annotations.  Thus it is currently
 * not possible to map our methods to property names.  So the YAML config file must
 * have properties that can be reflectively determined.
 * TODO:  Find a way around this if YAML is to be used effectively.
 * 
 * @author akamen
 *
 */
public class YAMLParser extends ConfigConstants implements IGenericServerParser
{
	
	/** The log. */
	static Logger log = LoggerFactory.getLogger(YAMLParser.class);

	/* (non-Javadoc)
	 * @see soleng.framework.core.config.server.parser.IGenericServerParser#processServerConfiguration(java.io.FileReader)
	 */
	public List<ServerBean> processServerConfiguration(FileReader fileReader) 
	{
		List<ServerBean> servers = new ArrayList<ServerBean>();
		Yaml yamlLoader = null;

		try
		{

	        TypeDescription descr = new TypeDescription(ServerBeanList.class);
	        descr.putListPropertyType(ConfigConstants.SERVERS_PROPERTY, ServerBean.class);
	        yamlLoader = new Yaml(new Constructor(descr));
	        ServerBeanList serverList = yamlLoader.loadAs(fileReader, ServerBeanList.class);
	        			
			servers = serverList.getServers();


		} catch (Exception e)
		{
			log.error("Configuration pasing error", e);
			throw new IllegalArgumentException("Failed to parse configuration file: " + e.getMessage());
		}
	
		
		return servers;
		
	}

}
