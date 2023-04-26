package com.mooip.movie;

public final class MovieYearCountModel {

    final private Integer year;
    final private Integer count;
    final private Integer watchedCount;
    
    public MovieYearCountModel(Integer year, Integer count, Integer watchedCount) {
        this.year = year;
        this.count = count;
        this.watchedCount = watchedCount;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getWatchedCount() {
        return watchedCount;
    }
   
}