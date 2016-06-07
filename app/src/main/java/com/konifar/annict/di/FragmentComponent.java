package com.konifar.annict.di;

import com.konifar.annict.di.scope.FragmentScope;
import com.konifar.annict.fragment.LoginFragment;
import com.konifar.annict.fragment.WorksFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(WorksFragment fragment);

    void inject(LoginFragment fragment);

}
