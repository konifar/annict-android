package com.konifar.annict.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.konifar.annict.R;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.databinding.ActivityMainBinding;
import com.konifar.annict.fragment.MyProgramsFragment;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    AnnictClient client;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getComponent().inject(this);

        // After authentication, intent has uri data including auth code.
        String authCode = extractAuthCode();
        if (TextUtils.isEmpty(authCode)) {
            startActivity(LoginActivity.createIntent(this));
            finish();
        } else {
            replaceFragment(MyProgramsFragment.newInstance(authCode), R.id.content_view);
        }
    }

    private String extractAuthCode() {
        if (getIntent() != null && getIntent().getData() != null) {
            String authCode = getIntent().getData().getQueryParameter("code");
            Log.d(TAG, "authCode:" + authCode);
            return authCode;
        } else {
            return "";
        }
    }

}