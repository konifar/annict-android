package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import org.parceler.Parcel;

/**
 * https://annict.wikihub.io/wiki/api/me-programs
 */
@Parcel
@Table
public class Channel {

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public long id;

    @Column(indexed = true)
    @SerializedName("name")
    public String name;

    public Channel() {
        //
    }
}
