package com.konifar.annict.di;

import com.konifar.annict.di.scope.ActivityScope;
import com.konifar.annict.view.activity.LoginActivity;
import com.konifar.annict.view.activity.MainActivity;
import com.konifar.annict.view.activity.ProgramDetailActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(ProgramDetailActivity activity);

    FragmentComponent plus(FragmentModule module);

}
