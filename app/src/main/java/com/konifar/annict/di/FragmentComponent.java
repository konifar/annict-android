package com.konifar.annict.di;

import com.konifar.annict.di.scope.FragmentScope;
import com.konifar.annict.fragment.LoginFragment;
import com.konifar.annict.fragment.MyProgramsFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MyProgramsFragment fragment);

    void inject(LoginFragment fragment);

}
