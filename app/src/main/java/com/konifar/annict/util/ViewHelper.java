package com.konifar.annict.util;

import com.konifar.annict.R;
import com.konifar.annict.model.Episode;

import android.content.Context;
import android.text.TextUtils;

public class ViewHelper {

    /**
     * http://furyu.hatenablog.com/entry/20130730/1375178609
     * TODO this is temporary for now.
     */
    public static String getTwitterProfileImageUrl(String twitterUserName) {
        return "http://furyu.nazo.cc/twicon/" + twitterUserName + "/bigger";
    }

    public static String getEpisodeTitle(Episode episode, Context context) {
        if (episode == null) {
            return "";
        }

        if (TextUtils.isEmpty(episode.title)) {
            return episode.numberText;
        } else {
            return context.getString(R.string.episode_brackets, episode.numberText, episode.title);
        }
    }
}
