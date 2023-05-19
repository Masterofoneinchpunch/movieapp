package com.mooip.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LookupSupport {
    
    private final static Map lookups = populateLookups();
    
    private LookupSupport () {
    }
    
    private static synchronized Map populateLookups() {
        Map<String,List> lookupMaps = new HashMap();
        
        lookupMaps.put("VIDEOTYPE", addVideoTypes());
        lookupMaps.put("CASETYPE", addCaseTypes());
        lookupMaps.put("COMESWITH", addComesWith());
        lookupMaps.put("COUNTRY", addCountries());
        lookupMaps.put("LANGUAGE", addLanguages());
        lookupMaps.put("REGION", addRegions());
        lookupMaps.put("OBTAINEDTYPE", addObtainedTypes());
        lookupMaps.put("MPAARATING", addMpaaRatings());
        lookupMaps.put("MYRATING", addMyRatings());
        
        return lookupMaps;
    }
 
    private static List<LookupModel> addComesWith() {
        List<LookupModel> comesWith = new ArrayList();
        
        comesWith.add(new LookupModel("Book"));
        comesWith.add(new LookupModel("Chapter Insert"));
        comesWith.add(new LookupModel("Insert"));
        comesWith.add(new LookupModel("Insert Booklet"));
        
        return comesWith;
    }
    
    private static List<LookupModel> addVideoTypes() {
        List<LookupModel> videoTypes = new ArrayList();
        
        videoTypes.add(new LookupModel("BD (blu-ray)"));
        videoTypes.add(new LookupModel("BD/DVD Combo"));
        videoTypes.add(new LookupModel("3D/BD/DVD Combo"));
        videoTypes.add(new LookupModel("4K/BD Combo"));
        videoTypes.add(new LookupModel("DVD"));
        videoTypes.add(new LookupModel("DVD-MOD"));
        videoTypes.add(new LookupModel("HD DVD"));
        videoTypes.add(new LookupModel("Laserdisc"));
        videoTypes.add(new LookupModel("VHS"));

        return videoTypes;
    }

    private static List<LookupModel> addCaseTypes() {
        List<LookupModel> videoTypes = new ArrayList();
        
        videoTypes.add(new LookupModel("Box Set: Keep Case"));
        videoTypes.add(new LookupModel("Box Set: Other"));
        videoTypes.add(new LookupModel("Box Set: Slim Case"));
        videoTypes.add(new LookupModel("Box Set: Snap Case"));
        videoTypes.add(new LookupModel("Digipak"));
        videoTypes.add(new LookupModel("Keep Case"));
        videoTypes.add(new LookupModel("Slim Case"));
        videoTypes.add(new LookupModel("Snap Case"));
        videoTypes.add(new LookupModel("Steelbook"));
        videoTypes.add(new LookupModel("Other"));

        return videoTypes;
    }
    
    private static List<LookupModel> addRegions() {
        List<LookupModel> regions = new ArrayList();
        
        regions.add(new LookupModel("R1")); 
        regions.add(new LookupModel("R0/ALL")); 
        regions.add(new LookupModel("R2"));
        regions.add(new LookupModel("R3")); 
        regions.add(new LookupModel("ABC/R0")); 
        regions.add(new LookupModel("ABC/R1")); 
        regions.add(new LookupModel("A/R1"));
        regions.add(new LookupModel("A"));
        regions.add(new LookupModel("AB"));
        regions.add(new LookupModel("ABC"));
        
        return regions;
    }

    private static List<LookupModel> addObtainedTypes() {
        List<LookupModel> obtainedTypes = new ArrayList();
        
        obtainedTypes.add(new LookupModel("Gift"));
        obtainedTypes.add(new LookupModel("Online Purchase"));
        obtainedTypes.add(new LookupModel("Store Purchase"));
        obtainedTypes.add(new LookupModel("Other"));
        obtainedTypes.add(new LookupModel("Unknown"));
       
        return obtainedTypes;
    }
    
    private static List<LookupModel> addMpaaRatings() {
        List<LookupModel> mpaaRatings = new ArrayList();
        mpaaRatings.add(new LookupModel("Unrated"));
        mpaaRatings.add(new LookupModel("G"));
        mpaaRatings.add(new LookupModel("PG"));
        mpaaRatings.add(new LookupModel("PG-13"));
        mpaaRatings.add(new LookupModel("R"));
        mpaaRatings.add(new LookupModel("NC-17"));
        
        return mpaaRatings;
    }

    private static List<LookupModel> addMyRatings() {
        List<LookupModel> myRatings = new ArrayList();
        myRatings.add(new LookupModel("****"));
        myRatings.add(new LookupModel("***" + "\u00BD"));     
        myRatings.add(new LookupModel("***"));     
        myRatings.add(new LookupModel("**½"));     
        myRatings.add(new LookupModel("**"));     
        myRatings.add(new LookupModel("*½"));     
        myRatings.add(new LookupModel("*"));     
      
        return myRatings;
    }

    private static List<LookupModel> addCountries() {
        List<LookupModel> countries = new ArrayList();
        countries.add(new LookupModel("USA"));
        countries.add(new LookupModel("Argentina"));
        countries.add(new LookupModel("Austria"));
        countries.add(new LookupModel("Australia"));
        countries.add(new LookupModel("Brazil"));
        countries.add(new LookupModel("Canada"));
        countries.add(new LookupModel("China"));
        countries.add(new LookupModel("Czechoslovakia"));
        countries.add(new LookupModel("Denmark"));
        countries.add(new LookupModel("France"));
        countries.add(new LookupModel("Germany"));
        countries.add(new LookupModel("Hong Kong"));
        countries.add(new LookupModel("India"));
        countries.add(new LookupModel("Indonesia"));
        countries.add(new LookupModel("Iran"));
        countries.add(new LookupModel("Ireland"));
        countries.add(new LookupModel("Italy"));
        countries.add(new LookupModel("Jamaica"));
        countries.add(new LookupModel("Japan"));
        countries.add(new LookupModel("Mexico"));
        countries.add(new LookupModel("Mongolia"));
        countries.add(new LookupModel("Morocco"));
        countries.add(new LookupModel("Norway"));
        countries.add(new LookupModel("Poland"));
        countries.add(new LookupModel("Portugal"));
        countries.add(new LookupModel("Russia"));
        countries.add(new LookupModel("South Korea"));
        countries.add(new LookupModel("Soviet Union"));
        countries.add(new LookupModel("Spain"));
        countries.add(new LookupModel("Sweden"));
        countries.add(new LookupModel("Taiwan"));
        countries.add(new LookupModel("Thailand"));
        countries.add(new LookupModel("Turkey"));
        countries.add(new LookupModel("UK"));
        countries.add(new LookupModel("Vietnam"));
                
        return countries;
    }

    private static List<LookupModel> addLanguages() {
        List<LookupModel> languages = new ArrayList();
        languages.add(new LookupModel("Arabic"));
        languages.add(new LookupModel("Bengali"));
        languages.add(new LookupModel("Cantonese"));
        languages.add(new LookupModel("Czech"));
        languages.add(new LookupModel("Danish"));
        languages.add(new LookupModel("English"));
        languages.add(new LookupModel("French"));
        languages.add(new LookupModel("German"));
        languages.add(new LookupModel("Indonesian"));
        languages.add(new LookupModel("Italian"));
        languages.add(new LookupModel("Japanese"));
        languages.add(new LookupModel("Korean"));
        languages.add(new LookupModel("Mandarin"));
        languages.add(new LookupModel("Mongolian"));
        languages.add(new LookupModel("Norwegian"));
        languages.add(new LookupModel("Persian"));
        languages.add(new LookupModel("Polish"));
        languages.add(new LookupModel("Portugese"));
        languages.add(new LookupModel("Russian"));
        languages.add(new LookupModel("Spanish"));
        languages.add(new LookupModel("Swedish"));
        languages.add(new LookupModel("Thai"));
        languages.add(new LookupModel("Turkish"));
        languages.add(new LookupModel("Vietnamese"));
                       
        return languages;
    }
    
    public static List<LookupModel> getLookup(String key) {
        return (List) lookups.get(key);
    }
    
}
