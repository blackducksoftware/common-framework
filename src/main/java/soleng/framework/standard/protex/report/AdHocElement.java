package soleng.framework.standard.protex.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


// TODO: Auto-generated Javadoc
/**
 * Basic object mapping for an adhoc report
 * Can be used to retrieve all the column,value pairs .
 * 
 *
 * @author akamen
 */
public class AdHocElement implements HocElement {
	
	public static String COUNTER = "counter";
	
	/** The internal map. */
	private HashMap<Integer, String> internalMap = new HashMap<Integer, String>();
	
	/** The internal pair map. */
	private HashMap<String, String> internalPairMap = new HashMap<String, String>();
	
	/** The doc. */
	private Document doc = null;
	
	/**
	 * Instantiates a new ad hoc element.
	 */
	public AdHocElement() {}

	/**
	 * Determines the value of a column, where the position is the column number .
	 *
	 * @param position the position
	 * @param name the name
	 * @throws Exception the exception
	 */
	public void setCoordinate(Integer position, String name) throws Exception
	{
		String someName = internalMap.get(position);
		
		if(someName == null){
			internalMap.put(position, name);
		}
		else {
			throw new Exception("Position and name combination must be unique!  name = " + name);
		}
	}
	
	
	@Override
	public void setCounter(Integer counter) 
	{
		if(internalPairMap != null)
		{
			if(counter != null)
				internalPairMap.put(COUNTER, counter.toString());
		}
	}	
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.protex.report.HocElement#getCoordinate(java.lang.Integer)
	 */
	public String getCoordinate(Integer pos)
	{
		return internalMap.get(pos);
	}
	
	/**
	 * Traditional key/value combination, presumably used after value is pulled from coordinate.
	 *
	 * @param key the key
	 * @param value the value
	 * @throws Exception the exception
	 */
	public void setPair(String key, String value) throws Exception
	{
		internalPairMap.put(key, value);
	}
	
	/**
	 * Gets the pair keys.
	 *
	 * @return the pair keys
	 */
	public Set<String> getPairKeys()
	{
		return internalPairMap.keySet();
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.protex.report.HocElement#getValue(java.lang.String)
	 */
	public String getValue(String key)
	{
		return internalPairMap.get(key);
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.protex.report.HocElement#getSize()
	 */
	public int getSize()
	{
		return internalMap.size();
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.protex.report.HocElement#getInternalValues()
	 */
	public Collection<String> getInternalValues()
	{
		return internalMap.values();
	}

	/**
	 * Returns the JSoup doc element that contains the headers.
	 *
	 * @return the doc
	 */
	public Document getDoc() {
		return doc;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.protex.report.HocElement#setDoc(org.jsoup.nodes.Document, java.lang.String)
	 */
	public void setDoc(Document doc, String RAW_COLUMNS){
		Elements vrows = doc.select(RAW_COLUMNS);
		String headerDoc = vrows.toString();
	    doc = Jsoup.parseBodyFragment(headerDoc);
		this.doc = doc;
	}


}
