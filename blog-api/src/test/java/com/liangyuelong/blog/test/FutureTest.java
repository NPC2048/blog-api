package com.liangyuelong.blog.test;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);

        Future<StringBuilder> future = service.submit(() -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("asdfasdf");
            return stringBuilder;
        });
        StringBuilder stringBuilder = future.get();
        System.out.println(stringBuilder);

    }

}
