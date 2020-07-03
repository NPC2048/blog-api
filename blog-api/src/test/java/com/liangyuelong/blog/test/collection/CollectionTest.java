package com.liangyuelong.blog.test.collection;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CollectionTest {

    @Test
    public void queueTest() {
        Queue<String> queue = new ConcurrentLinkedQueue<>();

        queue.offer("Java");
        queue.offer("Python");
        queue.offer("Go");
        queue.offer("JavaScript");

        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }

        // 遍历删除 key
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("1", "Java");
        map.put("2", "Python");
        map.put("3", "Mysql");
        map.put("4", "Docker");

        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            System.out.println(map);
            if (entry.getKey().equals("2")) {
                map.remove(entry.getKey());
            }
        }

    }

}
