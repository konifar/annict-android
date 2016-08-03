package com.konifar.annict.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivitySearchBinding;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding binding;

    public static void start(Activity activity) {
        activity.startActivity(SearchActivity.createIntent(activity));
        activity.overridePendingTransition(0, R.anim.activity_fade_exit);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initBackToolbar(binding.searchToolbar.getToolbar());
    }

    @Override
    public void finish() {
        overridePendingTransition(0, R.anim.activity_fade_exit);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
