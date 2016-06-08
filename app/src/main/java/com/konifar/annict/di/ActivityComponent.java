package com.konifar.annict.di;

import com.konifar.annict.activity.MainActivity;
import com.konifar.annict.activity.OAuthActivity;
import com.konifar.annict.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(OAuthActivity activity);

    FragmentComponent plus(FragmentModule module);

}
