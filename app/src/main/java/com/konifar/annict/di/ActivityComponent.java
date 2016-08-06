package com.konifar.annict.di;

import com.konifar.annict.di.scope.ActivityScope;
import com.konifar.annict.view.activity.EpisodeDetailActivity;
import com.konifar.annict.view.activity.LoginActivity;
import com.konifar.annict.view.activity.MainActivity;
import com.konifar.annict.view.activity.SearchActivity;
import com.konifar.annict.view.activity.SettingsActivity;
import com.konifar.annict.view.activity.WorkDetailActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(EpisodeDetailActivity activity);

    void inject(WorkDetailActivity activity);

    void inject(SettingsActivity activity);

    void inject(SearchActivity activity);

    FragmentComponent plus(FragmentModule module);
}
