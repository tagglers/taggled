package com.taggled.backend.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Cuong on 11/9/2015.
 */
public class DonationActivity {


    private Integer id;
    private Integer userCampaignId;
    private BigDecimal donationAmount;
    private Date createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserCampaignId() {
        return userCampaignId;
    }

    public void setUserCampaignId(Integer userCampaignId) {
        this.userCampaignId = userCampaignId;
    }

    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
