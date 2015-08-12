package soleng.framework.core.config.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParser;

import soleng.framework.core.config.ConfigConstants;
import soleng.framework.core.config.server.parser.IGenericServerParser;
import soleng.framework.core.config.server.parser.JSONParser;
import soleng.framework.core.config.server.parser.XMLParser;
import soleng.framework.core.config.server.parser.YAMLParser;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerConfigurationParser.
 */
public class ServerConfigurationParser {

	/** The log. */
	static Logger log = LoggerFactory
			.getLogger(ServerConfigurationParser.class);

	/** The file name. */
	private String fileName = null;

	private static List<ServerBean> serverList = new ArrayList<ServerBean>();

	/**
	 * The Enum FILE_TYPE.
	 */
	private enum FILE_TYPE {
		/** The json. */
		JSON,
		/** The xml. */
		XML,
		/** The yaml. */
		YAML
	};

	/**
	 * Instantiates a new server configuration parser.
	 * 
	 * @param fileName
	 *            the file name
	 */
	public ServerConfigurationParser(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Process server configuration.
	 * 
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ServerBean> processServerConfiguration() throws Exception {
		IGenericServerParser serverParser = null;

		try (FileReader fileReader = new FileReader(fileName)) {
			FILE_TYPE fileType = determineFileType();

			if (fileType == FILE_TYPE.XML) {
				serverParser = new XMLParser();
			} else if (fileType == FILE_TYPE.YAML) {
				serverParser = new YAMLParser();
			} else if (fileType == FILE_TYPE.JSON) {
				serverParser = new JSONParser();
			}

			serverList = serverParser.processServerConfiguration(fileReader);
			fileReader.close();

		} catch (Exception e) {
			log.error("Unable to process server configuration file", e);
		}

		return serverList;
	}

	public List<ServerBean> getServerListByApplication(
			ConfigConstants.APPLICATION appName) {
		List<ServerBean> serverListByName = new ArrayList<ServerBean>();

		for (ServerBean bean : serverList) {
			if (bean.getApplication() == appName) {
				serverListByName.add(bean);
			}
		}

		return serverListByName;
	}

	/**
	 * Determine file type.
	 * 
	 * @return the file type
	 */
	private FILE_TYPE determineFileType() {
		// Our default return
		FILE_TYPE returnType = FILE_TYPE.XML;
		try (FileReader fileReader = new FileReader(fileName)) {
			// Replacing java Files nio package with Tika
			// Part of https://jira.blackducksoftware.com/browse/SECF-113
			File f = new File(fileName);
			Tika tika = new Tika();
			String fileTypeStr = tika.detect(f);
			log.debug("Tika detected configuration content as: " + fileTypeStr);
			if (fileTypeStr != null) {
				if (fileTypeStr.contains(FILE_TYPE.XML.toString().toLowerCase())) {
					returnType = FILE_TYPE.XML;
				}
				// If that did not work, it may be either JSON or YAML
				else if (fileTypeStr.contains(FILE_TYPE.JSON.toString().toLowerCase())) {
					try {
						new JsonParser().parse(fileReader);
						returnType =  FILE_TYPE.JSON;
					} catch (Exception jsonException) {
						// ignore
					}
				} else if (fileTypeStr.contains(FILE_TYPE.YAML.toString().toLowerCase())) {
					// If we are still here it is most likely YAML
					log.debug("Detected file type: " + fileTypeStr);
					returnType =  FILE_TYPE.YAML;
				}
			} else {
				// Default, go with XML
				return returnType;
			}

		} catch (IOException ioe) {
			throw new IllegalArgumentException("Unable to determine file type",
					ioe);
		}

		return returnType;
	}

}
