package com.konifar.annict.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.konifar.annict.model.SearchType;
import com.konifar.annict.model.Season;
import com.konifar.annict.model.Work;
import com.konifar.annict.repository.WorkRepository;
import com.konifar.annict.util.PageNavigator;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchTabViewModel implements ViewModel {

    private final Context context;
    private final WorkRepository repository;
    private final PageNavigator navigator;

    private int currentPage = 1;
    private boolean isLoading;

    private SearchType type;

    public ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
    public ObservableInt recyclerViewVisibility = new ObservableInt(View.GONE);
    public ObservableInt footerProgressBarVisibility = new ObservableInt(View.GONE);

    @Inject
    public SearchTabViewModel(Context context,
                              WorkRepository repository,
                              PageNavigator navigator) {
        this.context = context;
        this.repository = repository;
        this.navigator = navigator;
    }

    public void setType(@NonNull SearchType type) {
        this.type = type;
    }

    public void incremantePage() {
        currentPage++;
    }

    public Observable<List<SearchItemViewModel>> showNextWorks() {
        if (isLoading) return Observable.empty();
        isLoading = true;

        int nextPage = currentPage + 1;

        return getWorksObservable(nextPage)
                .doOnSubscribe(() -> footerProgressBarVisibility.set(View.VISIBLE))
                .doOnCompleted(() -> {
                    isLoading = false;
                    footerProgressBarVisibility.set(View.GONE);
                })
                .doOnError((throwable) -> {
                    isLoading = false;
                    footerProgressBarVisibility.set(View.GONE);
                })
                .map(this::createViewModel);
    }

    private List<SearchItemViewModel> createViewModel(List<Work> works) {
        return Stream.of(works)
                .map(work -> new SearchItemViewModel(context, work, navigator))
                .collect(Collectors.toList());
    }

    public Observable<List<Work>> getWorksObservable(int page) {
        switch (type) {
            case SEASON:
                return repository.getWhereSeason(Season.SUMMER.getName(2016), page);
            case POPULAR:
                return repository.getOrderWatchersCountDesc(page);
            default:
                throw new IllegalStateException(type + " is not supported.");
        }
    }

    public Observable<List<Work>> getWorksObservableWithAuth(String authCode, int page) {
        switch (type) {
            case SEASON:
                return repository.getWhereSeasonWithAuth(authCode, Season.SUMMER.getName(2016), page);
            case POPULAR:
                return repository.getOrderWatchersCountDescWithAuth(authCode, page);
            default:
                throw new IllegalStateException(type + " is not supported.");
        }
    }

    public Observable<List<SearchItemViewModel>> showWorks(@Nullable String accessToken,
                                                           @NonNull String authCode) {
        if (isLoading) return Observable.empty();
        isLoading = true;

        return getLoadObservable(accessToken, authCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> progressBarVisibility.set(View.VISIBLE))
                .doOnCompleted(() -> {
                    isLoading = false;
                    progressBarVisibility.set(View.GONE);
                })
                .doOnError((throwable) -> {
                    isLoading = false;
                    progressBarVisibility.set(View.GONE);
                })
                .map(this::createViewModel);
    }

    private Observable<List<Work>> getLoadObservable(@Nullable String accessToken, @NonNull String authCode) {
        if (!TextUtils.isEmpty(accessToken)) {
            return getWorksObservable(1);
        } else {
            return getWorksObservableWithAuth(authCode, 1);
        }
    }

    @Override
    public void destroy() {
        // Do nothing
    }

}
