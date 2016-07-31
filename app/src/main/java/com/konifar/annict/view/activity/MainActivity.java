package com.konifar.annict.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivityMainBinding;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.viewmodel.MainViewModel;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainViewModel viewModel;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getComponent().inject(this);

        viewModel.showData(DefaultPrefs.get(this).getAccessToken(), extractAuthCode(), R.id.content_view);
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
