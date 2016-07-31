package com.konifar.annict.di;

import com.konifar.annict.di.scope.FragmentScope;
import com.konifar.annict.view.fragment.LoginFragment;
import com.konifar.annict.view.fragment.MyProgramsFragment;
import com.konifar.annict.view.fragment.RecordCreateDialogFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MyProgramsFragment fragment);

    void inject(LoginFragment fragment);

    void inject(RecordCreateDialogFragment fragment);

}
