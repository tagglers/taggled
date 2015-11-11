/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.taggled.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.utils.SystemProperty;
import com.taggled.backend.bean.Campaign;
import com.taggled.backend.bean.CampaignUpdate;
import com.taggled.backend.bean.DonationActivity;
import com.taggled.backend.bean.Invitation;
import com.taggled.backend.bean.TaggleActivity;
import com.taggled.backend.bean.User;
import com.taggled.backend.bean.UserCampaign;
import com.taggled.backend.bean.UserProfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.xml.transform.Result;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.taggled.com",
                ownerName = "backend.taggled.com",
                packagePath = ""
        )
)
public class MyEndpoint {


    public static final Double DONATION_AMOUNT = new Double("0.99");

    @ApiMethod(name = "createProfile")
    public UserProfile createProfile(User user){

        //create new user
        int userId = createDBUser(user);

        //check invitation, if exist create user campaign and donation activity
        List<Invitation> invs = getDBInvitationByEmail(user.getEmail());
        for (Invitation i : invs) {

            //create user campaign record
            int userCampId = createDBUserCampaign(i.getCampaignId(), userId, 0);

            //create donation activity
            insertDBDonationActivity(userCampId, DONATION_AMOUNT);



        }

        //get profile
        return getProfile(userId);

    }

    @ApiMethod(name = "updateProfile")
    public UserProfile updateProfile(User user){

        //update existing user
        updateDBUser(user);

        //get profile
        return getProfile(user.getId());

    }

    @ApiMethod(name = "getProfile")
    public UserProfile getProfile(@Named("userid") int userId){

        UserProfile userProfile = new UserProfile();

        //get user info
        User user = getDBUser(userId);

        userProfile.setUser(user);

        //get campaign list
        userProfile.setCampaigns(getDBCampaigns(user));

        return userProfile;

    }

    @ApiMethod(name = "taggle")
    public UserProfile taggle(@Named("campaignid") int campaignId
            , @Named("taggleemail") String taggleEmail, @Named("taggledemail") String taggledEmail){

        UserProfile userProfile = new UserProfile();


        //I. UPDATE on taggling user info
        User tagglingUser = getDBUserByEmail(taggleEmail);

        //get taggled user by email
        User taggledUser = getDBUserByEmail(taggledEmail);

        //get taggling user info, his current level
        UserCampaign tagglingUserCampaign = getDBUserCampByEmail(taggleEmail, campaignId);

        //update taggling user cantaggle status
        int count = getNumberOfTaggledUsers(tagglingUser.getId(), campaignId);

        boolean canTaggle = true;

        if (count >= 3)
            canTaggle = false;

        updateDBUserCampaign(campaignId, tagglingUser.getId(), tagglingUserCampaign.getCurrentLevel(), canTaggle
                , tagglingUserCampaign.getTotalDonation()); //dont change total amount for now, will be updated later

        //create taggle activity record
        insertDBTaggleActivity(tagglingUserCampaign.getId(), taggledUser.getId(), tagglingUserCampaign.getCurrentLevel());



        //II. update on taggled user info

        int taggledUserLevel = tagglingUserCampaign.getCurrentLevel() + 1;

        //check if taggled user has been participated in the campaign
        UserCampaign taggledUserCampaign = getDBUserCampByEmail(taggledEmail, campaignId);
        //if no create new user campaign
        //if yes update

        int taggledUserCampId = 0;

        if (taggledUserCampaign.getId() > 0) {
            taggledUserCampId = taggledUserCampaign.getId();
            updateDBUserCampaign(campaignId, taggledUser.getId(), taggledUserLevel, true
                    , taggledUserCampaign.getTotalDonation().doubleValue()+ DONATION_AMOUNT);
        }else{
            taggledUserCampId = createDBUserCampaign(campaignId, taggledUser.getId(), taggledUserLevel);
        }

        //create donation activity record
        insertDBDonationActivity(taggledUserCampId, DONATION_AMOUNT);

        //update totalDonation for all users
        updateDBTotalAmt(taggledUser.getId(), tagglingUserCampaign.getCurrentLevel());

        //return taggled user profile
        userProfile = getProfile(taggledUser.getId());

        return userProfile;

    }


    @ApiMethod(name = "getCampaignDetail")
    public Campaign getCampaignDetail(@Named("campaignid") int campaignId){


        return getDBCampaign(campaignId);

    }

