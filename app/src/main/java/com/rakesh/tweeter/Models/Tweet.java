package com.rakesh.tweeter.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rparuthi on 3/23/2017.
 */

public class Tweet {

    private String body;
    private long uid;//Unique TweetId

    public String getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    //private User user;
    private String createdAt;

    public User getUser() {
        return user;
    }

    private User user;


    //Deserialize Json
    //Create Tweet object from Json

    public static Tweet fromJson(JSONObject jsonObject) {

        Tweet tweet = new Tweet();
        try {

            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            //tweet.createdAt = jsonObject.getLong("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return  tweet;
    }

    //Create Tweet Array from jsonArray
    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }
            }catch ( Exception ex){
                ex.printStackTrace();
                continue;
            }

        }

        return  tweets;
    }

}
