package com.konifar.annict.di;

import com.konifar.annict.di.scope.FragmentScope;
import com.konifar.annict.view.fragment.LoginFragment;
import com.konifar.annict.view.fragment.MainFragment;
import com.konifar.annict.view.fragment.MyProgramsFragment;
import com.konifar.annict.view.fragment.MyWorksFragment;
import com.konifar.annict.view.fragment.RecordCreateDialogFragment;
import com.konifar.annict.view.fragment.SearchFragment;
import com.konifar.annict.view.fragment.SearchTabFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MainFragment fragment);

    void inject(MyProgramsFragment fragment);

    void inject(LoginFragment fragment);

    void inject(RecordCreateDialogFragment fragment);

    void inject(MyWorksFragment fragment);

    void inject(SearchFragment fragment);

    void inject(SearchTabFragment fragment);
}
