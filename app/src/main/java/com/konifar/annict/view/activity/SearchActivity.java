package com.konifar.annict.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivitySearchBinding;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.viewmodel.SearchViewModel;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @Inject
    SearchViewModel viewModel;

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initBackToolbar(binding.searchToolbar.getToolbar());
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
