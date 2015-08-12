package soleng.framework.core.config.server.parser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.core.config.ConfigConstants;
import soleng.framework.core.config.server.ServerBean;

import com.google.gson.Gson;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONParser.
 */
public class JSONParser extends ConfigConstants implements IGenericServerParser {

	/** The log. */
	static Logger log = LoggerFactory.getLogger(YAMLParser.class);

	
	/* (non-Javadoc)
	 * @see soleng.framework.core.config.server.parser.IGenericServerParser#processServerConfiguration(java.io.FileReader)
	 */
	public List<ServerBean> processServerConfiguration(FileReader fileReader) 
	{
		List<ServerBean> servers = new ArrayList<ServerBean>();
		Gson gson = new Gson();

		try
		{
			ServerBean[] serverArray = gson.fromJson(fileReader, ServerBean[].class);
			for(ServerBean bean : serverArray)
			{
				servers.add(bean);
			}
		
		} catch (Exception e) {
			log.error("Unable to parse json", e);
		} 
		
		return servers;
	}

}
