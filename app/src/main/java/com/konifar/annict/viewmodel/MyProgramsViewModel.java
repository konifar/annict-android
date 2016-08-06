package com.konifar.annict.viewmodel;

import com.konifar.annict.model.Program;
import com.konifar.annict.repository.ProgramRepository;
import com.konifar.annict.util.PageNavigator;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class MyProgramsViewModel extends AbstractListViewModel<Program, MyProgramItemViewModel> {

    private final Context context;

    private final ProgramRepository repository;

    private final PageNavigator pageNavigator;

    @Inject
    public MyProgramsViewModel(Context context, ProgramRepository repository,
        PageNavigator pageNavigator) {
        this.context = context;
        this.repository = repository;
        this.pageNavigator = pageNavigator;
    }

    @Override
    public Observable<List<Program>> getLoadObservable(int page) {
        return repository.getMineOrderByStartedAtDesc(page);
    }

    @Override
    MyProgramItemViewModel convertToViewModel(Program program) {
        return new MyProgramItemViewModel(context, program, pageNavigator);
    }

    @Override
    public Observable<List<Program>> getLoadObservableWithAuth(String authCode, int page) {
        return repository.getMineOrderByStartedAtDescWithAuth(authCode, page);
    }
}
