package soleng.framework.standard.trackable.progressrecorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/** 
 * This ProgressRecorder writes progress to the log file.
 * @author sbillings
 *
 */
public class LoggingProgressRecorder extends AbstractProgressRecorder {
	
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(LoggingProgressRecorder.class);

	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.progressrecorder.AbstractProgressRecorder#reportProgress(int, int, int)
	 */
	protected void reportProgress(int percentComplete, int completedSoFar, int totalToComplete) {
		String msg = "PercentComplete: " + percentComplete + 
				" [" + completedSoFar + "/" + totalToComplete + "]";
		log.info(msg);
	}

}
