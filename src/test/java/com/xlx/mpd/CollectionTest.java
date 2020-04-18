package com.xlx.mpd;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xielx at 2020/4/17 11:20
 */
public class CollectionTest {
    
    @Test
    public void testFastFail(){
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
    
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            list.add("F");
            System.out.println("size:" + list.size());
        }
    }
    
    @Test
    public void testSafeFail(){
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("A","123");
        concurrentHashMap.put("B","123");
        concurrentHashMap.put("C","123");
    
        Set<Map.Entry<String, String>> entries = concurrentHashMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
           System.out.println(iterator.next());
            concurrentHashMap.put("D","2-3");
        }
        System.out.println("list:" + concurrentHashMap.size());
        
    }
}
