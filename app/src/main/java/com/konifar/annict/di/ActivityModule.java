package com.konifar.annict.di;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.konifar.annict.util.PageNavigator;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    public AppCompatActivity activity() {
        return activity;
    }

    @Provides
    LayoutInflater layoutInflater() {
        return activity.getLayoutInflater();
    }

    @Provides
    public PageNavigator provideActivityNavigator(AppCompatActivity activity) {
        return new PageNavigator(activity);
    }

}
