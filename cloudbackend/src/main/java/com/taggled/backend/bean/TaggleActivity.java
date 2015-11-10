package com.taggled.backend.bean;

import java.util.Date;

/**
 * Created by Cuong on 11/9/2015.
 */
public class TaggleActivity {

    private Integer id;
    private Integer userCampaignId;
    private Integer taggledUserId;
    private Integer level;
    private Date createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUserCampaignId() {
        return userCampaignId;
    }

    public void setUserCampaignId(Integer userCampaignId) {
        this.userCampaignId = userCampaignId;
    }

    public Integer getTaggledUserId() {
        return taggledUserId;
    }

    public void setTaggledUserId(Integer taggledUserId) {
        this.taggledUserId = taggledUserId;
    }
}
