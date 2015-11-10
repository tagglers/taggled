package io.taggled;

import java.util.Date;

import io.taggled.models.User;
import io.taggled.models.UserCampaign;

/**
 * Created by Cuong on 11/9/2015.
 */
public class TaggleActivity {

    private UserCampaign userCampaign;
    private User taggledUser;
    private Integer level;
    private Date createdDate;

    public UserCampaign getUserCampaign() {
        return userCampaign;
    }

    public void setUserCampaign(UserCampaign userCampaign) {
        this.userCampaign = userCampaign;
    }

    public User getTaggledUser() {
        return taggledUser;
    }

    public void setTaggledUser(User taggledUser) {
        this.taggledUser = taggledUser;
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
}
