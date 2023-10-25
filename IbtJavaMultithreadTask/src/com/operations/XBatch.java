package com.operations;

import java.util.ArrayList;
import java.util.List;

public class XBatch {

	private final int threadCount;
    private final int commitCount;

    public XBatch(int threadCount, int commitCount) {
        this.threadCount = threadCount;
        this.commitCount = commitCount;
    }

    public void execute() {
        
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            int start = i * commitCount;
            int end = (i + 1) * commitCount;
            Thread thread = new Thread((Runnable) new OperationClass(start, end, i));
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

    }
	
}
