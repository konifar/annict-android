package com.konifar.annict.model;

import com.konifar.annict.R;

import android.support.annotation.StringRes;

public enum SearchType {

    SEASON(R.string.search_season),
    POPULAR(R.string.search_popular);

    public int nameResId;

    SearchType(@StringRes int nameResId) {
        this.nameResId = nameResId;
    }
}
