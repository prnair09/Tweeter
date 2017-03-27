package com.rakesh.tweeter.Activites;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rakesh.tweeter.Adapters.TweetsArrayAdapter;
import com.rakesh.tweeter.Fragments.ComposeTweetFragment;
import com.rakesh.tweeter.Listeners.EndlessScrollListener;
import com.rakesh.tweeter.Models.Tweet;
import com.rakesh.tweeter.Models.User;
import com.rakesh.tweeter.R;
import com.rakesh.tweeter.TweeterApplication;
import com.rakesh.tweeter.TweeterClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.offset;

public class TimelineActivity extends BaseActivity
    implements ComposeTweetFragment.ComposeTweetHandler
{

    Toolbar toolbar;

    private TweeterClient tweeterClient;
    private ArrayList<Tweet> tweets;
    //private TweetsAdapter aTweets;
    private TweetsArrayAdapter aTweets;

    private ListView lvTweets;
    private RecyclerView rvTweets;

    long max_id = 0;
    long since_id = 1;

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        //Set Toolbar
        setSupportActionBar(toolbar);


        //Find ListView
        lvTweets = (ListView)findViewById(R.id.lvTweets);

        //rvTweets = (RecyclerView) findViewById(R.id.rvTweets);

        tweets = new ArrayList<>();

        //Create Adapter
        aTweets = new TweetsArrayAdapter(this,tweets);
        //aTweets = new TweetsAdapter(this,R.layout.item_tweet,tweets);

        //Set the listview adapter
        lvTweets.setAdapter(aTweets);


        //rvTweets.setAdapter(aTweets);

        //rvTweets.setLayoutManager(new LinearLayoutManager(this));


        tweeterClient = TweeterApplication.getRestClient();

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeLine(page);

                return  true;
            }
        });

        populateTimeLine(0);

        getCurentUser();


    }

    private void getCurentUser() {
        tweeterClient.getUser(new TextHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG",responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if(responseString != null) {
                    try {
                        JSONObject objJson = new JSONObject(responseString);
                        currentUser = User.fromJson(objJson);

                        Log.d("DEBUG",currentUser.getName());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }



    private void populateTimeLine(int offSet){
        final int mPage = offset;

        tweeterClient.getHomeTimeline(new JsonHttpResponseHandler(){
            //Success


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());


                //Deserialize Json

                //Create models
                ArrayList<Tweet> newTweets = Tweet.fromJsonArray(response);

                if(newTweets.size() > 0) {
                    max_id = (long) newTweets.get(newTweets.size()-1).getUid()- 1;
                    //tweets.addAll(mPage == 0 ? 0:tweets.size() - 1, newTweets);
                    //tweets.addAll(tweets.size() == 0 ? 0:tweets.size() - 1, newTweets);
                    //aTweets.notifyDataSetChanged();
                    //aTweets.addTweets(tweets,offSet);
                    aTweets.addAll(newTweets);
                }


            }


            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG",errorResponse.toString());
                Toast.makeText(getApplicationContext(),"API limit reached",Toast.LENGTH_SHORT).show();
            }
        },max_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate the menu
        getMenuInflater().inflate(R.menu.menu_items,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_pulltop){
            Toast.makeText(this,"Clicked on Pull top",Toast.LENGTH_SHORT).show();

        }else if (id == R.id.action_compose){
            showCompose();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCompose() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser);
        composeTweetFragment.show(fragmentManager, "Compose_Tweet");
    }

    @Override
    public void onTweetSuccess(Tweet tweet) {
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();
        //lvTweets.smoothScrollToPosition(0);
        lvTweets.setSelection(0);

    }
}
