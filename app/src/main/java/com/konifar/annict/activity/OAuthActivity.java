package com.konifar.annict.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivityOauthBinding;

public class OAuthActivity extends BaseActivity {

    private static final String TAG = OAuthActivity.class.getSimpleName();

    private ActivityOauthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_oauth);
        getComponent().inject(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || intent.getData() == null) return;

        String authenticationCode = intent.getData().getQueryParameter("code");
        Log.d(TAG, "code:" + authenticationCode);
    }

}
