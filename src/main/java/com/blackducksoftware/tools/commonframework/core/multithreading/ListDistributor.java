/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/

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
