package soleng.framework.core.gwt.async;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class AsyncUtilsTest.
 */
public class AsyncUtilsTest {

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
	 * Test generate progress bar string50.
	 */
	@Test
	public void testGenerateProgressBarString50() {
		String progressBar = AsyncUtils.generateProgressBarString(50);
		assertEquals("|>>>>>>>>>>__________|50%", progressBar);
	}
	
	/**
	 * Test generate progress bar string200.
	 */
	@Test
	public void testGenerateProgressBarString200() {
		String progressBar = AsyncUtils.generateProgressBarString(200);
		assertEquals("|>>>>>>>>>>>>>>>>>>>>|100%", progressBar);
	}
	
	/**
	 * Test generate progress bar string minus50.
	 */
	@Test
	public void testGenerateProgressBarStringMinus50() {
		String progressBar = AsyncUtils.generateProgressBarString(-50);
		assertEquals("|____________________|0%", progressBar);
	}
	
	/**
	 * Test generate progress bar string1.
	 */
	@Test
	public void testGenerateProgressBarString1() {
		String progressBar = AsyncUtils.generateProgressBarString(-50);
		assertEquals("|____________________|0%", progressBar);
	}
}
