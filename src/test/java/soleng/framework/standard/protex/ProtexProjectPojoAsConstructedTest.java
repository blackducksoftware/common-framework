package soleng.framework.standard.protex;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ProtexProjectPojoAsConstructedTest.
 */
public class ProtexProjectPojoAsConstructedTest {
	
	/** The pojo. */
	private static ProtexProjectPojo pojo;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pojo = new ProtexProjectPojo("testprojectkey", "testprojectname");
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
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test get project name.
	 */
	@Test
	public void testGetProjectName() {
		assertEquals("testprojectname", pojo.getProjectName());
	}

	/**
	 * Test get project key.
	 */
	@Test
	public void testGetProjectKey() {
		assertEquals("testprojectkey", pojo.getProjectKey());
	}

	/**
	 * Test get date.
	 */
	@Test
	public void testGetDate() {
		assertEquals(null, pojo.getDate());
	}

	/**
	 * Test get download path.
	 */
	@Test
	public void testGetDownloadPath() {
		assertEquals(null, pojo.getDownloadPath());
	}

	/**
	 * Test get status.
	 */
	@Test
	public void testGetStatus() {
		assertEquals(null, pojo.getStatus());
	}


	/**
	 * Test get uploaded mbe.
	 */
	@Test
	public void testGetUploadedMBE() {
		assertEquals(null, pojo.getUploadedMBE());
	}

	/**
	 * Test get uploaded app a.
	 */
	@Test
	public void testGetUploadedAppA() {
		assertEquals(null, pojo.getUploadedAppA());
	}

	/**
	 * Test get view path.
	 */
	@Test
	public void testGetViewPath() {
		assertEquals(null, pojo.getViewPath());
	}
}
