package com.konifar.annict.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * https://annict.wikihub.io/wiki/api/works
 */
@Parcel
@Table
public class Work {

    public static final String TAG = Work.class.getSimpleName();

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public long id;

    @Column(indexed = true)
    @SerializedName("title")
    public String title;

    @Column
    @SerializedName("title_kana")
    public String titleKana;

    @Column
    @SerializedName("media")
    public String media;

    @Column
    @SerializedName("media_text")
    public String mediaText;

    @Column
    @SerializedName("season_name")
    public String seasonName;

    @Column
    @SerializedName("season_name_text")
    public String seasonNameText;

    @Column
    @SerializedName("released_on")
    public String releasedOn;

    @Column
    @SerializedName("released_on_about")
    public String releasedOnAbout;

    @Column
    @SerializedName("official_site_url")
    public String officialSiteUrl;

    @Column
    @SerializedName("wikipedia_url")
    public String wikipediaUrl;

    @Column
    @SerializedName("twitter_username")
    public String twitterUserName;

    @Column
    @SerializedName("twitter_hashtag")
    public String twitterHashtag;

    @Column
    @SerializedName("episodes_count")
    public int episodesCount;

    @Column
    @SerializedName("watchers_count")
    public int watchersCount;

    public Work() {
        //
    }

}
