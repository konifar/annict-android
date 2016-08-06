package com.konifar.annict.viewmodel;

import android.view.View;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.util.PageNavigator;
import javax.inject.Inject;

public class LoginViewModel implements ViewModel {

  private final PageNavigator navigator;

  @Inject public LoginViewModel(PageNavigator navigator) {
    this.navigator = navigator;
  }

  public void onClickLoginButton(@SuppressWarnings("unused") View view) {
    navigator.startCustomTab(AnnictClient.getOAuthUrl());
  }

  @Override public void destroy() {
    // Do nothing
  }
}
