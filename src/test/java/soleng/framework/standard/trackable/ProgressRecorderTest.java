package soleng.framework.standard.trackable;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import soleng.framework.standard.trackable.progressrecorder.LoggingProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgressRecorderTest.
 */
public class ProgressRecorderTest {

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		LoggingProgressRecorder recorder = new LoggingProgressRecorder();
		assertEquals(0, recorder.getPercentComplete());
		
		recorder.setTotalToComplete(20);
		recorder.setGranularity(5);
		
		recorder.incrementCompletedSoFar(1);
		assertEquals(5, recorder.getPercentComplete());
		
		recorder.setCompletedSoFar(10);
		assertEquals(50, recorder.getPercentComplete());
		
		recorder.incrementCompletedSoFar(5);
		assertEquals(75, recorder.getPercentComplete());
	}

}