package soleng.framework.standard.codecenter.pojo;


public class ComponentPojoImpl implements ComponentPojo {
	
	private static String NEW_LINE = System.getProperty("line.separator");
	
	private String id;
	private String name;
	private String version;
	private String kbComponentId; // Might be null
	
	public ComponentPojoImpl(String id, String name, String version, String kbComponentId) {
		this.id = id;
		
		if (name == null) {
			this.name = "";
		} else {
			this.name = name.trim();
		}
		
		if (version == null) {
			this.version = "";
		} else {
			this.version = version.trim();
		}
		
		this.kbComponentId = kbComponentId;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentPojo#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentPojo#getName()
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentPojo#getVersion()
	 */
	public String getVersion() {
		return version;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.codecenter.pojo.IComponentPojo#getKbComponentId()
	 */
	public String getKbComponentId() {
		return kbComponentId;
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
		ComponentPojoImpl other = (ComponentPojoImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(ComponentPojo otherCompPojo) {
		return this.toString().compareTo(otherCompPojo.toString());
	}
		
	/**
	 * compareTo() uses toString() for comparisons (for sorting)
	 */
	@Override
	public String toString() {	
		StringBuilder sb = new StringBuilder();
		sb.append("Component: name = ");
		sb.append(name);
		sb.append(", version = ");
		sb.append(version);
		return sb.toString();
    }	

}
