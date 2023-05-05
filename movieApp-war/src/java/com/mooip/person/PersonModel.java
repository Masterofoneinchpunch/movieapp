package com.mooip.person;

import java.util.Date;
import com.mooip.util.DateUtil;
import org.apache.log4j.Logger;

public final class PersonModel {
    private static final Logger log = Logger.getLogger(PersonModel.class);

    private Date birthDate;
    private String frontPicEmbed;
    private Date futureBirthday;
    private Date lastModified;
    private Long personId;
    private String person;

    public PersonModel() {        
    }
    
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFrontPicEmbed() {
        return frontPicEmbed;
    }

    public void setFrontPicEmbed(String frontPicEmbed) {
        this.frontPicEmbed = frontPicEmbed;
    }

    public Date getFutureBirthday() {
        return futureBirthday;
    }

    public void setFutureBirthday(Date futureBirthday) {
        this.futureBirthday = futureBirthday;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
    
    public Integer getAge() {
        if (this.birthDate == null) {
            return null;
        }
        
        return DateUtil.getAge(this.birthDate);
    }
   
    public boolean isBirthdayToday() {
        boolean isBirthdayToday = false;
        
        if (birthDate != null) {
            isBirthdayToday = DateUtil.sameDay(futureBirthday, new Date());
        }
        
        return isBirthdayToday;
    }
    
}