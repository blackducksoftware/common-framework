package soleng.framework.core.config.server.parser;

import java.io.FileReader;
import java.util.List;

import soleng.framework.core.config.server.ServerBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGenericServerParser.
 */
public interface IGenericServerParser {

	/**
	 * Process server configuration.
	 *
	 * @param fileReader the file reader
	 * @return the list
	 */
	public List<ServerBean> processServerConfiguration(FileReader fileReader);
}
