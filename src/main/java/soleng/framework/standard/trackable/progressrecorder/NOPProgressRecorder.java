package soleng.framework.standard.trackable.progressrecorder;


// TODO: Auto-generated Javadoc
/**
 * This ProgressRecorder does nothing.
 * It is useful when your class sometimes uses a ProgressRecorder (say, when it's run from
 * a web app) and sometimes doesn't (when it's run standalone). Initialize the ProgressRecorder
 * field to an instance of NOPProgressRecorder to ensure the code will work even when no
 * one calls the setProgressRecorder method.
 * @author sbillings
 *
 */
public class NOPProgressRecorder extends AbstractProgressRecorder {
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.progressrecorder.AbstractProgressRecorder#reportProgress(int, int, int)
	 */
	protected void reportProgress(int percentComplete, int completedSoFar, int totalToComplete) {
	}
}
