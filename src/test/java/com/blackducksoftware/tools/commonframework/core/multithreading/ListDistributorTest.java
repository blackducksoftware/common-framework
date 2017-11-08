/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
