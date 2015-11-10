package com.taggled.backend.bean;

import java.util.List;

/**
 * Created by Cuong on 11/9/2015.
 */
public class UserProfile {

    private User user;
    private List<Invitation> campaignInvitations;
    private List<UserCampaign> campaigns;
    private List<TaggleActivity> taggleActivities;
    private List<DonationActivity> donationActivities;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Invitation> getCampaignInvitations() {
        return campaignInvitations;
    }

    public void setCampaignInvitations(List<Invitation> campaignInvitations) {
        this.campaignInvitations = campaignInvitations;
    }

    public List<UserCampaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<UserCampaign> campaigns) {
        this.campaigns = campaigns;
    }

    public List<TaggleActivity> getTaggleActivities() {
        return taggleActivities;
    }

    public void setTaggleActivities(List<TaggleActivity> taggleActivities) {
        this.taggleActivities = taggleActivities;
    }

    public List<DonationActivity> getDonationActivities() {
        return donationActivities;
    }

    public void setDonationActivities(List<DonationActivity> donationActivities) {
        this.donationActivities = donationActivities;
    }
}
