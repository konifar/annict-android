package com.konifar.annict.model;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * https://annict.wikihub.io/wiki/api/records
 */
@Parcel
@Table
public class User {

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public long id;

    @Column(indexed = true)
    @SerializedName("username")
    public String username;

    @Column
    @SerializedName("description")
    public String description;

    @Nullable
    @Column
    @SerializedName("url")
    public String url;

    @Column
    @SerializedName("records_count")
    public int recordsCount;

    @Column
    @SerializedName("created_at")
    public Date createdAt;

    public User() {
        //
    }

}
