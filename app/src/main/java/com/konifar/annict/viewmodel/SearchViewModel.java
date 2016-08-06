package com.konifar.annict.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.konifar.annict.util.PageNavigator;
import javax.inject.Inject;

public class SearchViewModel extends BaseObservable implements ViewModel {

  private final PageNavigator navigator;

  @Inject public SearchViewModel(PageNavigator navigator) {
    this.navigator = navigator;
  }

  public void showData(@Nullable String accessToken, @Nullable String authCode,
      @IdRes int layoutResId) {
    if (!TextUtils.isEmpty(accessToken)) {
      navigator.replaceSearchFragment(layoutResId);
    } else {
      // After authentication, intent has uri data including auth code.
      if (TextUtils.isEmpty(authCode)) {
        navigator.startLoginActivity();
        navigator.finish();
      } else {
        navigator.replaceSearchFragment(authCode, layoutResId);
      }
    }
  }

  @Override public void destroy() {
    // Do nothing
  }
}
