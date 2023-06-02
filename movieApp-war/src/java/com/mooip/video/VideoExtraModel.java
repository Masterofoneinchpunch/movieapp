package com.mooip.video;

import java.util.Date;

public final class VideoExtraModel {
    
    private Long videoExtraId;
    private Long videoId;
    private Date lastModified;
    private String title;
    private String description;
    
    public VideoExtraModel() {        
    }

    public Long getVideoExtraId() {
        return videoExtraId;
    }

    public void setVideoExtraId(Long videoExtraId) {
        this.videoExtraId = videoExtraId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String newline = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        
        sb.append(newline);
        sb.append("videoExtraId = ").append(this.videoExtraId).append(newline); 
        sb.append("videoId = ").append(this.videoId).append(newline);
        sb.append("lastModified = ").append(this.lastModified).append(newline);
        sb.append("title = ").append(this.title).append(newline);
        sb.append("description = ").append(this.description).append(newline);

        return sb.toString();
    }
    
}
