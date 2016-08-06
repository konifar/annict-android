package com.konifar.annict.viewmodel;

import android.content.Context;

import com.konifar.annict.model.Season;
import com.konifar.annict.model.Work;
import com.konifar.annict.repository.WorkRepository;
import com.konifar.annict.util.PageNavigator;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class SearchSeasonViewModel extends AbstractListViewModel<Work, SearchItemViewModel> {

    private final Context context;
    private final WorkRepository repository;
    private final PageNavigator navigator;

    @Inject
    public SearchSeasonViewModel(Context context,
                                 WorkRepository repository,
                                 PageNavigator navigator) {
        this.context = context;
        this.repository = repository;
        this.navigator = navigator;
    }

    @Override
    public Observable<List<Work>> getLoadObservable(int page) {
        return repository.getWhereSeason(Season.SUMMER.getName(2016), page);
    }

    @Override
    public Observable<List<Work>> getLoadObservableWithAuth(String authCode, int page) {
        return repository.getWhereSeasonWithAuth(authCode, Season.SUMMER.getName(2016), page);
    }

    @Override
    SearchItemViewModel convertToViewModel(Work work) {
        return new SearchItemViewModel(context, work, navigator);
    }
}
