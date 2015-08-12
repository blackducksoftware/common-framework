package soleng.framework.standard.trackable;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soleng.framework.standard.trackable.progressrecorder.ProgressRecorder;
import soleng.framework.standard.trackable.progressrecorder.QueuingProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * Use an instance of this class to start a long-running, trackable tasks (worker).
 * Pass a fully-initialized instance of the worker. Call startTrackable to tell
 * it to start the task. Periodically call getProgress to get progress (percent complete).
 * @author sbillings
 *
 */
public class TrackableRunner {
	
	/** The log. */
	private Logger log = LoggerFactory.getLogger(TrackableRunner.class);
	
	/** The threadable trackable. */
	private ThreadableTrackable threadableTrackable;
	
	/** The queue. */
	private Queue<ProgressInfo> queue;
	
	/** The percent complete. */
	private int percentComplete=0;
	
	/** The worker future. */
	private Future<Integer> workerFuture;
	
	/** The max tolerable silence. */
	private int maxTolerableSilence=0; // How many times in a row will client tolerate getting no progress (before giving up)?
										// 0 means never give up (should get an exception if worker dies)
	/** The current silence duration. */
										private int currentSilenceDuration=0; // How many times in a row has there been no progress?
	
	/** The worker thread exception. */
	private RuntimeException workerThreadException=null;
	
	/**
	 * Instantiates a new trackable runner.
	 *
	 * @param trackable the trackable
	 */
	public TrackableRunner(Trackable trackable) {
		this.threadableTrackable = new ThreadableTrackable(trackable);
		
		queue = new ConcurrentLinkedQueue<ProgressInfo>();
		ProgressRecorder progressRecorder = new QueuingProgressRecorder(queue);
		trackable.setProgressRecorder(progressRecorder);
	}
	
	/**
	 * Tell the TrackableRunner to start the task.
	 */
	public void startTrackable() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
		workerFuture =  executor.submit(threadableTrackable);

	}
	
	/**
	 * Call this method if you want to limit how long WorkerRunner waits for an update from the worker.
	 * The default is to wait indefinitely, which is usually the right behavior.
	 * @param maxTolerableSilence How many times is it OK to get no response from the worker
	 * during a call to getProgress. 0 means there's no limit.
	 */
	public void setMaxSilence(int maxTolerableSilence) {
		this.maxTolerableSilence = maxTolerableSilence;
	}
	
	/**
	 * Checks for a progress update, and returns the current percent complete value.
	 * If there is no progress update, this method will just return the previous value.
	 * @return Percent of task completed so far
	 */
	public int getProgress() {
		Integer result=-99;
		if (workerFuture.isDone()) {
			try {
				result = workerFuture.get();
			} catch (InterruptedException ie) {
				workerThreadException = new RuntimeException(ie.getMessage());
				log.error("worker thread interrupted exception: " + ie.getMessage() + "; result: " + result);
			} catch (ExecutionException ee) {
				workerThreadException = new RuntimeException(ee.getMessage());
				log.error("worker thread execution exception: " + ee.getMessage() + "; result: " + result);
			}
		}

		// If worker has thrown an exception (most likely since the last call to getProgress()), throw it
		if (this.workerThreadException != null) {
			throw this.workerThreadException;
		}
		percentComplete = readProgressFromQueue(percentComplete);
		if ((this.maxTolerableSilence > 0) && (this.currentSilenceDuration > this.maxTolerableSilence)) {
			throw new RuntimeException("No response from worker in " + this.maxTolerableSilence + " tries.");
		}
		return percentComplete;
	}
	
	/**
	 * Check to see if the worker is still alive.
	 *
	 * @return true, if is alive
	 */
	public boolean isAlive() {
		return !workerFuture.isDone();
	}
	
	/**
	 * This innier class gets wrapped around the worker class so it can be run in a thread.
	 * @author sbillings
	 *
	 */
	private class ThreadableTrackable implements Callable<Integer> {
		
		/** The trackable. */
		Trackable trackable;
		
		/**
		 * Instantiates a new threadable trackable.
		 *
		 * @param trackable the trackable
		 */
		public ThreadableTrackable(Trackable trackable) {
			this.trackable = trackable;
		}
		
		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		public Integer call() throws Exception {
			try {
				trackable.runAndTrack();
			} catch (Exception e) {
				workerThreadException = new RuntimeException(e.getMessage());
				throw e;
			}
			return Integer.valueOf(0);
		}
		
	}

	/**
	 * Check the queue for a progress update from the worker.
	 * @param lastPercentComplete The previous percent complete value
	 * @return The (possibly updated) percent complete value
	 */
	private int readProgressFromQueue(int lastPercentComplete) {
		int mostRecentPercentComplete = lastPercentComplete;
		this.currentSilenceDuration++; // assume the worst
		while (!queue.isEmpty()) {
			this.currentSilenceDuration = 0; // reset if we get one or more progress updates
			ProgressInfo progressReported = queue.poll(); // discard all but last (=most recent)
			mostRecentPercentComplete = progressReported.getPercentComplete();
		}
		return mostRecentPercentComplete;
	}
}
