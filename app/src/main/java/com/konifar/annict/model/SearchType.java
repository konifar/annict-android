package com.konifar.annict.model;

import android.support.annotation.StringRes;
import com.konifar.annict.R;

public enum SearchType {

  SEASON(R.string.search_season),
  POPULAR(R.string.search_popular);

  public int nameResId;

  SearchType(@StringRes int nameResId) {
    this.nameResId = nameResId;
  }
}
