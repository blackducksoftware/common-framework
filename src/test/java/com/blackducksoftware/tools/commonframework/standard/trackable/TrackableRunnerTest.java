/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.standard.trackable;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.standard.trackable.Trackable;
import com.blackducksoftware.tools.commonframework.standard.trackable.TrackableRunner;

// TODO: Auto-generated Javadoc
/**
 * The Class TrackableRunnerTest.
 */
public class TrackableRunnerTest {

    /** The Constant MAX_TOLERABLE_SILENCE. */
    private static final int MAX_TOLERABLE_SILENCE = 7;

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Test healthy worker.
     *
     * @throws Throwable
     *             the throwable
     */
    @Test
    public void testHealthyWorker() throws Throwable {
	int percentCompleted = 0;

	// Create and configure the worker object
	HealthyWorkerMock worker = new HealthyWorkerMock();
	worker.setConfiguration("Worker Configuration");
	assertEquals("Worker Configuration", worker.getConfiguration());

	// Create a TrackableRunner to run the worker object
	TrackableRunner workerRunner = new TrackableRunner(worker);

	// Start the work
	workerRunner.startTrackable();

	// Periodically ask the runner for progress info
	for (int i = 0; i < 100; i++) {
	    try {
		Thread.sleep(10L);
	    } catch (InterruptedException e) {
		fail("Sleep interrupted.");
	    }
	    percentCompleted = workerRunner.getProgress();
	    if (percentCompleted >= 100) {
		break;
	    }
	}
	assertEquals(100, percentCompleted);
    }

    /**
     * Test shy worker.
     *
     * @throws Throwable
     *             the throwable
     */
    @Test
    public void testShyWorker() throws Throwable {
	int percentCompleted = 0;

	// Create and configure the worker object
	Trackable worker = new ShyWorkerMock();

	// Create a TrackableRunner to run the worker object
	TrackableRunner workerRunner = new TrackableRunner(worker);
	workerRunner.setMaxSilence(15); // Allows a 50% buffer

	// Start the work
	workerRunner.startTrackable();

	// Periodically ask the runner for progress info
	for (int i = 0; i < 12; i++) {
	    try {
		Thread.sleep(10L);
	    } catch (InterruptedException e) {
		fail("Sleep interrupted.");
	    }
	    percentCompleted = workerRunner.getProgress();
	    if (percentCompleted >= 100) {
		break;
	    }
	}

	assertEquals(100, percentCompleted);
    }

    /**
     * Test unresponsive worker.
     *
     * @throws Throwable
     *             the throwable
     */
    @Test
    public void testUnresponsiveWorker() throws Throwable {
	int percentCompleted = 0;

	// Create and configure the worker object
	Trackable worker = new UnresponsiveWorkerMock();

	// Create a TrackableRunner to run the worker object
	TrackableRunner workerRunner = new TrackableRunner(worker);
	workerRunner.setMaxSilence(MAX_TOLERABLE_SILENCE);

	// Start the work
	workerRunner.startTrackable();

	// Periodically ask the runner for progress info
	int tryCount;
	Exception exceptionThrown = null;
	for (tryCount = 1; tryCount <= 100; tryCount++) {
	    try {
		Thread.sleep(10L);
	    } catch (InterruptedException e) {
		fail("Sleep interrupted.");
	    }
	    try {
		percentCompleted = workerRunner.getProgress();
	    } catch (RuntimeException e) {
		exceptionThrown = e;
		assertEquals("No response from worker in "
			+ MAX_TOLERABLE_SILENCE + " tries.", e.getMessage());
		break;
	    }
	    if (percentCompleted >= 100) {
		break;
	    }
	}
	assertEquals(0, percentCompleted); // This worker makes no progress
	assertNotNull(exceptionThrown);
	assertEquals(MAX_TOLERABLE_SILENCE + 1, tryCount); // if we didn't
							   // detect worker's
							   // death, we've
							   // failed
    }

    /**
     * Test suicidal worker.
     *
     * @throws Throwable
     *             the throwable
     */
    @Test
    public void testSuicidalWorker() throws Throwable {

	// Create and configure the worker object
	Trackable worker = new SuicidalWorkerMock();

	// Create a TrackableRunner to run the worker object
	TrackableRunner workerRunner = new TrackableRunner(worker);

	// Start the work
	workerRunner.startTrackable();

	Thread.sleep(500L);
	assertFalse(workerRunner.isAlive()); // This worker should be dead

	// This should throw the suicidal worker's null pointer exception
	try {
	    workerRunner.getProgress();
	    fail("WorkerTerminatedException not thrown");
	} catch (RuntimeException e) {
	    assertEquals(
		    "java.lang.NullPointerException: SuicidalWorkerMock committing suicide.",
		    e.getMessage());
	}
    }

}
