package soleng.framework.core.config.server.parser;

import java.io.FileReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.core.config.server.ServerBean;
import soleng.framework.core.config.server.ServerBeanList;

import com.thoughtworks.xstream.XStream;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLParser.
 */
public class XMLParser implements IGenericServerParser {

	/** The log. */
	static Logger log = LoggerFactory.getLogger(XMLParser.class);

	/* (non-Javadoc)
	 * @see soleng.framework.core.config.server.parser.IGenericServerParser#processServerConfiguration(java.io.FileReader)
	 */
	public List<ServerBean> processServerConfiguration(FileReader fileReader) 
	{
		ServerBeanList serverBeanList = null;
		
		
		XStream xstream = new XStream();
		
		try
		{
			xstream.processAnnotations(ServerBean.class);
			xstream.processAnnotations(ServerBeanList.class);

			serverBeanList =  (ServerBeanList)xstream.fromXML(fileReader);
	        
	        log.debug("Deserialized XML");
		
		} catch (Exception e) {
			log.error("Unable to process XML", e);
		}
			
		return serverBeanList.getServers();
	}

}
