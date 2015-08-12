package soleng.framework.standard.trackable.progressrecorder;

// TODO: Auto-generated Javadoc
/**
 * A ProgressRecorder is used to record progress updates and provide them to users/clients.
 * @author sbillings
 *
 */
public interface ProgressRecorder {
	
	/**
	 * Call this method to tell the progress recorder what "done" is.
	 * "Done" is defined by a pre-determined set of subtasks that must be completed.
	 * Be careful not to set this too low; you don't want completion to be declared
	 * until everything is done. For example, if the worker task needs to read 100 licenses,
	 * you might want to set this number to 101 to give the worker time to finish up
	 * after the last license is read. Then, when everything is done and ready for the
	 * user, call setDone().
	 *
	 * @param totalToExpect the new total to complete
	 */
	public void setTotalToComplete(int totalToExpect);
	
	/**
	 * Set the number of subtasks completed so far.
	 * @param completedSoFar the number of subtasks completed so far.
	 */
	public void setCompletedSoFar(int completedSoFar);
	
	/**
	 * Call this method to increment progress (to tell the ProgressRecorder that more subtasks are done).
	 * @param incrementBy the number to increment the "done so far" number by.
	 */
	public void incrementCompletedSoFar(int incrementBy);
	
	/**
	 * Call this method to adjust how frequently the ProgressReporter reports progress (say, every 5%).
	 * @param granularity Granularity. For example: 5 means: report every 5%.
	 */
	public void setGranularity(int granularity);
	
	/**
	 * Call this method to declare all subtasks done.
	 * It's a good idea to call this, but wait until the last possible second.
	 */
	public void setDone();
}
