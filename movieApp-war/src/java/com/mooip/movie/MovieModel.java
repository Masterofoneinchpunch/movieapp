package com.mooip.movie;

import com.mooip.movie.trailer.TrailerModel;
import java.util.Date;
import java.util.List;

public final class MovieModel {
    
    private Long movieId;
    private String title;
    private Integer year;
    private Date lastModified;
    private Date lastWatched;
    private String language;
    private String country;
    private String mpaaRating;
    private String myRating;
    private String review;
    private Integer runtime;
    private List<TrailerModel> trailers;
    private List<Date> watchHistory;

    public MovieModel () {        
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(Date lastWatched) {
        this.lastWatched = lastWatched;
    }
   
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
       
    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getMyRating() {
        return myRating;
    }

    public void setMyRating(String myRating) {
        this.myRating = myRating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<TrailerModel> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailerModel> trailers) {
        this.trailers = trailers;
    }

    public List<Date> getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(List<Date> watchHistory) {
        this.watchHistory = watchHistory;
    }
           
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }    
    
    @Override
    public String toString() {
        String newline = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        
        sb.append(newline);
        sb.append("movieId = ").append(this.movieId).append(newline); 
        sb.append("title = ").append(this.title).append(newline);
        sb.append("year = ").append(this.year).append(newline);
        sb.append("lastModified = ").append(this.lastModified).append(newline);
        sb.append("lastWatched = ").append(this.lastWatched).append(newline);
        sb.append("language = ").append(this.language).append(newline);
        sb.append("country = ").append(this.country).append(newline);
        sb.append("mpaaRating = ").append(this.mpaaRating).append(newline);
        sb.append("myRating = ").append(this.myRating).append(newline);
        sb.append("runtime = ").append(this.runtime).append(newline);

        return sb.toString();
    }
    
}