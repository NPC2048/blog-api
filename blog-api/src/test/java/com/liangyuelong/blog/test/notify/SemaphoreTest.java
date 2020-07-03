package com.liangyuelong.blog.test.notify;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

public class SemaphoreTest {

    private static volatile String message;

    private static List<Thread> threads
            = Collections.synchronizedList(new LinkedList<>());


    public static void main(String[] args) throws InterruptedException {
        Semaphore exitSemaphore = new Semaphore(0);
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 3; i++) {
            final int index = i;
            service.submit(() -> {
                Thread t = Thread.currentThread();
                threads.add(t);
                // 等待资源
                System.out.println(index + "阻塞");
                LockSupport.park();
                System.out.println("伞兵" + index + "号准备就绪");
                System.out.println("消息: " + message);

            });
        }
        service.submit(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入要发送的消息:");
                message = scanner.next();
                System.out.println(message + ":" + StringUtils.isNotEmpty(message));
                if (StringUtils.isNotEmpty(message)) {
                    //增加一个许可
                    System.out.println("获取:" + message);
                    Thread thread = threads.get(Integer.parseInt(message));
                    System.out.println(thread);
                    LockSupport.unpark(thread);
                }
                if ("exit".equals(message)) {
                    exitSemaphore.release();
                }
            }
        });
        exitSemaphore.acquire();
        service.shutdownNow();
    }

}
