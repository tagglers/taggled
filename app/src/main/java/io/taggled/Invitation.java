package io.taggled;

import java.util.Date;

/**
 * Created by Cuong on 11/9/2015.
 */
public class Invitation {

    private Integer id;
    private String userEmail;
    private String invitationCode;
    private String invitedByEmail;
    private Date createdDate;
    private Campaign campaign;

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getInvitedByEmail() {
        return invitedByEmail;
    }

    public void setInvitedByEmail(String invitedByEmail) {
        this.invitedByEmail = invitedByEmail;
    }

}
