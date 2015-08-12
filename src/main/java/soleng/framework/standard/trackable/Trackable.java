package soleng.framework.standard.trackable;

import soleng.framework.standard.trackable.progressrecorder.ProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * Worker classes that execute long-running tasks can implement this interface to enable
 * clients to monitor progress of that task.
 * @author sbillings
 *
 */
public interface Trackable {
	/**
	 * The TrackableRunner object will call this method to provide the worker with
	 * the ProgressRecorder. The worker should provide periodic updates on progress
	 * to the ProgressRecorder.
	 * @param pr The progress recorder.
	 */
	public void setProgressRecorder(ProgressRecorder pr);
	
	/**
	 * The TrackableRunner object will call this method to run the task.
	 *
	 * @throws Exception the exception
	 */
	public void runAndTrack() throws Exception;
}
