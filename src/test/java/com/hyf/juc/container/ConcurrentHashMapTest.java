package com.hyf.juc.container;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author baB_hyf
 * @date 2022/07/27
 */
public class ConcurrentHashMapTest {

    private Map<Integer, Integer> map = new ConcurrentSkipListMap<>();

    @Before
    public void before() {
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
    }

    @Test
    public void testA() {

        new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.remove(2);
            for (int i = 4; i < 10000; i++) {
                map.put(i, i);
            }
        }).start();

        map.forEach((k, v) -> {
            try {
                System.out.println("get: " + k + " -> " + v);
                Thread.sleep(1200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
