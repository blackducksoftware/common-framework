package soleng.framework.standard.trackable;

import soleng.framework.standard.trackable.progressrecorder.ProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class SuicidalWorkerMock.
 */
public class SuicidalWorkerMock implements Trackable {

	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.Trackable#setProgressRecorder(soleng.framework.standard.trackable.progressrecorder.ProgressRecorder)
	 */
	public void setProgressRecorder(ProgressRecorder progressRecorder) {
	}

	/* (non-Javadoc)
	 * @see soleng.framework.standard.trackable.Trackable#runAndTrack()
	 */
	public void runAndTrack() {
		throw new NullPointerException("SuicidalWorkerMock committing suicide.");
	}
}
