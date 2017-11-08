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

import java.util.ArrayList;
import java.util.List;

public class ListDistributor {

    private class IndexPair {
	final int fromIndex;

	final int toIndex;

	public IndexPair(int fromIndex, int toIndex) {
	    this.fromIndex = fromIndex;
	    this.toIndex = toIndex;
	}

	public int getFromIndex() {
	    return fromIndex;
	}

	public int getToIndex() {
	    return toIndex;
	}
    }

    private final List<IndexPair> indexPairList = new ArrayList<IndexPair>();

    public ListDistributor(int numThreads, int listSize) {

	int numThreadsToUse = numThreads;
	if (numThreads > listSize) {
	    numThreadsToUse = listSize;
	}

	int numElementsPerThread = listSize / numThreadsToUse;
	int remainder = listSize % numThreadsToUse;

	for (int i = 0; i < numThreadsToUse; i++) {
	    int fromIndex = i * numElementsPerThread;
	    int toIndex = fromIndex + numElementsPerThread;
	    if (i == (numThreadsToUse - 1)) {
		toIndex += remainder;
	    }
	    IndexPair indexPair = new IndexPair(fromIndex, toIndex);
	    indexPairList.add(indexPair);
	}

    }

    public int getFromListIndex(int threadIndex) {
	return indexPairList.get(threadIndex).getFromIndex();
    }

    public int getToListIndex(int threadIndex) {
	return indexPairList.get(threadIndex).getToIndex();
    }

    public int getNumThreads() {
	return indexPairList.size();
    }

}
