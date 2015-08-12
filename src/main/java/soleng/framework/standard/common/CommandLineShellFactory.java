package soleng.framework.standard.common;

import soleng.framework.standard.protex.ProtexCommandLineShell;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating CommandLineShell objects.
 */
public class CommandLineShellFactory 
{

	/**
	 * Gets the command line shell.
	 *
	 * @param app the app
	 * @return the command line shell
	 */
	public static CommandLineShell getCommandLineShell(BlackDuckApplication app)
	{
		CommandLineShell shell = null;
		if(app == BlackDuckApplication.PROTEX)
			shell =  new ProtexCommandLineShell();
		if(app == BlackDuckApplication.CODE_CENTER)
		{
			throw new UnsupportedOperationException();		
		}
		
		return shell;

	}
}
