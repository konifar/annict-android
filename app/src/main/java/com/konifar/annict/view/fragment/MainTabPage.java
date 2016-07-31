package com.konifar.annict.view.fragment;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

public interface MainTabPage {

    @StringRes
    int getTitleResId();

    void scrollToTop();

    Fragment getFragment();

}