    private Campaign getDBCampaign(int id){

        Connection conn = null;

        Campaign campaign = new Campaign();
        try {

            conn = getDBConnection();

            String select = "SELECT * from campaign WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(select);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                campaign.setId(rs.getInt(1));
                campaign.setName(rs.getString(2));
                campaign.setDescription(rs.getString(3));
                campaign.setImgUrl(rs.getString(4));
                campaign.setStartDate(rs.getDate(5));
                campaign.setEndDate(rs.getDate(6));
                campaign.setTargetAmount(rs.getDouble(7));
                campaign.setCreatedDate(rs.getDate(8));
                campaign.setCurrentAmount(rs.getDouble(9));
            }

            select = "SELECT * from campaignupdate WHERE campaignid = ?";

            stmt = conn.prepareStatement(select);

            stmt.setInt(1, campaign.getId());

            rs = stmt.executeQuery();

            List<CampaignUpdate> updates = new ArrayList<CampaignUpdate>();

            while(rs.next()){

                CampaignUpdate update = new CampaignUpdate();

                update.setId(rs.getInt(1));
                update.setCampaignId(rs.getInt(2));
                update.setImgUrl(rs.getString(3));
                update.setContent(rs.getString(4));
                update.setCreatedDate(rs.getDate(5));
                updates.add(update);

            }

            campaign.setCampaignUpdates(updates);


        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return campaign;
    }

