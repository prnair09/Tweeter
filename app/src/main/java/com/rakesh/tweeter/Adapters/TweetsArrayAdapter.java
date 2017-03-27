package com.rakesh.tweeter.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rakesh.tweeter.Helper.Utility;
import com.rakesh.tweeter.Models.Tweet;
import com.rakesh.tweeter.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by rparuthi on 3/25/2017.
 */

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
//public class TweetsArrayAdapter extends BaseAdapter {


    Context ctx;
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {

        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    /*
    public TweetsArrayAdapter(Context context,ArrayList<Tweet> tweets){
        ctx = context;
        tweets = tweets;
    }

    @Override
    public Tweet getItem(int position){
        return tweets.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getCount() {

        return (null != tweets ? tweets.size() : 0);
    }

    //Override and setup custom template



    public  void addTweets(ArrayList<Tweet> newTweets,int offset) {

        if(tweets == null){
            tweets = new ArrayList<Tweet>();
        }
        tweets.addAll(offset == 0 ? 0:tweets.size() - 1, newTweets);
        this.notifyDataSetChanged();
        //this.notifyItemInserted(newArticles.size() - 1);
       // this.notifyItemRangeInserted(startposition, count);

    }*/



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //GetTweet
        Tweet tweet = getItem(position);

        TweetsViewHolder holder;

        //find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            holder = new TweetsViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (TweetsViewHolder)convertView.getTag();
        }

        holder.twTweetAuthorFullname.setText(tweet.getUser().getName());

        holder.twTweetAuthorScreenname.setText(tweet.getUser().getScreenName());

        holder.twTweetText.setText(tweet.getBody());

        holder.twTweetTimestamp.setText(Utility.getRelativeTimeAgo(tweet.getCreatedAt()));


        Picasso.with(getContext()).
                load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(4,4))
                .into(holder.twTweetauthorAvatar);

        return convertView;
    }


    public class TweetsViewHolder {
        TextView twTweetRetweetedBy;
        ImageView twTweetauthorAvatar;
        TextView twTweetAuthorFullname;
        TextView twTweetAuthorScreenname;
        TextView twTweetTimestamp;
        TextView twTweetText;
        FrameLayout quoteTweetHolder;
        LinearLayout twTweetActionBar;
        View bottomSeparator;


        public TweetsViewHolder(View view){

            twTweetRetweetedBy = (TextView) view.findViewById(R.id.tw__tweet_retweeted_by);

            twTweetauthorAvatar = (ImageView) view.findViewById(R.id.tw__tweet_author_avatar);

            twTweetAuthorFullname = (TextView) view.findViewById(R.id.tw__tweet_author_full_name);

            twTweetAuthorScreenname = (TextView) view.findViewById(R.id.tw__tweet_author_screen_name);

            twTweetTimestamp = (TextView) view.findViewById(R.id.tw__tweet_timestamp);

            twTweetText = (TextView) view.findViewById(R.id.tw__tweet_text);

            quoteTweetHolder = (FrameLayout) view.findViewById(R.id.quote_tweet_holder);

            twTweetActionBar = (LinearLayout)view.findViewById(R.id.tw__tweet_action_bar);

            bottomSeparator = (View)view.findViewById(R.id.bottom_separator);

        }
    }
}
