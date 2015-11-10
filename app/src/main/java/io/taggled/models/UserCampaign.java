package io.taggled.models;

import java.util.Date;

/**
 * Created by Cuong on 11/9/2015.
 */
public class UserCampaign {

    private User user;
    private Campaign campaign;
    private Integer topLevel;
    private Integer currentLevel;
    private Integer totalDonation;
    private boolean canTaggle;
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Integer getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Integer topLevel) {
        this.topLevel = topLevel;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Integer getTotalDonation() {
        return totalDonation;
    }

    public void setTotalDonation(Integer totalDonation) {
        this.totalDonation = totalDonation;
    }

    public boolean isCanTaggle() {
        return canTaggle;
    }

    public void setCanTaggle(boolean canTaggle) {
        this.canTaggle = canTaggle;
    }






}
