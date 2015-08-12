/**
 * 
 */
package soleng.framework.standard.protex.report.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportRowDataBlock.
 *
 * @author Ari Kamen
 * 
 * Abstract ReportGenerator object to hold an entire row of data
 */
public class ReportRowDataBlock extends HashMap<String, String> implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4325280841768786267L;
	
	/** The row number. */
	private Integer rowNumber;
	
	/** The access map. */
	private HashMap<String, Boolean> accessMap;
	
	
	/**
	 * Instantiates a new report row data block.
	 *
	 * @param row the row
	 */
	public ReportRowDataBlock(Integer row)
	{
		super();
		rowNumber = new Integer(row);
		accessMap = new HashMap<String, Boolean>();
	}
	
	/**
	 * Adds the data point.
	 *
	 * @param dirtyKey the dirty key
	 * @param value the value
	 */
	public void addDataPoint(String dirtyKey, String value)
	{
		super.put(dirtyKey, value);
		accessMap.put(dirtyKey, false);
	}
	
	/**
	 * Sets the row.
	 *
	 * @param row the new row
	 */
	public void setRow(Integer row)
	{
		rowNumber = row;
	}
	
	/**
	 * Gets the data point.
	 *
	 * @param dirtyKey the dirty key
	 * @return the data point
	 */
	public String getDataPoint(String dirtyKey)
	{
		String value = super.get(dirtyKey);
		accessMap.put(dirtyKey, true);
		
		return value;
	}
	
	/**
	 * Gets the row number.
	 *
	 * @return the row number
	 */
	public Integer getRowNumber()
	{
		return rowNumber;
	}
	
	/**
	 * Gets the unused data points.
	 *
	 * @return the unused data points
	 */
	public String getUnusedDataPoints()
	{
		StringBuffer buffer = new StringBuffer();
		Iterator<String> it = accessMap.keySet().iterator();
		while(it.hasNext())
		{
			String key = it.next();
			Boolean bool = accessMap.get(key);
			if(!bool)
			{
				buffer.append("Unused key: " + key + " value: " + super.get(key) +"\n");
			}
		}
		
		return buffer.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractMap#toString()
	 */
	public String toString()
	{
		return super.toString();
	}
}
