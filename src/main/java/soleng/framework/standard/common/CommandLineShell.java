package soleng.framework.standard.common;

import java.util.List;

import soleng.framework.standard.protex.ProtexProjectPojo;

// TODO: Auto-generated Javadoc
/**
 * The Interface CommandLineShell.
 */
public interface CommandLineShell 
{

	/**
	 * Launches an interactive command line window
	 * Asks the user to select the project and returns the selection.
	 * 
	 * User {@link soleng.framework.protex.ProtexWrapper }  to retrieve the list of projects.
	 *
	 * @param projectList the project list
	 * @return the project pojo
	 * @throws Exception the exception
	 */
	public ProtexProjectPojo runProjectSelector(List<ProtexProjectPojo> projectList) throws Exception;
	
}
