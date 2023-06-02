package com.mooip.video;

import java.util.Date;

public final class VideoBoxSetModel {
    
    private Long boxSetId;
    private Date lastModified;
    private Long videoBoxSetId;
    private Long videoId;    
    private String videoTitle;
    
    public VideoBoxSetModel() {        
    }

    public Long getBoxSetId() {
        return boxSetId;
    }

    public void setBoxSetId(Long boxSetId) {
        this.boxSetId = boxSetId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getVideoBoxSetId() {
        return videoBoxSetId;
    }

    public void setVideoBoxSetId(Long videoBoxSetId) {
        this.videoBoxSetId = videoBoxSetId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }    

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }
    
}

