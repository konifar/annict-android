package com.konifar.annict.util;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.konifar.annict.R;
import com.konifar.annict.di.scope.ActivityScope;
import com.konifar.annict.model.Program;
import com.konifar.annict.view.activity.LoginActivity;
import com.konifar.annict.view.activity.EpisodeDetailActivity;
import com.konifar.annict.view.fragment.MyProgramsFragment;

import javax.inject.Inject;

@ActivityScope
public class PageNavigator {

    AppCompatActivity activity;

    @Inject
    public PageNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void finish() {
        activity.finish();
    }

    public void startCustomTab(@NonNull String url) {
        CustomTabsIntent intent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.theme500))
                .build();

        intent.launchUrl(activity, Uri.parse(url));
    }

    public void replaceMyProgramsFragment(@IdRes int layoutResId) {
        replaceFragment(MyProgramsFragment.newInstance(), layoutResId);
    }

    public void replaceMyProgramsFragment(@NonNull String authCode, @IdRes int layoutResId) {
        replaceFragment(MyProgramsFragment.newInstance(authCode), layoutResId);
    }

    public void startLoginActivity() {
        activity.startActivity(LoginActivity.createIntent(activity));
    }

    private void replaceFragment(@NonNull Fragment fragment, @IdRes int layoutResId) {
        final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(layoutResId, fragment, fragment.getClass().getSimpleName());
        ft.commit();
    }

    public void startProgramDetailActivity(@Nullable Program program) {
        activity.startActivity(EpisodeDetailActivity.createIntent(activity, program));
    }

}
