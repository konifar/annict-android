package com.konifar.annict.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivityLoginBinding;
import com.konifar.annict.view.fragment.LoginFragment;

public class LoginActivity extends BaseActivity {

  private ActivityLoginBinding binding;

  public static Intent createIntent(Context context) {
    return new Intent(context, LoginActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    getComponent().inject(this);

    replaceFragment(LoginFragment.newInstance(), R.id.content_view);

    overridePendingTransition(0, 0);
  }

  @Override public void finish() {
    super.finish();
    overridePendingTransition(R.anim.activity_fade_enter, R.anim.activity_fade_exit);
  }
}
