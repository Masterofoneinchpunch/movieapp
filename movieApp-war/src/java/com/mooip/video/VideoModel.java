package com.mooip.video;

import com.mooip.util.StringUtil;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public final class VideoModel {
    
    private static final String CRITERION_LABEL = "Criterion";
    
    private String caseType;
    private String comments;
    private Float cost;
    private String currentlyLentTo;
    private Integer discCount;
    private String label;
    private Date lastModified;
    private String location;
    private String rating;
    private String region;
    private String obtainedFrom;
    private String obtainedType;
    private String title;
    private Integer totalMinutes;
    private Long videoId;
    private String videoType;
    private Integer yearOfRelease;
    private Boolean oopYn;
    private Boolean partOfCollection = Boolean.FALSE;
    private Boolean partOfCriterion = Boolean.FALSE;
    private Boolean slipcoverYn;
    private String spineNumber;
    private List<VideoBoxSetModel> videoBoxSet;
    private List<String> videoCollections;
    private List<VideoMovieModel> videoMovies;
    private List<VideoExtraModel> videoExtras;
    
    public VideoModel () {        
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Boolean getOopYn() {
        return oopYn;
    }

    public void setOopYn(Boolean oopYn) {
        this.oopYn = oopYn;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getDiscCount() {
        return discCount;
    }

    public void setDiscCount(Integer discCount) {
        this.discCount = discCount;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getObtainedFrom() {
        return obtainedFrom;
    }

    public void setObtainedFrom(String obtainedFrom) {
        this.obtainedFrom = obtainedFrom;
    }

    public String getObtainedType() {
        return obtainedType;
    }

    public void setObtainedType(String obtainedType) {
        this.obtainedType = obtainedType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEscapeQuotes() {
        return StringUtil.escapeQuotes(this.title);
    }
    
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Boolean getSlipcoverYn() {
        return slipcoverYn;
    }

    public void setSlipcoverYn(Boolean slipcoverYn) {
        this.slipcoverYn = slipcoverYn;
    }

    public String getSpineNumber() {
        return spineNumber;
    }

    public void setSpineNumber(String spineNumber) {
        this.spineNumber = spineNumber;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }
       
    public List<VideoMovieModel> getVideoMovies() {
        return videoMovies;
    }

    public void setVideoMovies(List<VideoMovieModel> videoMovies) {
        this.videoMovies = videoMovies;
    }

    public List<VideoExtraModel> getVideoExtras() {
        return videoExtras;
    }

    public void setVideoExtras(List<VideoExtraModel> videoExtras) {
        this.videoExtras = videoExtras;
    }

    public String getCurrentlyLentTo() {
        return currentlyLentTo;
    }

    public void setCurrentlyLentTo(String currentlyLentTo) {
        this.currentlyLentTo = currentlyLentTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<VideoBoxSetModel> getVideoBoxSet() {
        return videoBoxSet;
    }

    public void setVideoBoxSet(List<VideoBoxSetModel> videoBoxSet) {
        this.videoBoxSet = videoBoxSet;
    }

    public List<String> getVideoCollections() {
        return videoCollections;
    }

    public void setVideoCollections(List<String> videoCollections) {
        this.videoCollections = videoCollections;
    }
    
    public Boolean getPartOfCollection() {       
        if (this.label != null && label.equals(CRITERION_LABEL)) {
            this.partOfCollection = Boolean.TRUE;
            this.partOfCriterion = Boolean.TRUE;
        }
        
        if (videoCollections.isEmpty() == false) {
            this.partOfCollection = Boolean.TRUE;
        }
        
        return this.partOfCollection;
    }
    
    public Boolean getPartOfCriterion() {
        return this.partOfCriterion;
    }
    
    public Boolean getHasCommentary() {
        Boolean hasCommentary = Boolean.FALSE;
        List<VideoMovieModel> videoMovies = getVideoMovies();
        if (videoMovies != null && videoMovies.isEmpty() == false) {
           for (VideoMovieModel vmm : videoMovies) {
               if (vmm.getCommentaries() != null && vmm.getCommentaries().isEmpty() == false) {
                   return Boolean.TRUE;
               }
           }         
        } 
            
        return hasCommentary;
    }

    public Boolean getHasVideoMisc() {
        Boolean hasVideoMisc = Boolean.FALSE;
        List<VideoMovieModel> videoMovies = getVideoMovies();
        if (videoMovies != null && videoMovies.isEmpty() == false) {
           for (VideoMovieModel vmm : videoMovies) {
               if (vmm.getVideoMisc() != null && vmm.getVideoMisc().isEmpty() == false) {
                   return Boolean.TRUE;
               }
           }         
        } 
            
        return hasVideoMisc;
    }
    
    public List getVideoTrailers() {
        List trailers = new ArrayList();
        List<VideoMovieModel> movies = getVideoMovies();
        if (movies != null && movies.isEmpty() == false) {
            for (VideoMovieModel mm : movies) {
                List movieTrailers = mm.getTrailers();
                if (movieTrailers != null) {
                    trailers.addAll(movieTrailers);
                }
            } 
        }
        return trailers;
    }    
    
    @Override
    public String toString() {
        String newline = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        
        sb.append(newline);
        sb.append("caseType = ").append(this.caseType).append(newline);    
        sb.append("comments = ").append(this.comments).append(newline);    
        sb.append("cost = ").append(this.cost).append(newline);    
        sb.append("discCount = ").append(this.discCount).append(newline);    
        sb.append("label = ").append(this.label).append(newline);    
        sb.append("lastModified = ").append(this.lastModified).append(newline);    
        sb.append("location = ").append(this.location).append(newline);    
        sb.append("obtainedFrom = ").append(this.obtainedFrom).append(newline);    
        sb.append("obtainedType = ").append(this.obtainedType).append(newline);    
        sb.append("oopYn = ").append(this.oopYn).append(newline);    
        sb.append("rating = ").append(this.rating).append(newline);    
        sb.append("region = ").append(this.region).append(newline);    
        sb.append("slipcoverYn = ").append(this.slipcoverYn).append(newline);    
        sb.append("spineNumber = ").append(this.spineNumber).append(newline);    
        sb.append("title = ").append(this.title).append(newline);   
        sb.append("totalMinutes = ").append(this.totalMinutes).append(newline); 
        sb.append("videoId = ").append(this.videoId).append(newline);    
        sb.append("videoType = ").append(this.videoType).append(newline);    
        sb.append("yearOfRelease = ").append(this.yearOfRelease).append(newline);    

        return sb.toString();
    }
    
    public String getJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("title:\"").append(this.title).append("\",");
        sb.append("caseType:\"").append(StringUtil.emptyStrIfNull(this.caseType)).append("\",");
        sb.append("cost:\"").append(StringUtil.emptyStrIfNull(this.cost)).append("\",");
        sb.append("discCount:\"").append(StringUtil.emptyStrIfNull(this.discCount)).append("\",");
        sb.append("label:\"").append(StringUtil.emptyStrIfNull(this.label)).append("\",");
        sb.append("location:\"").append(StringUtil.emptyStrIfNull(this.location)).append("\",");
        sb.append("obtainedFrom:\"").append(StringUtil.emptyStrIfNull(this.obtainedFrom)).append("\",");
        sb.append("obtainedType:\"").append(StringUtil.emptyStrIfNull(this.obtainedType)).append("\",");
        sb.append("region:\"").append(StringUtil.emptyStrIfNull(this.region)).append("\",");
        sb.append("spineNumber:\"").append(StringUtil.emptyStrIfNull(this.spineNumber)).append("\",");
        sb.append("videoType:\"").append(StringUtil.emptyStrIfNull(this.videoType)).append("\",");
        sb.append("yearOfRelease:\"").append(StringUtil.emptyStrIfNull(this.yearOfRelease)).append("\"");
        sb.append("}");
        return sb.toString();        
    }
    
}