package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import org.parceler.Parcel;

import android.support.annotation.Nullable;

import java.util.Date;

/**
 * https://annict.wikihub.io/wiki/api/records
 */
@Parcel
@Table
public class Record {

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public long id;

    @Column
    @SerializedName("comment")
    public String comment;

    @Nullable
    @Column
    @SerializedName("rating")
    public Long rating;

    @Column
    @SerializedName("is_modified")
    public boolean isModified;

    @Column
    @SerializedName("likes_count")
    public int likesCount;

    @Column
    @SerializedName("comments_count")
    public int commentsCount;

    @Column
    @SerializedName("created_at")
    public Date createdAt;

    @Column(indexed = true)
    @SerializedName("user")
    public User user;

    @Column(indexed = true)
    @SerializedName("work")
    public Work work;

    @Column(indexed = true)
    @SerializedName("episode")
    public Work episode;

    public Record() {
        //
    }
}
