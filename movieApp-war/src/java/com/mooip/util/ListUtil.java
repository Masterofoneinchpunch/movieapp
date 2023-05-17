package com.mooip.util;

import java.util.ArrayList;
import java.util.List;

/**
 * An utility class for lists.
 * 
 * @author masterofoneinchpunch
 */
public final class ListUtil {
    private ListUtil() {
    }

    /**
     * Changes an Integer list into an int array.
     * 
     * @param list The Integer list.
     * @return arr The integer array.
     */
    public static int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        
        return arr;
    }
    
    /**
     * Changes an int array into an Integer list.
     * 
     * @param arr The integer array.
     * @return newList The Integer list.
     */
    public static List<Integer> toList(int[] arr) {
        List<Integer> newList = new ArrayList<Integer>();
        for (Integer num : arr) {
            newList.add(num);
        }
        
        return newList;
    }
    
    /**
     * Returns all the items from the first list that is not in the second list.
     * 
     * @param list1 The first list.
     * @param list2 The second list.
     * @param <T> the type of the element.
     * @return minus A list of all the first list items not in the second list.
     */
    public static <T> List<T> minus(List<T> list1, List<T> list2) {
        List<T> minus = new ArrayList<T>();
        for (T item : list1) {
            if (list2.contains(item) == false) {
                minus.add(item);
            }
        }
        return minus;
    }

    /**
     * Takes a list in order and concatenates a number.
     * 
     * @param list The list full of Integers.
     * @return number The result of the concatenation of Integers.
     */
    public static Long listToNumber(List<Integer> list) {
        StringBuilder sb = new StringBuilder(list.size());
        for (Integer num : list) {
            sb.append(num);
        }
        
        return Long.valueOf(sb.toString());
    }  
    
    /**
     * Turn a list into a string.
     * 
     * @param list Any list you want into string form.
     * @param <T> the type of the element.
     * @return listToString The list as a string.
     */
    public static <T> String listToString(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (T s : list) {
            sb.append(s.toString());
        }
        return sb.toString();
    }
}
