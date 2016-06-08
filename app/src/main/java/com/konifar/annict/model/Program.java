package com.konifar.annict.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * https://annict.wikihub.io/wiki/api/me-programs
 */
@Parcel
@Table
public class Program {

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("id")
    public long id;

    @Column
    @SerializedName("started_at")
    public Date startedAt;

    @Column
    @SerializedName("is_rebroadcast")
    public boolean isRebroadcast;

    @Column(indexed = true)
    @SerializedName("channel")
    public boolean channel;

    @Column(indexed = true)
    @SerializedName("work")
    public boolean work;

    @Column(indexed = true)
    @SerializedName("episode")
    public boolean episode;

    public Program() {
        //
    }

}
