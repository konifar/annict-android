package com.konifar.annict.di;

import com.konifar.annict.di.scope.ActivityScope;
import com.konifar.annict.view.activity.EpisodeDetailActivity;
import com.konifar.annict.view.activity.LoginActivity;
import com.konifar.annict.view.activity.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(EpisodeDetailActivity activity);

    FragmentComponent plus(FragmentModule module);

}
