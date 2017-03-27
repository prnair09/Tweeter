package com.rakesh.tweeter.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rakesh.tweeter.Models.Tweet;
import com.rakesh.tweeter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by rparuthi on 3/25/2017.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetsViewHolder> {


    ArrayList<Tweet> tweets;
    private Context ctx;
    private int rowLayout;

    public class TweetsViewHolder extends RecyclerView.ViewHolder{
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
            super(view);
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

    public TweetsAdapter(Context context,int rowLayout, ArrayList<Tweet> tweets){
        this.ctx = context;
        this.rowLayout = rowLayout;
        this.tweets = tweets;
    }


    @Override
    public int getItemCount() {

        return (null != tweets ? tweets.size() : 0);
    }

    @Override
    public TweetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return  new TweetsViewHolder(view);
    }

    public  void addTweets(ArrayList<Tweet> newTweets,int startposition, int count) {

        tweets.addAll(newTweets);
        this.notifyDataSetChanged();
        //tweets.addAll(startposition, newArticles);
        //this.notifyItemInserted(newArticles.size() - 1);
        //this.notifyItemRangeInserted(startposition, count);

    }

    @Override
    public void onBindViewHolder(TweetsViewHolder holder, final int position) {

        Tweet tweet = tweets.get(position);

        holder.twTweetAuthorFullname.setText(tweet.getUser().getName());

        holder.twTweetAuthorScreenname.setText(tweet.getUser().getScreenName());

        holder.twTweetText.setText(tweet.getBody());


        Picasso.with(getContext()).
                load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(4,4))
                .into(holder.twTweetauthorAvatar);

    }
}
