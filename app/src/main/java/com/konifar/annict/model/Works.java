package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * https://annict.wikihub.io/wiki/api/me-works
 */
@Parcel
public class Works {

    @SerializedName("works")
    public List<Work> list;
}
