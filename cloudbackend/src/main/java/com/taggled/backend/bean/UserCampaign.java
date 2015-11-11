package com.taggled.backend.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Cuong on 11/9/2015.
 */
public class UserCampaign {

    private Integer id;
    private User user;
    private Campaign campaign;
    private Integer topLevel;
    private Integer currentLevel;
    private Double totalDonation;
    private boolean canTaggle;
    private Date createdDate;
    private List<DonationActivity> donationActivities;
    private List<TaggleActivity> taggleActivities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Double getTotalDonation() {
        return totalDonation;
    }

    public void setTotalDonation(Double totalDonation) {
        this.totalDonation = totalDonation;
    }

    public boolean isCanTaggle() {
        return canTaggle;
    }

    public void setCanTaggle(boolean canTaggle) {
        this.canTaggle = canTaggle;
    }

    public List<DonationActivity> getDonationActivities() {
        return donationActivities;
    }

    public void setDonationActivities(List<DonationActivity> donationActivities) {
        this.donationActivities = donationActivities;
    }

    public List<TaggleActivity> getTaggleActivities() {
        return taggleActivities;
    }

    public void setTaggleActivities(List<TaggleActivity> taggleActivities) {
        this.taggleActivities = taggleActivities;
    }
}
