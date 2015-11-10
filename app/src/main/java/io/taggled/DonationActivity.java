package io.taggled;

import java.util.Date;

import io.taggled.models.UserCampaign;

/**
 * Created by Cuong on 11/9/2015.
 */
public class DonationActivity {

    private UserCampaign userCampaign;
    private Integer donationAmount;
    private Date createdDate;

    public UserCampaign getUserCampaign() {
        return userCampaign;
    }

    public void setUserCampaign(UserCampaign userCampaign) {
        this.userCampaign = userCampaign;
    }

    public Integer getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Integer donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
