package com.main;

import com.operations.XBatch;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int threadCount = 5;
        int commitCount = 20;

        XBatch xBatch = new XBatch(threadCount, commitCount);
        xBatch.execute();

	}

}
