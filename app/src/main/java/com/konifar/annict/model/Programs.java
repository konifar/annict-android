package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

/**
 * https://annict.wikihub.io/wiki/api/me-programs
 */
@Parcel public class Programs {

  @SerializedName("programs") public List<Program> list;
}
