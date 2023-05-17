package com.mooip.util;

public final class NumberUtil {  
    private NumberUtil() {
    }

    public static Integer parseInteger(String num) {
        return (num == null || num.trim().equals("")) ? null : Integer.valueOf(num);
    }

    public static Float parseFloat(String num) {
        return (num == null || num.trim().equals("")) ? null : Float.parseFloat(num);
    }

    public static Boolean parseBoolean(String num) {
        return (num == null || num.equals("")) ? null : Boolean.parseBoolean(num);
    }
    
    public static Float parseCurrency(String num) {
        if (num == null) {
            return null;
        }
        
        num = num.trim();
        if (num.equals("")) {
            return null;
        }
        
        if (num.startsWith("$")) {
            return Float.parseFloat(num.substring(1));
        }
        
        return Float.parseFloat(num);
    }
    
    /**
     * Changes an int array into an Integer array.
     * 
     * @param arr The integer array.
     * @return newIntegerArray The Integer array.
     */
    public static Integer[] toIntegerArray(int[] arr) {
        Integer[] newIntegerArray = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newIntegerArray[i] = arr[i];
        }
        
        return newIntegerArray;
    }  
}