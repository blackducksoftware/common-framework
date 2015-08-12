package soleng.framework.standard.protex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import soleng.framework.standard.common.CommandLineShell;
import soleng.framework.standard.common.ProjectPojo;

// TODO: Auto-generated Javadoc
/**
 * The Class ProtexCommandLineShell.
 */
public class ProtexCommandLineShell implements CommandLineShell {

	/**
	 * Instantiates a new protex command line shell.
	 */
	public ProtexCommandLineShell()
	{
		
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.common.CommandLineShell#runProjectSelector(java.util.List)
	 */
	public ProtexProjectPojo runProjectSelector(List<ProtexProjectPojo> projectList) throws Exception 
	{
	    ProtexProjectPojo projSelection = null;
		String projectID = null;

		System.out.println("Please provide the number of the project (press 0 to exit):");
		for(int i = 0; i < projectList.size(); i++)
		{
			System.out.println((i+1)+ ". Project: " + projectList.get(i).getProjectName());
		}
		
		int inputSelectionNumber = -1;
		try {
			 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			 String line = br.readLine();
			 line = line.trim();
			 inputSelectionNumber = Integer.parseInt(line);
			 
			 br.close();
			 
			 if(inputSelectionNumber == 0)
			 {
				System.out.println("Bye!");
				System.exit(1);
			 }
			 
		} catch (IOException e1) {
			throw new Exception("Trouble parsing selection: " + e1.getMessage());
		}
		
		{
			if(inputSelectionNumber > projectList.size() || inputSelectionNumber < 1)
			{
				System.err.println("Please provide a valid selection number!");
				System.exit(1);
			}
			
			// Reduce the number by one, because the selection screen was incremented by one.
			inputSelectionNumber = inputSelectionNumber - 1;
			
			projSelection = projectList.get(inputSelectionNumber);
			projectID = projSelection.getProjectKey();
		}
		
		if(projectID == null)
		{
			System.err.println("Unable to determine correct project ID.");
			System.exit(1);
		}
		
		return projSelection;
	}

}
