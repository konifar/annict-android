package com.konifar.annict.di;

import com.konifar.annict.StethoWrapper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(StethoWrapper stethoDelegator);

    ActivityComponent plus(ActivityModule module);
}
