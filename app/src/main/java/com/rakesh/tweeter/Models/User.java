package com.rakesh.tweeter.Models;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rparuthi on 3/23/2017.
 */

public class User implements Serializable{

    private String name;
    private long uid;

    public String getScreenName() {
        return "@" + screenName;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private String screenName;
    private String profileImageUrl;


    //Create user object from json
    public static User fromJson(JSONObject jsonObject){
        User user = new User();

        try{
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  user;
    }


}
