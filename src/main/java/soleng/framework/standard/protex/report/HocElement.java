package soleng.framework.standard.protex.report;

import java.util.Collection;

import org.jsoup.nodes.Document;

// TODO: Auto-generated Javadoc
/**
 * Generic representation of an element derived from parsing the AdHoc Report.
 * 
 * Modified on March 4th, 2015:  Added counter.
 * @author akamen
 */
public interface HocElement {

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	int getSize();

	/**
	 * Gets the internal values.
	 *
	 * @return the internal values
	 */
	public Collection<String> getInternalValues();

	/**
	 * Gets the value.
	 *
	 * @param key the key
	 * @return the value
	 */
	public String getValue(String key);

	/**
	 * Gets the coordinate.
	 *
	 * @param position the position
	 * @return the coordinate
	 */
	public String getCoordinate(Integer position);

	/**
	 * Sets the pair.
	 *
	 * @param string the string
	 * @param string2 the string2
	 * @throws Exception the exception
	 */
	public void setPair(String string, String string2) throws Exception;

	/**
	 * Sets the doc.
	 *
	 * @param doc the doc
	 * @param rAW_COLUMNS the r a w_ columns
	 */
	public void setDoc(Document doc, String rAW_COLUMNS);
	
	/**
	 * Sets the counter for this element
	 * @param counter
	 */
	public void setCounter(Integer counter);

}
