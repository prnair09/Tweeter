package com.rakesh.tweeter.Helper;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by rparuthi on 3/26/2017.
 */

public class Utility {

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        simpleDateFormat.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = simpleDateFormat.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
