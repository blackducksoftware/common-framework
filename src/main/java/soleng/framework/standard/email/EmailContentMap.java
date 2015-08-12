package soleng.framework.standard.email;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The map of keys that are defined in the user specified template.
 * @author akamen
 *
 */
public class EmailContentMap extends HashMap<String, String> {
	static Logger log = LoggerFactory.getLogger(EmailContentMap.class);

	private static final long serialVersionUID = 1L;

	public EmailContentMap()  {
		super();
	}
	

	/**
	 * Place the value of the key within your template
	 * @throws IllegalArgumentException if key does not exist.
	 */
	@Override
	public String put(String key, String value)
	{
		return super.put(key, value);
	}

	/**
	 * Checks to see if the template contains your key
	 * @param key
	 * @return
	 */
	public Boolean doesMapContainKey(String key)
	{
		log.debug("Checking if key exists: " + key);
		if(super.containsKey(key))
		{
			log.debug("Key exists.");
			return true;
		}
		else 
		{
			log.debug("Key does not exist.");
			return false;
		}
	}
	
}
