package com.liangyuelong.blog.test.elapsedtime;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentRun {

    public static void run(int size, Executor executor) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(size);
        CountDownLatch concurrentLatch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            service.execute(() -> {
                try {
                    concurrentLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    executor.execute();
                } finally {
                    latch.countDown();
                }
            });
        }
        concurrentLatch.countDown();
        latch.await();
    }

}
