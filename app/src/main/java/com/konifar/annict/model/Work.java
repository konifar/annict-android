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

    /**
     * 4681
     */
    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public long id;

    /**
     * ふらいんぐうぃっち
     */
    @Column(indexed = true)
    @SerializedName("title")
    public String title;

    /**
     * ふらいんぐうぃっち
     */
    @Column
    @SerializedName("title_kana")
    public String titleKana;

    /**
     * tv
     */
    @Column
    @SerializedName("media")
    public String media;

    /**
     * TV
     */
    @Column
    @SerializedName("media_text")
    public String mediaText;

    /**
     * 2016-spring
     */
    @Column
    @SerializedName("season_name")
    public String seasonName;

    /**
     * 2016年春
     */
    @Column
    @SerializedName("season_name_text")
    public String seasonNameText;

    /**
     *
     */
    @Column
    @SerializedName("released_on")
    public String releasedOn;

    /**
     *
     */
    @Column
    @SerializedName("released_on_about")
    public String releasedOnAbout;

    /**
     * http://www.flyingwitch.jp/
     */
    @Column
    @SerializedName("official_site_url")
    public String officialSiteUrl;

    /**
     * https://ja.wikipedia.org/wiki/%E3%81%B5%E3%82%89%E3%81%84%E3%82%93%E3%81%90%E3%81%86%E3%81%83%E3%81%A3%E3%81%A1
     */
    @Column
    @SerializedName("wikipedia_url")
    public String wikipediaUrl;

    /**
     * flying_tv
     */
    @Column
    @SerializedName("twitter_username")
    public String twitterUserName;

    /**
     * flyingwitch
     */
    @Column
    @SerializedName("twitter_hashtag")
    public String twitterHashtag;

    /**
     * 5
     */
    @Column
    @SerializedName("episodes_count")
    public int episodesCount;

    /**
     * 695
     */
    @Column
    @SerializedName("watchers_count")
    public int watchersCount;

    public Work() {
        //
    }

}
