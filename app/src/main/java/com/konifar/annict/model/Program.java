package com.konifar.annict.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import org.parceler.Parcel;

/**
 * https://annict.wikihub.io/wiki/api/me-programs
 */
@Parcel @Table public class Program {

  public static final String TAG = Program.class.getSimpleName();

  @PrimaryKey(auto = false) @Column(indexed = true) @SerializedName("id") public long id;

  @Column @SerializedName("started_at") public Date startedAt;

  @Column @SerializedName("is_rebroadcast") public boolean isRebroadcast;

  @Column(indexed = true) @SerializedName("channel") public Channel channel;

  @Column(indexed = true) @SerializedName("work") public Work work;

  @Column(indexed = true) @SerializedName("episode") public Episode episode;

  public Program() {
    //
  }
}
