package com.liangyuelong.blog.test;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.ReflectUtils;

import java.util.*;

public class RandomTest {

    @Test
    public void name() {
        System.out.println("hello world");
        System.out.println(Arrays.toString(random(10)));
        System.out.println(randomShuffle(10));

        long t = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            randomShuffle(1000);
        }
        t = System.currentTimeMillis() - t;
        System.out.println("ms:" + t);
        t = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            random(1000);
        }
        t = System.currentTimeMillis() - t;
        System.out.println(t);

    }

    public int[] random(int size) {
        int[] seed = new int[size];
        for (int i = 0; i < size; i++) {
            seed[i] = i;
        }
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            int r = RandomUtils.nextInt(0, size - i - 1);
            arr[i] = seed[r];
            seed[r] = seed[size - i - 1];
        }
        return arr;
    }

    public List<Integer> randomShuffle(int size) {
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    @Test
    void clazz() {
        String b = new String("");
        System.out.println(!b.equals(""));
        System.out.println(Object[].class.getClassLoader());
        System.out.println(int[].class.getClassLoader());
        System.out.println(List.class.getClassLoader());
        String[] a = {"a"};
        System.out.println(a instanceof Object[]);
        System.out.println(int[].class);
        System.out.println(a.getClass());
        List<String> list = new ArrayList<>();
        System.out.println(list.getClass());
        System.out.println(ReflectUtils.newInstance(list.getClass()));
        List<String> newList = (List<String>) ReflectUtils.newInstance(list.getClass());
        newList.add("s");
        System.out.println(newList);
    }
}
