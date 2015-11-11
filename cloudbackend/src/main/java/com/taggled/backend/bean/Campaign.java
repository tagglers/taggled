package com.taggled.backend.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Cuong on 11/9/2015.
 */
public class Campaign {

    private Integer id;
    private String name;
    private String description;
    private String imgUrl;
    private Date startDate;
    private Date endDate;
    private Double targetAmount;
    private Double currentAmount;
    private Organization org;
    private Date createdDate;
    private List<CampaignUpdate> campaignUpdates;


    public List<CampaignUpdate> getCampaignUpdates() {
        return campaignUpdates;
    }

    public void setCampaignUpdates(List<CampaignUpdate> campaignUpdates) {
        this.campaignUpdates = campaignUpdates;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }



    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }


    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }
}
