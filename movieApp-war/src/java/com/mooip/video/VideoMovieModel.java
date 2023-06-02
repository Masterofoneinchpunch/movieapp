package com.mooip.video;

import com.mooip.movie.MovieModel;
import com.mooip.movie.trailer.TrailerModel;
import java.util.Date;
import java.util.List;

public final class VideoMovieModel {
    
    private Long videoMovieId;
    private final MovieModel movieModel;
    private List<String> commentaries;
    private List<VideoMiscModel> videoMisc;
    
    public VideoMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public Long getVideoMovieId() {
        return videoMovieId;
    }

    public void setVideoMovieId(Long videoMovieId) {
        this.videoMovieId = videoMovieId;
    }
   
    public MovieModel getMovieModel() {
        return movieModel;
    }

    public Long getMovieId() {
        return movieModel.getMovieId();
    }

    public String getTitle() {
        return movieModel.getTitle();
    }

    public Integer getYear() {
        return movieModel.getYear();
    }

    public Date getLastModified() {
        return movieModel.getLastModified();
    }

    public String getCountry() {
        return movieModel.getCountry();
    }

    public String getLanguage() {
        return movieModel.getLanguage();
    }

    public Date getLastWatched() {
        return movieModel.getLastWatched();
    }
    
    public String getMpaaRating() {
        return movieModel.getMpaaRating();
    }

    public String getMyRating() {
        return movieModel.getMyRating();
    }

    public Integer getRuntime() {
        return movieModel.getRuntime();
    }
    
    public String getReview() {
        return movieModel.getReview();
    }    

    public List<Date> getWatchHistory() {
        return movieModel.getWatchHistory();
    }
    
    public List<String> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<String> commentaries) {
        this.commentaries = commentaries;
    }
    
    public List<TrailerModel> getTrailers() {
        return movieModel.getTrailers();
    }

    public List<VideoMiscModel> getVideoMisc() {
        return videoMisc;
    }

    public void setVideoMisc(List<VideoMiscModel> videoMisc) {
        this.videoMisc = videoMisc;
    }
    
}
