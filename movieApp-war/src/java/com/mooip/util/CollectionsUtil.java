package com.mooip.util;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public final class CollectionsUtil {
    private CollectionsUtil() {
    }

    public static List<Integer> toList(int[] arr) {
        List<Integer> newList = new ArrayList<Integer>();
        for (Integer num : arr) {
            newList.add(num);
        }
        
        return newList;
    }

    public static <T> List<T> minus(List<T> list1, List<T> list2) {
        List<T> minus = new ArrayList<T>();
        for (T item : list1) {
            if (list2.contains(item) == false) {
                minus.add(item);
            }
        }
        return minus;
    }

    public static <T> T getFirstMinus(Set<T> set1, Set<T> set2) {
        for (T item : set1) {
            if (set2.contains(item) == false) {
                return item;
            }
        }
        return null;
    }
    
    public static <T> Set<T> minus(Set<T> set1, Set<T> set2) {
        Set<T> minus = new HashSet<T>();
        for (T item : set1) {
            if (set2.contains(item) == false) {
                minus.add(item);
            }
        }
        return minus;
    }

    public static Long listToNumber(List<Integer> list) {
        StringBuilder sb = new StringBuilder(list.size());
        for (Integer num : list) {
            sb.append(num);
        }
        
        return Long.valueOf(sb.toString());
    }   
}

