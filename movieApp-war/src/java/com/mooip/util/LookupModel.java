package com.mooip.util;

public final class LookupModel {
    private String key;
    private String lookupValue;
    
    public LookupModel(String key) {
        this.key = key;
        this.lookupValue = key;
    }

    public LookupModel(String key, String lookupValue) {
        this.key = key;
        this.lookupValue = lookupValue;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }   
    
    @Override
    public String toString() {
        String newline = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        
        sb.append(newline);
        sb.append("key = ").append(this.key).append(", ");    
        sb.append("lookupValue = ").append(this.lookupValue).append(newline);    

        return sb.toString();
    }
}
