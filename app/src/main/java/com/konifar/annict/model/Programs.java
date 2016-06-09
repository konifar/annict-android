package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * https://annict.wikihub.io/wiki/api/me-programs
 */
@Parcel
public class Programs {

    @SerializedName("programs")
    public List<Program> list;

}
