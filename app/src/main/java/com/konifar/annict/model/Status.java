package com.konifar.annict.model;

import android.support.annotation.StringRes;

import com.konifar.annict.R;

/**
 * https://annict.wikihub.io/wiki/api/me-statuses
 */
public enum Status {

    NON(R.string.status_non),
    WANNA_WATCH(R.string.status_wanna_watch),
    WATCHING(R.string.status_watching),
    WATCHED(R.string.status_watched),
    ON_HOLD(R.string.status_on_hold),
    STOP_WATCHING(R.string.status_stop_watching),
    NO_SELECT(R.string.status_no_select);

    @StringRes
    public int stringRes;

    Status(@StringRes int stringRes) {
        this.stringRes = stringRes;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