    private void updateDBTotalAmt(int taggledUserId, int level){

        Connection conn = null;

        int l  = level;

        int t = taggledUserId;

        int userCampId = 0;

        try {

            conn = getDBConnection();
            String select = "SELECT uc.id, uc.userid from usercampaign AS uc, taggleactivity AS ta " +
                    "WHERE uc.id = ta.usercampid " +
                    "AND ta.taggleduserid = ?" +
                    "AND ta.level = ?";

            String update = "UPDATE usercampaign set totaldonation = totaldonation + 0.99" +
                    " WHERE id = ?";

            while (l >= 1) {

                l--;

                PreparedStatement stmt = conn.prepareStatement(select);

                stmt.setInt(1, t);
                stmt.setInt(2, l);

                ResultSet rs = stmt.executeQuery();

                while(rs.next()){

                    userCampId = rs.getInt(1);
                    t = rs.getInt(2);
                    break;
                }
                stmt = conn.prepareStatement(update);
                stmt.setInt(1, userCampId);
                int c = stmt.executeUpdate();
                if (c <= 0)
                    break;

            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }



    }

    private void insertDBTaggleActivity(int userCampId, int taggledUserId, int level){

        Connection conn = null;

        try {

            conn = getDBConnection();

            String statement = "INSERT INTO taggleactivity(usercampid, taggleduserid, level) " +
                    "VALUES(?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt.setInt(1, userCampId);
            stmt.setInt(2, taggledUserId);
            stmt.setInt(3, level);

            int success = stmt.executeUpdate();


        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

    }

    private int getNumberOfTaggledUsers(int userId, int campaignId){

        Connection conn = null;

        int count = 0;

        try {

            conn = getDBConnection();

            String statement = "SELECT count(*) AS cnt FROM taggled.usercampaign AS uc" +
                    ", taggled.taggleactivity AS ta " +
                    "WHERE uc.id = usercampid AND currentlevel = level" +
                    " AND userid = ? " +
                    "AND campaignid = ? ";

            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt.setInt(1, userId);
            stmt.setInt(2, campaignId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                count = rs.getInt(1);
            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }


        return count;

    }


    private void insertDBDonationActivity(int userCampId, Double donationAmount){

        Connection conn = null;

        try {

            conn = getDBConnection();

            String statement = "INSERT INTO donationactivity(usercampid, donationAmount) " +
                    "VALUES(?,?)";

            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt.setInt(1, userCampId);
            stmt.setDouble(2, donationAmount);

            int success = stmt.executeUpdate();


        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

    }

    private void updateDBUser(User user){

        Connection conn = null;

        int userId = 0;

        try {

            conn = getDBConnection();

            String statement = "UPDATE user SET email = ?" +
                    ", name_first = ?" +
                    ", name_last = ?" +
                    ", school = ?" +
                    ", imgurl = ?" +
                    " WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getNameFirst());
            stmt.setString(3, user.getNameLast());
            stmt.setString(4, user.getSchool());
            stmt.setString(5, user.getProfileImgUrl());
            stmt.setInt(6, user.getId());

            int success = stmt.executeUpdate();

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private int createDBUserCampaign(int campaignId, int userId, int toplevel){

        Connection conn = null;
        int userCampId = 0;
        try {

            conn = getDBConnection();

            String statement = "INSERT INTO usercampaign(userid, campaignid, toplevel" +
                    ", currentlevel, cantaggle, totaldonation) " +
                    "VALUES(?,?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, userId);
            stmt.setInt(2, campaignId);
            stmt.setInt(3, toplevel);
            stmt.setInt(4, toplevel);//same as top when created new
            stmt.setString(5, "Y");
            stmt.setDouble(6, DONATION_AMOUNT);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                userCampId=rs.getInt(1);
            }
        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return userCampId;

    }

    private int updateDBUserCampaign(int campaignId, int userId, int currentlevel, boolean canTaggle, Double donationAmount){

        Connection conn = null;
        int success = 0;
        try {

            conn = getDBConnection();

            String statement = "UPDATE usercampaign" +
                    " SET currentlevel = ?" +
                    ", cantaggle = ? " +
                    ", totaldonation = ?" +
                    " WHERE userid = ?" +
                    " AND campaignid = ?";

            PreparedStatement stmt = conn.prepareStatement(statement);


            stmt.setInt(1, currentlevel);
            stmt.setString(2, canTaggle ? "Y" : "N");
            stmt.setDouble(3, donationAmount);
            stmt.setInt(4, userId);
            stmt.setInt(5, campaignId);

            success = stmt.executeUpdate();


        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return success;

    }


    private List<Invitation> getDBInvitationByEmail(String email){

        Connection conn = null;
        List<Invitation> invitations  = new ArrayList<Invitation>();

        try {

            conn = getDBConnection();

            String statement = "SELECT * FROM invitation WHERE UPPER(useremail) = UPPER(?)";

            PreparedStatement stmt = conn.prepareStatement(statement);


            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Invitation i = new Invitation();
                i.setId(rs.getInt(1));
                i.setCampaignId(rs.getInt(2));
                i.setUserEmail(rs.getString(3));
                i.setInvitationCode(rs.getString(4));
                i.setInvitedByEmail(rs.getString(5));
                i.setCreatedDate(rs.getDate(6));

                invitations.add(i);
            }


        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return invitations;

    }


    private int createDBUser(User user){

        Connection conn = null;

        int userId = 0;

        try {

            conn = getDBConnection();

            String statement = "INSERT INTO user(email, name_first, name_last, school, imgurl) " +
                    "VALUES(?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getNameFirst());
            stmt.setString(3, user.getNameLast());
            stmt.setString(4, user.getSchool());
            stmt.setString(5, user.getProfileImgUrl());

            int success = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                userId=rs.getInt(1);
            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return userId;
    }

    private List<TaggleActivity> getTaggleActivities(int userCampaignId){

        List<TaggleActivity> taggles = new ArrayList<TaggleActivity>();
        Connection conn = null;


        try {

            conn = getDBConnection();

            String statement = "SELECT * FROM taggled.taggleactivity WHERE usercampid = ?";

            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt.setInt(1, userCampaignId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                TaggleActivity taggle = new TaggleActivity();
                taggle.setId(rs.getInt(1));
                taggle.setUserCampaignId(rs.getInt(2));
                taggle.setTaggledUserId(rs.getInt(3));
                taggle.setLevel(rs.getInt(4));
                taggle.setCreatedDate(rs.getDate(5));
                taggles.add(taggle);
            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }


        return taggles;

    }


    private List<DonationActivity> getDonationActivities(int userCampaignId){

        List<DonationActivity> donations = new ArrayList<DonationActivity>();

        Connection conn = null;

        try {

            conn = getDBConnection();

            String statement = "SELECT * FROM taggled.donationactivity WHERE usercampid = ?";

            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt.setInt(1, userCampaignId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                DonationActivity donation = new DonationActivity();
                donation.setId(rs.getInt(1));
                donation.setUserCampaignId(rs.getInt(2));
                donation.setDonationAmount(rs.getDouble(3));
                donation.setCreatedDate(rs.getDate(4));

                donations.add(donation);
            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return donations;

    }

    private List<UserCampaign> getDBCampaigns(User user){

        List<UserCampaign> userCampaigns = new ArrayList<UserCampaign>();

        Connection conn = null;

        try {

            conn = getDBConnection();

            String statement = "SELECT c.id" +
                    ", c.name" +
                    ", c.description" +
                    ", c.imgurl" +
                    ", c.startdate" +
                    ", c.enddate" +
                    ", c.targetAmount" +
                    ", c.currentamount" +
                    ", uc.toplevel" +
                    ", uc.currentlevel" +
                    ", uc.totaldonation" +
                    ", uc.cantaggle" +
                    ", uc.id usercampid" +
                    " from taggled.usercampaign AS uc, taggled.campaign AS c " +
                    "WHERE uc.campaignid = c.id AND userid = ?";

            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt.setInt(1, user.getId());

            ResultSet rs = stmt.executeQuery();

            List<Campaign> campaigns = new ArrayList<Campaign>();

            while (rs.next()) {

                Campaign campaign = new Campaign();
                campaign.setId(rs.getInt(1));
                campaign.setName(rs.getString(2));
                campaign.setDescription(rs.getString(3));
                campaign.setImgUrl(rs.getString(4));
                campaign.setStartDate(rs.getDate(5));
                campaign.setEndDate(rs.getDate(6));
                campaign.setTargetAmount(rs.getDouble(7));
                campaign.setCurrentAmount(rs.getDouble(8));

                UserCampaign userCampaign = new UserCampaign();

                userCampaign.setUser(user);

                userCampaign.setCampaign(campaign);

                userCampaign.setTopLevel(rs.getInt(9));

                userCampaign.setCurrentLevel(rs.getInt(10));

                userCampaign.setTotalDonation(rs.getDouble(11));

                String canTaggle = rs.getString(12);

                userCampaign.setCanTaggle("Y".equals(canTaggle));

                userCampaign.setId(rs.getInt(13));

                userCampaign.setDonationActivities(getDonationActivities(userCampaign.getId()));

                userCampaign.setTaggleActivities(getTaggleActivities(userCampaign.getId()));

                userCampaigns.add(userCampaign);

            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return userCampaigns;
    }


    private UserCampaign getDBUserCampByEmail(String email, int campaignId){

        UserCampaign campaign = new UserCampaign();

        Connection conn = null;

        try {

            conn = getDBConnection();

            String statement = "SELECT uc.* from taggled.usercampaign AS uc, taggled.user AS u" +
                    " WHERE email = ?" +
                    "AND uc.userid = u.id";
            PreparedStatement stmt = conn.prepareStatement(statement);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                campaign.setId(rs.getInt(1));
                campaign.setTopLevel(rs.getInt(4));
                campaign.setCurrentLevel(rs.getInt(5));
                campaign.setTotalDonation(rs.getDouble(6));
                campaign.setCanTaggle("Y".equals(rs.getString(7)));

            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return campaign;
    }


    private User getDBUserByEmail(String email){

        User user = new User();

        Connection conn = null;

        try {

            conn = getDBConnection();

            String statement = "SELECT * from taggled.user" +
                    " WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(statement);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));
                user.setNameFirst(rs.getString(3));
                user.setNameLast(rs.getString(4));
                user.setSchool(rs.getString(5));
                user.setProfileImgUrl(rs.getString(6));
                user.setCreatedDate(rs.getDate(7));
            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return user;
    }

    private User getDBUser(int userId){

        User user = new User();

        Connection conn = null;

        try {

            conn = getDBConnection();

            String statement = "SELECT * from taggled.user WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(statement);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();



            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));
                user.setNameFirst(rs.getString(3));
                user.setNameLast(rs.getString(4));
                user.setSchool(rs.getString(5));
                user.setProfileImgUrl(rs.getString(6));
                user.setCreatedDate(rs.getDate(7));

            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            if (conn != null)
                try {
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return user;
    }

    private Connection getDBConnection() throws Exception{

        String url = null;

        if (SystemProperty.environment.value() ==
                SystemProperty.Environment.Value.Production) {
            // Connecting from App Engine.
            // Load the class that provides the "jdbc:google:mysql://"
            // prefix.
            Class.forName("com.mysql.jdbc.GoogleDriver");
            url =
                    "jdbc:google:mysql://taggled-1125:taggled?user=root";
        } else {
            // Connecting from an external network.
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://173.194.231.227:3306/taggled?user=clientuser&password=googlesummit";

        }

        return DriverManager.getConnection(url);
    }
}
