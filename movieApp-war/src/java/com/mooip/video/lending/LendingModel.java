package com.mooip.video.lending;

import java.util.Date;

public final class LendingModel {
    
    private Date lendingDate;
    private String lentTo;
    private Date returnDate;
    private Boolean returnedYn;
    private String title;
    private Long videoId;
    
    public LendingModel() {        
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }
   
    public String getLentTo() {
        return lentTo;
    }

    public void setLentTo(String lentTo) {
        this.lentTo = lentTo;
    }    

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getReturnedYn() {
        return returnedYn;
    }

    public void setReturnedYn(Boolean returnedYn) {
        this.returnedYn = returnedYn;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
       
}

