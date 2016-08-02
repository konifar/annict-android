package com.konifar.annict.view.fragment;

import android.support.v4.app.Fragment;

public interface MainTabPage {

    String ARG_AUTH_CODE = "auth_code";

    void scrollToTop();

    Fragment getFragment();

}
