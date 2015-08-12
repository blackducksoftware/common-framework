package soleng.framework.standard.trackable.progressrecorder;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.standard.trackable.ProgressInfo;

// TODO: Auto-generated Javadoc
/**
 * This ProgressRecorder writes progress to the given queue.
 * @author sbillings
 *
 */
public class QueuingProgressRecorder extends AbstractProgressRecorder {
	
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(QueuingProgressRecorder.class);
	
	// Unique to this subclass
	/** The queue. */
	private Queue<ProgressInfo> queue;
	
	/**
	 * Instantiates a new queuing progress recorder.
	 *
	 * @param queue the queue
	 */
	public QueuingProgressRecorder(Queue<ProgressInfo> queue) {
		super();
		this.queue = queue;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.progressrecorder.AbstractProgressRecorder#reportProgress(int, int, int)
	 */
	protected void reportProgress(int percentComplete, int completedSoFar, int totalToComplete) {
			ProgressInfo progressInfo = new ProgressInfo(percentComplete, completedSoFar,
					totalToComplete);
			this.queue.add(progressInfo);
			log.debug("lastReportedPercentComplete: " + percentComplete);
		}
}
