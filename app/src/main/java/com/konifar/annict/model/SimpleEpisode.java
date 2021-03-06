package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import org.parceler.Parcel;

import android.support.annotation.Nullable;

/**
 * https://annict.wikihub.io/wiki/api/episodes
 */
@Parcel
@Table
public class SimpleEpisode {

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public long id;

    @Nullable
    @Column
    @SerializedName("number")
    public String number;

    @Column
    @SerializedName("number_text")
    public String numberText;

    @Column
    @SerializedName("sort_number")
    public int sortNumber;

    @Column(indexed = true)
    @SerializedName("title")
    public String title;

    @Column
    @SerializedName("records_count")
    public int recordsCount;

    public SimpleEpisode() {
        //
    }
}
