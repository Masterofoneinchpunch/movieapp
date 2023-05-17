package com.mooip.util;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    public static final String NEWLINE = System.getProperty("line.separator");
    
    // https://stackoverflow.com/questions/758717/pandigital-regex
    public static final String PAN_DIGITAL_NINE = "^(?!.*([1-9]).*\\1)[1-9]{9}$";
    public static final String PAN_DIGITAL = "^(?!.*([1-9]).*\\1)[1-9]{2,9}$";
    
    private static final String[] numberNames = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static final String[] tensNames = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    
    private StringUtil() {
    }

    public static String emptyStrIfNull(Number num) {
        return (num == null) ? "" : num.toString();
    }

    public static String emptyStrIfNull(String str) {
        return (str == null) ? "" : str;
    }

    public static boolean isEmpty(String str) {
        return (str == null || 0 == str.trim().length());
    }

    /**
     * Is the passed in string a palindrome?
     * 
     * @param str The passed in string.
     * @return boolean A true if a palindrome, a false if not.
     */
    public static boolean isPalindrome(String str) {
        final String reverseString = new StringBuilder(str).reverse().toString();        
        return str.equals(reverseString);
    }
    
    // 
    /**
     * Returns a number as word.  This works up to a thousand.  Ex. 1000 becomes One thousand.
     * 
     * @param number The passed in number.
     * @return numberAsWord The number as word.
     */
    public static String numberAsWord(Integer number) {
        StringBuilder numberAsWord = new StringBuilder("");
        final String passedNum = number.toString();
        if (passedNum.length() == 4) {
            int index = Character.digit(passedNum.charAt(0), 10);
            numberAsWord.append(numberNames[index]).append(" thousand");
        } else {
            numberAsWord.append(convertLessThanOneThousand(number));
        }
        
        return numberAsWord.toString();
    }
 
    private static String convertLessThanOneThousand(Integer number) {
        String soFar;

        if (number % 100 < 20){
            soFar = numberNames[number % 100];
            number /= 100;
        } else {
            String lastDigit = numberNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10];
            if (lastDigit.equals("") == false) {
                soFar += "-" + lastDigit;
            }
            number /= 10;
        }
        
        // this return is for numbers under 100
        if (number == 0) {
            return soFar;
        }
        
        if (soFar.equals("") == false) {
            soFar = " and " + soFar;
        }
        return numberNames[number] + " hundred" + soFar;    
    }

    /**
     * A utility method used to escape quotes with an HTML entity; for example modifying the label of a
     * tree node so that those characters don't break the rendering.  If the passed in value is null
     * return an empty string.
     *
     * @param value The String value passed in.
     * @return value If the parameter is null, empty quotes will be passed back.  Otherwise replace all quotations with
     * correct entity value.
     */
    public static String escapeQuotes(String value) {
        if (value == null) {
            return "";
        }
        
        return value.replaceAll("\"", "&quot;");
    }    
 
    public static String repeat(String str, int rep) {
        StringBuilder sb = new StringBuilder("");
        
        for (int i = 0; i < rep; i++) {
            sb.append(str);
        }
        
        return sb.toString();
    }

    /**
     * Gets the alphabetic score according to each letter in the string.
     * 
     * @param str The string passed in.
     * @return alphabeticScore The alphabetic score.
     */
    public static int alphabeticScore(String str) {
        str = str.toUpperCase();
        final int startValue = (int)'A' - 1;
        int alphabeticScore = 0;
        for (int i = 0; i < str.length(); i++) {
            alphabeticScore += (str.charAt(i) - startValue);
        }
        return alphabeticScore;
    }

    /**
     * Does the input string match the passed in regex?
     * 
     * @param regex The regex pattern passed in.
     * @param str The passed in string.
     * @return boolean A true if the string matches, a false if not.
     */
    public static boolean matchRegex(final String regex, final String str) {
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);

        return mat.find();
    }

    /**
     * Gets the matches for the passed in regex.
     * 
     * @param regex The regex pattern passed in.
     * @param str The passed in string.
     * @return matches A list of matches.
     */
    public static List<String> getMatches(final String regex, final String str) {
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);
        List<String> matches = new ArrayList<String>();
        while (mat.find()) {
            matches.add(mat.group());
        }
        
        return matches;
    }    
    
    /**
     * Finds the longest repeating pattern.
     * 
     * @param str The past in string to look through.
     * @return repeatingPattern The longest repeating pattern.
     */
    //TODO: I noticed that this does not work with patterns such as "1111611116"
    public static String longestRepeatingPattern(String str){		
        String repeatingPattern = null;
        final String regex = "(.+?)(?:\\1)+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        
        while (m.find()) {
            String repPattern = m.group(1);
                        
            if (repeatingPattern == null || repPattern.length() > repeatingPattern.length()) {
                repeatingPattern = repPattern;
            }
        }
        
        return repeatingPattern;
    }

    public static String firstLongestRepeatingPattern(String str){		
        String repeatingPattern = null;
        final String regex = "^(.+?)(?=\\1)+"; //^(\\d+)(?:\\1)+
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        
        while (m.find()) {
            String repPattern = m.group(1);
            System.out.println("repPattern: " + repPattern);            
            if (repeatingPattern == null || repPattern.length() > repeatingPattern.length()) {
                repeatingPattern = repPattern;
            }
        }
        
        return repeatingPattern;
    }
    
    public static boolean isPanDigital(final String str) {
        if (str.length() > 9) {
            return false;
        }
        return StringUtil.matchRegex(PAN_DIGITAL, str);
    }

    /**
     * Checks to see if the string is pandigital.
     * 
     * @param str The string to check.
     * @return boolean A true if the string is pandigital (has to have a length of 9), a false if not.s
     */
    public static boolean isPanDigitalNine(final String str) {
        if (str.length() != 9) {
            return false;
        }
        return StringUtil.matchRegex(PAN_DIGITAL_NINE, str);
    }
    
    /**
     * Sorts the string passed in.
     * 
     * @param str The string to be sorted.
     * @return sortStr A sorted string.
     */
    public static String sortString(String str) {
        char[] strA = str.toCharArray();
        Arrays.sort(strA);
        return String.valueOf(strA);
    }
    
    /** 
     * Checks to see if both strings are a permutation of each other.
     * @param str The first string.
     * @param str2 The second string.
     * @return boolean A true if they are a permutation, a false if not.
     */
    public static boolean isPermutation(String str, String str2) {
        final String sortedStr = sortString(str);
        final String sortedStr2 = sortString(str2);

        return sortedStr.equals(sortedStr2);
    }
    
    /**
     * Turn a Matrix into a string.
     * 
     * @param matrix The integer matrix.
     * @return sb The String of the matrix.
     * TODO: add padding for longest member in a column.
     */
    public static String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < matrix.length; i++) {
            final int WIDTH = matrix[i].length;
            for (int j = 0; j < WIDTH; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append(NEWLINE);
        }
        
        return sb.toString();
    }    
}