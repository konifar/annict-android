package com.konifar.annict.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.SearchType;
import com.konifar.annict.model.Season;
import com.konifar.annict.model.Works;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.util.PageNavigator;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchTabViewModel implements ViewModel {

    private final Context context;
    private final AnnictClient client;
    private final PageNavigator navigator;

    private int currentPage = 1;
    private boolean isLoading;

    private SearchType type;

    public ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
    public ObservableInt recyclerViewVisibility = new ObservableInt(View.GONE);
    public ObservableInt footerProgressBarVisibility = new ObservableInt(View.GONE);

    @Inject
    public SearchTabViewModel(Context context,
                              AnnictClient client,
                              PageNavigator navigator) {
        this.context = context;
        this.client = client;
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> footerProgressBarVisibility.set(View.VISIBLE))
                .doOnCompleted(() -> {
                    isLoading = false;
                    footerProgressBarVisibility.set(View.GONE);
                })
                .doOnError((throwable) -> {
                    isLoading = false;
                    footerProgressBarVisibility.set(View.GONE);
                })
                .map(works ->
                        Stream.of(works.list)
                                .map(work -> new SearchItemViewModel(context, work, navigator))
                                .collect(Collectors.toList())
                );
    }

    public Observable<Works> getWorksObservable(int page) {
        switch (type) {
            case SEASON:
                return client.getWorksWhereSeason(Season.SUMMER.getName(2016), page);
            case POPULAR:
                return client.getWorksSortByWatchersCount(page);
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
                .map(works ->
                        Stream.of(works.list)
                                .map(work -> new SearchItemViewModel(context, work, navigator))
                                .collect(Collectors.toList())
                );
    }

    private Observable<Works> getLoadObservable(@Nullable String accessToken, @NonNull String authCode) {
        if (!TextUtils.isEmpty(accessToken)) {
            return getWorksObservable(1);
        } else {
            return client.postOauthToken(authCode)
                    .flatMap(token -> {
                        DefaultPrefs.get(context).putAccessToken(token.accessToken);
                        return getWorksObservable(1);
                    });
        }
    }

    @Override
    public void destroy() {
        // Do nothing
    }

}
