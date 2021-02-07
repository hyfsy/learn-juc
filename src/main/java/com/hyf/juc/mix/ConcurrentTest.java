package com.hyf.juc.mix;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class ConcurrentTest {

    @Test
    public void testMap() {
        Map<String, String> map = new ConcurrentHashMap<>();
        ConcurrentSkipListMap<Object, Object> skipListMap = new ConcurrentSkipListMap<>();


    }
}
