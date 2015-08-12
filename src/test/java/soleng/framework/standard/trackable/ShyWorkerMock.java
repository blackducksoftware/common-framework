package soleng.framework.standard.trackable;

import soleng.framework.standard.trackable.progressrecorder.ProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class ShyWorkerMock.
 */
public class ShyWorkerMock implements Trackable {
	
	/** The progress recorder. */
	private ProgressRecorder progressRecorder;
	
	/** The configuration. */
	private String configuration=null;
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.Trackable#setProgressRecorder(soleng.framework.standard.trackable.progressrecorder.ProgressRecorder)
	 */
	public void setProgressRecorder(ProgressRecorder progressRecorder) {
		this.progressRecorder = progressRecorder;
	}
	
	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.Trackable#runAndTrack()
	 */
	public void runAndTrack() {
		// The worker must initialize the recorder
		progressRecorder.setTotalToComplete(10); // Define "done"; how many are we doing?
		progressRecorder.setGranularity(5); // Set the granulary (5 = every 5%)
		
		// Simulate the (slow) work
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				break;
			}
			// Normally: Each time we complete one, tell the recorder how many we've completed
			// However, this worker will neglect the updates
			// We want to degrade the progress service gracefully (be innaccurate) in this case, rather than failing
			// progressRecorder.setCompletedSoFar(i+1);
		}
		progressRecorder.setDone(); // Declare all the work done (even though we neglected the updates
	}
	
	// The reset of the methods are not part of the Trackable interface
	// but real worker classes may need other methods (or constructors/factories) to
	// enable them to be configured.
	/**
	 * Sets the configuration.
	 *
	 * @param configuration the new configuration
	 */
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public String getConfiguration() {
		return configuration;
	}
}
