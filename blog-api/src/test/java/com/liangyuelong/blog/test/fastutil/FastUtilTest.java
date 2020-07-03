package com.liangyuelong.blog.test.fastutil;


import com.liangyuelong.blog.test.elapsedtime.ElapsedTime;
import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class FastUtilTest {

    @Test
    public void intArrayListTest() {
        int length = 1000000;
        ArrayList<Integer> integers = new ArrayList<>(length);
        ElapsedTime.elapsedTimePrint(() -> {
            for (int i = 0; i < length; i++) {
                integers.add(i);
            }
        });
        System.out.println(integers.size());
    }

    @Test
    public void intListTest() {
        int length = 1000000;
        IntList intList = new IntArrayList(length);
        ElapsedTime.elapsedTimePrint(() -> {
            for (int i = 0; i < length; i++) {
                intList.add(i);
            }
        });
        System.out.println(intList.size());
    }

    @Test
    public void doubleIntegerMapTest() {
        int length = 4000000;
        Map<Double, Integer> map = new HashMap<>(length);
        ElapsedTime.elapsedTimePrint(() -> {
            for (int i = 0; i < length; i++) {
                map.put((double) i, i);
            }
        });
        System.out.println(map.size());
    }

    @Test
    public void doubleIntMapTest() {
        int length = 4000000;
        Double2IntMap map = new Double2IntOpenHashMap(length);
        ElapsedTime.elapsedTimePrint(() -> {
            for (int i = 0; i < length; i++) {
                map.put(i, i);
            }
        });
        System.out.println(map.size());
    }

    @Test
    public void integerLinkedList() {
        int length = 5000000;
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        ElapsedTime.elapsedTimePrint(linkedHashSet::add, length);
        System.out.println(linkedHashSet.size());
    }

    @Test
    public void intLinkedList() {
        int length = 5000000;
        IntLinkedOpenHashSet intLinkedOpenHashSet = new IntLinkedOpenHashSet();
        ElapsedTime.elapsedTimePrint(intLinkedOpenHashSet::add, length);
        System.out.println(intLinkedOpenHashSet.size());
    }

}
