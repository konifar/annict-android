package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

/**
 * https://annict.wikihub.io/wiki/api/me-works
 */
@Parcel public class Works {

  @SerializedName("works") public List<Work> list;
}
