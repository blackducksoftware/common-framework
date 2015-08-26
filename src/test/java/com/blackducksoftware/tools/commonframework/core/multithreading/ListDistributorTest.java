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

package com.blackducksoftware.tools.commonframework.core.multithreading;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blackducksoftware.tools.commonframework.core.multithreading.ListDistributor;

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
