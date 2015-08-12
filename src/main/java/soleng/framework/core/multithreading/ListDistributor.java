/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.core.multithreading;

import java.util.ArrayList;
import java.util.List;

public class ListDistributor {
	private int numThreads;
	private int listSize;
	
	private class IndexPair {
		int fromIndex;
		int toIndex;
		
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
	
	private List<IndexPair> indexPairList = new ArrayList<IndexPair>();
	
	public ListDistributor(int numThreads, int listSize) {
		this.numThreads = numThreads;
		this.listSize = listSize;
		
		int numThreadsToUse = numThreads;
		if (numThreads > listSize) {
			numThreadsToUse = listSize;
		}
		
		int numElementsPerThread = listSize / numThreadsToUse;
		int remainder = listSize % numThreadsToUse;
		
		for (int i=0; i < numThreadsToUse; i++) {
			int fromIndex = i * numElementsPerThread;
			int toIndex = fromIndex + numElementsPerThread;
			if (i == (numThreadsToUse-1)) {
				toIndex += remainder;
			}
			IndexPair indexPair = new IndexPair(fromIndex, toIndex);
			indexPairList.add(indexPair);
		}
		
	}
	
	public int getFromListIndex(int threadIndex) {
		return this.indexPairList.get(threadIndex).getFromIndex();
	}
	
	public int getToListIndex(int threadIndex) {
		return this.indexPairList.get(threadIndex).getToIndex();
	}
	
	public int getNumThreads() {
		return indexPairList.size();
	}

}
