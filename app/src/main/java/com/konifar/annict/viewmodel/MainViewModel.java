package com.konifar.annict.viewmodel;

import com.konifar.annict.util.PageNavigator;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import javax.inject.Inject;

public class MainViewModel implements ViewModel {

    private final PageNavigator navigator;

    @Inject
    public MainViewModel(PageNavigator navigator) {
        this.navigator = navigator;
    }

    public void showData(@Nullable String accessToken, @Nullable String authCode,
        @IdRes int layoutResId) {
        if (!TextUtils.isEmpty(accessToken)) {
            navigator.replaceMainFragment(layoutResId);
        } else {
            // After authentication, intent has uri data including auth code.
            if (TextUtils.isEmpty(authCode)) {
                navigator.startLoginActivity();
                navigator.finish();
            } else {
                navigator.replaceMainFragment(authCode, layoutResId);
            }
        }
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    public void onClickSearchMenu() {
        navigator.startSearchActivity();
    }

    public void onClickSettingMenu() {
        navigator.startSettingsActivity();
    }
}
