package soleng.framework.standard.trackable;

import soleng.framework.standard.trackable.progressrecorder.ProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class UnresponsiveWorkerMock.
 */
public class UnresponsiveWorkerMock implements Trackable {

	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.Trackable#setProgressRecorder(soleng.framework.standard.trackable.progressrecorder.ProgressRecorder)
	 */
	public void setProgressRecorder(ProgressRecorder progressRecorder) {
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.Trackable#runAndTrack()
	 */
	public void runAndTrack() throws InterruptedException {
		try {
		Thread.sleep(15L * 1000L); // Be unresponsive for intolerably long (15 seconds)
		} catch (InterruptedException e) {
			throw e;
		}
	}
}
