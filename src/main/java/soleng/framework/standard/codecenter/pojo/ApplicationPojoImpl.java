package soleng.framework.standard.codecenter.pojo;

import java.util.Map;

public class ApplicationPojoImpl implements Comparable, ApplicationPojo {
	private String id;
	private String name;
	private String version;
	private String description;
	private Map<String, String> appAttrNameValueMap;
	
	public ApplicationPojoImpl(String id, String name, String version, String description, Map<String, String> appAttrNameValueMap) {
		this.id = id;
		this.name = name.trim();
		this.version = version.trim();
		this.description = description.trim();

		this.appAttrNameValueMap = appAttrNameValueMap;
	}
	
	public ApplicationPojoImpl(String id) {
		this.id = id;
		this.name = "";
		this.version = "";
		this.description = "";
		this.appAttrNameValueMap = null;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IApplicationPojo#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IApplicationPojo#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IApplicationPojo#getVersion()
	 */
	public String getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IApplicationPojo#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IApplicationPojo#getAppAttrNameValueMap()
	 */
	public Map<String, String> getAppAttrNameValueMap() {
		return appAttrNameValueMap;
	}	
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IApplicationPojo#getCustomAttributeValue(java.lang.String)
	 */
	public String getCustomAttributeValue(String customAttributeName) {
		return appAttrNameValueMap.get(customAttributeName);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationPojoImpl other = (ApplicationPojoImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof ApplicationPojo)) {
			return -1;
		}
		ApplicationPojo otherAppPojo = (ApplicationPojo) o;
		return this.name.compareTo(otherAppPojo.getName());
	}
	
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder("ApplicationPojo:  id=");
		sb.append(id);
		sb.append(";  name=");
		sb.append(name);
		sb.append(";  description");
		sb.append(description);
		sb.append(";  version=");
		sb.append(version);
		return sb.toString();
    }
}
