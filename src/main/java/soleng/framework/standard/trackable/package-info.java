/**
 * The classes/interface in the trackable package help you make a 
 * long-running operation "trackable". 
 * <p>
 * Use case: A web applicate may need to keep transactions short (fast). To
 * execute a task that may take a long time, they may need to be able to execute
 * a quick transaction to start the task, and then poll periodically for status.
 * When the web app polls for status, if the task is not done, it should receive
 * an indication of how far along it is (of the total to be done, what percentage
 * is complete.
 * <p>
 * How to make a task trackable:<br>
 * <ol>
 * <li>The task should be extracted to a class, called the worker class. See the
 * HealthyWorkerMock class (under src/test) for an example of a worker class.
 * The worker class must implement the trackable interface. It can have any
 * number of constructors and methods required to create and initialize it, and then
 * one method (runAndTrack()) to run the task. It also needs a setProgressRecorder()
 * method (more on this later).<br>
 * <li>The class that will execute (start and monitor) the task (say, a server side class in the 
 * web app) must create and initialize the worker object, and then pass it
 * to an instance of TrackableRunner (via the TrackableRunner constructor).
 * To run the task it calls the startTrackable() method on its instance of
 * TrackableRunner. To (periodically) check on the progress of the task,
 * call the getProgress() method on the TrackableRunner instance.<br>
 * <li>By the time it is started by the TrackableRunner, the worker object will
 * have a ProgressRecorder (passed into it via the setProgressRecorder method).
 * Each task is assumed to consist of a known number of subtasks. When all
 * subtasks are completed, the task is done.
 * As it makes progress on the task, the worker object should call that 
 * ProgressRecorder to provide periodic updates on progress, by telling
 * it how many subtasks have been completed, or incrementing the number
 * completed.
 * <li>The web application client will call the server to initiate the task, 
 * and start a timer to remind it to check on progress in a little while.
 * When the timer expires, call the server to check progress. If progress has
 * reached 100%, get the results and provide them to the user. If not,
 * optionally provide the user with an update on progress (using the percent 
 * complete value returned by the check progress operation).
 * </ol>
 * <p>
 * How the Trackable mechanism works:
 * TrackableRunner provides to the worker class a ProgressRecorder that
 * knows how to pass progress updates back to it, and then runs the worker
 * task's startAndTrack method in a separate thread (receiving progress
 * updates via the ProgressRecorder).
 * 
 */
package soleng.framework.standard.trackable;