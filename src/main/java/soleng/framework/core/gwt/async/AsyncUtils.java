package soleng.framework.core.gwt.async;

import com.google.gwt.user.client.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class AsyncUtils.
 */
public class AsyncUtils {
	
	/** The timer. */
	private Timer timer;

	/**
	 * Schedule a task for some future time.
	 * Useful for polling to check progress after starting an asynchronous task.
	 * @param numSeconds The task will be run this many seconds from now
	 * @param taskToSchedule The task to run (when it's time)
	 */
	public void scheduleTask(int numSeconds, final ScheduledTask taskToSchedule) {
		  if (timer == null) {
			  timer = new Timer() {
				  public void run() {
					  taskToSchedule.runAfterTimer();
				  }
			  };
		  }
		  timer.schedule(numSeconds * 1000);
	  }
	
	/**
	 * Build a "progress bar-like" message.
	 *
	 * @param percentComplete The percentage of the task that has been completed
	 * @return A string representing a crude progress bar, like: "|xxxxx     |50%"
	 */
	public static String generateProgressBarString(int percentComplete) {
		if (percentComplete > 100) {
			percentComplete = 100;
		} else if (percentComplete < 0) {
			percentComplete = 0;
		}
    	int numDots = percentComplete/5;
    	StringBuilder progressMessage = new StringBuilder("|");
    	for (int i=0; i < 20; i++) {
    		if (i < numDots) {
        		progressMessage.append(">");
    		} else {
    			progressMessage.append("_");
    		}
    	}
    	progressMessage.append("|");
    	progressMessage.append(percentComplete);
    	progressMessage.append("%");

    	return progressMessage.toString();
	}

}
