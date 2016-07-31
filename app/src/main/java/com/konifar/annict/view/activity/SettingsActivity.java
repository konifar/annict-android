package com.konifar.annict.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivitySettingsBinding;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        initBackToolbar(binding.toolbar);
    }
}
