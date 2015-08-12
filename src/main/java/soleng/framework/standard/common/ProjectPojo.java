package soleng.framework.standard.common;

// TODO: Auto-generated Javadoc
/**
 * A generic and simple object that represents a project.
 * @author akamen
 *
 */
public abstract class ProjectPojo {

	/**
	 * Instantiates a new project pojo.
	 */
	public ProjectPojo() 
	{
		
	}
	
	/**
	 * Gets the project name.
	 *
	 * @return the project name
	 */
	public abstract String getProjectName();
	
	/**
	 * Gets the project key.
	 *
	 * @return the project key
	 */
	public abstract String getProjectKey();
	
	/**
	 * When was this project last created/analyzed in the BDS system?
	 * @return Pretty format date MM/dd/yyyy
	 */
	public abstract String getAnalyzedDate();
}
