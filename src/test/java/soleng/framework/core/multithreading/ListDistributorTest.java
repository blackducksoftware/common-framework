/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.core.multithreading;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import soleng.framework.core.multithreading.ListDistributor;

public class ListDistributorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1() {
		ListDistributor d = new ListDistributor(1, 1);
		assertEquals(1, d.getNumThreads());
		
		assertEquals(0, d.getFromListIndex(0));
		assertEquals(1, d.getToListIndex(0));
	}
	
	@Test
	public void test2() {
		ListDistributor d = new ListDistributor(2, 2);
		assertEquals(2, d.getNumThreads());
		
		assertEquals(0, d.getFromListIndex(0));
		assertEquals(1, d.getToListIndex(0));
		
		assertEquals(1, d.getFromListIndex(1));
		assertEquals(2, d.getToListIndex(1));
	}
	
	@Test
	public void test3() {
		ListDistributor d = new ListDistributor(2, 3);
		assertEquals(2, d.getNumThreads());
		
		assertEquals(0, d.getFromListIndex(0));
		assertEquals(1, d.getToListIndex(0));
		
		assertEquals(1, d.getFromListIndex(1));
		assertEquals(3, d.getToListIndex(1));
	}
	

	@Test
	public void test4() {
		ListDistributor d = new ListDistributor(3, 3);
		assertEquals(3, d.getNumThreads());
		
		assertEquals(0, d.getFromListIndex(0));
		assertEquals(1, d.getToListIndex(0));
		
		assertEquals(1, d.getFromListIndex(1));
		assertEquals(2, d.getToListIndex(1));
		
		assertEquals(2, d.getFromListIndex(2));
		assertEquals(3, d.getToListIndex(2));
	}
	
	@Test
	public void test5() {
		ListDistributor d = new ListDistributor(3, 10);
		assertEquals(3, d.getNumThreads());
		
		assertEquals(0, d.getFromListIndex(0));
		assertEquals(3, d.getToListIndex(0));
		
		assertEquals(3, d.getFromListIndex(1));
		assertEquals(6, d.getToListIndex(1));
		
		assertEquals(6, d.getFromListIndex(2));
		assertEquals(10, d.getToListIndex(2));
	}
	
	@Test
	public void test6() {
		ListDistributor d = new ListDistributor(3, 11);
		assertEquals(3, d.getNumThreads());
		
		assertEquals(0, d.getFromListIndex(0));
		assertEquals(3, d.getToListIndex(0));
		
		assertEquals(3, d.getFromListIndex(1));
		assertEquals(6, d.getToListIndex(1));
		
		assertEquals(6, d.getFromListIndex(2));
		assertEquals(11, d.getToListIndex(2));
	}
}
