package com.konifar.annict.di;

import com.konifar.annict.StethoWrapper;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = AppModule.class) public interface AppComponent {

  void inject(StethoWrapper stethoDelegator);

  ActivityComponent plus(ActivityModule module);
}
