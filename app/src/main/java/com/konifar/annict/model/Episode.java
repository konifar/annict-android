package com.konifar.annict.model;

import android.support.annotation.Nullable;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * https://annict.wikihub.io/wiki/api/episodes
 */
@Parcel @Table public class Episode {

  @PrimaryKey(auto = false) @Column(indexed = true) @SerializedName("id") public long id;

  @Nullable @Column @SerializedName("number") public String number;

  @Column @SerializedName("number_text") public String numberText;

  @Column @SerializedName("sort_number") public int sortNumber;

  @Column(indexed = true) @SerializedName("title") public String title;

  @Column @SerializedName("records_count") public int recordsCount;

  @Nullable @Column(indexed = true) @SerializedName("work") public Work work;

  @Nullable @Column @SerializedName("prev_episode") public SimpleEpisode prevEpisode;

  @Nullable @Column @SerializedName("next_episode") public SimpleEpisode nextEpisode;

  public Episode() {
    //
  }
}
