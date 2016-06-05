package com.konifar.annict;

import android.app.Application;
import android.support.annotation.NonNull;

import com.konifar.annict.di.AppComponent;
import com.konifar.annict.di.AppModule;
import com.konifar.annict.di.DaggerAppComponent;

public class MainApplication extends Application {

    AppComponent appComponent;

    @NonNull
    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        new StethoWrapper(this).setup();
    }

}
