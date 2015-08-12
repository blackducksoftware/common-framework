package soleng.framework.standard.trackable;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgressInfo.
 */
public class ProgressInfo {
	
	/** The total subtasks to complete. */
	private int totalSubtasksToComplete=0;
	
	/** The subtasks completed so far. */
	private int subtasksCompletedSoFar=0;
	
	/** The percent complete. */
	private int percentComplete=0;
	
	/**
	 * Instantiates a new progress info.
	 *
	 * @param percentComplete the percent complete
	 * @param subtasksCompletedSoFar the subtasks completed so far
	 * @param totalSubtasksToComplete the total subtasks to complete
	 */
	public ProgressInfo(int percentComplete, int subtasksCompletedSoFar, int totalSubtasksToComplete) {
		this.percentComplete = percentComplete;
		this.subtasksCompletedSoFar = subtasksCompletedSoFar;
		this.totalSubtasksToComplete = totalSubtasksToComplete;
	}
	
	/**
	 * Gets the total subtasks to complete.
	 *
	 * @return the total subtasks to complete
	 */
	public int getTotalSubtasksToComplete() {
		return totalSubtasksToComplete;
	}
	
	/**
	 * Sets the total subtasks to complete.
	 *
	 * @param totalSubtasksToComplete the new total subtasks to complete
	 */
	public void setTotalSubtasksToComplete(int totalSubtasksToComplete) {
		this.totalSubtasksToComplete = totalSubtasksToComplete;
	}
	
	/**
	 * Gets the subtasks completed so far.
	 *
	 * @return the subtasks completed so far
	 */
	public int getSubtasksCompletedSoFar() {
		return subtasksCompletedSoFar;
	}
	
	/**
	 * Sets the subtasks completed so far.
	 *
	 * @param subtasksCompletedSoFar the new subtasks completed so far
	 */
	public void setSubtasksCompletedSoFar(int subtasksCompletedSoFar) {
		this.subtasksCompletedSoFar = subtasksCompletedSoFar;
	}
	
	/**
	 * Gets the percent complete.
	 *
	 * @return the percent complete
	 */
	public int getPercentComplete() {
		return percentComplete;
	}
	
	/**
	 * Sets the percent complete.
	 *
	 * @param percentComplete the new percent complete
	 */
	public void setPercentComplete(int percentComplete) {
		this.percentComplete = percentComplete;
	}
}
