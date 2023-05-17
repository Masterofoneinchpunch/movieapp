package com.mooip.util;

import java.util.HashSet;
import java.util.Set;

public final class ContinuedFractionRepresentation {
    final private int number;
    private int periodCount;
    
    public ContinuedFractionRepresentation(int number) {
        this.number = number;
    }

    /**
     * Static Factory method of ContinuedFractionRepresentation.
     * 
     * @param number The number you are looking for.
     * @return ContinuedFractionRepresentation A ContinuedFractionRepresentation class initialized 
     * with the passed in number.
     */
    public static ContinuedFractionRepresentation getInstance(int number) {
        return new ContinuedFractionRepresentation(number);
    }
    
    public String getRepresentation() {
        Set<String> patterns = new HashSet<String>();
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        int sqrInt = (int) Math.sqrt(number);
        sb.append(sqrInt).append(";");
        
        if (sqrInt*sqrInt != number) {
            sb.append("(");
            int a = sqrInt;
            int p = 0;
            int q = 1;
            int j = 1;
            while (true) {
                p = (a * q) - p;
                //System.out.println("p " + p);
                q = (number - p*p) / q;
                //System.out.println("q " + q);
                a = (sqrInt + p) / q;
                //System.out.println("a" + j + " = " + a);
                j++;
                final String pgaPattern = p + " " + q + " " + a;
                if (patterns.contains(pgaPattern) == false) {
                    periodCount++;
                    patterns.add(pgaPattern);
                    sb.append(a).append(",");
                } else {
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(")");
                    break;
                }
            }                      
        }
        
        sb.append("]");
        return sb.toString();
    }

    public int getPeriodCount() {
        return this.periodCount;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public static void main(String[] args){
        ContinuedFractionRepresentation cfr = new ContinuedFractionRepresentation(2);
        System.out.println("representation for " + cfr.getNumber() + ": " + cfr.getRepresentation());
        System.out.println("pc: " + cfr.getPeriodCount());
        
        cfr = ContinuedFractionRepresentation.getInstance(3);
        System.out.println("representation for " + cfr.getNumber() + ": " + cfr.getRepresentation());
        System.out.println("pc: " + cfr.getPeriodCount());        
        cfr = ContinuedFractionRepresentation.getInstance(13);
        System.out.println("representation for " + cfr.getNumber() + ": " + cfr.getRepresentation());
        System.out.println("pc: " + cfr.getPeriodCount());        
        cfr = ContinuedFractionRepresentation.getInstance(23);
        System.out.println("representation for " + cfr.getNumber() + ": " + cfr.getRepresentation());
        System.out.println("pc: " + cfr.getPeriodCount());        
    }   
}
