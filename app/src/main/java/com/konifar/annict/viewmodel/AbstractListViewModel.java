package com.konifar.annict.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import rx.Observable;

public abstract class AbstractListViewModel<MODEL, ITEM_VIEW_MODEL> implements ViewModel {

    public ObservableInt progressBarVisibility = new ObservableInt(View.GONE);

    public ObservableInt recyclerViewVisibility = new ObservableInt(View.GONE);

    public ObservableInt footerProgressBarVisibility = new ObservableInt(View.GONE);

    private int currentPage = 1;

    private boolean isLoading;

    public void incremantePage() {
        currentPage++;
    }

    public Observable<List<ITEM_VIEW_MODEL>> showNext() {
        if (isLoading) {
            return Observable.empty();
        }
        isLoading = true;

        int nextPage = currentPage + 1;

        return getLoadObservable(nextPage).doOnSubscribe(
            () -> footerProgressBarVisibility.set(View.VISIBLE)).doOnCompleted(() -> {
            isLoading = false;
            footerProgressBarVisibility.set(View.GONE);
        }).doOnError((throwable) -> {
            isLoading = false;
            footerProgressBarVisibility.set(View.GONE);
        }).map(this::createViewModel);
    }

    public abstract Observable<List<MODEL>> getLoadObservable(int page);

    private List<ITEM_VIEW_MODEL> createViewModel(List<MODEL> works) {
        return Stream.of(works).map(this::convertToViewModel).collect(Collectors.toList());
    }

    abstract ITEM_VIEW_MODEL convertToViewModel(MODEL model);

    public Observable<List<ITEM_VIEW_MODEL>> showWithAuth(@Nullable String accessToken,
        @NonNull String authCode) {
        if (isLoading) {
            return Observable.empty();
        }
        isLoading = true;

        return getLoadObservable(accessToken, authCode)
            .doOnSubscribe(() ->
                progressBarVisibility.set(View.VISIBLE)).doOnCompleted(() -> {
                isLoading = false;
                progressBarVisibility.set(View.GONE);
            })
            .doOnError(throwable -> {
                isLoading = false;
                progressBarVisibility.set(View.GONE);
            })
            .map(this::createViewModel);
    }

    private Observable<List<MODEL>> getLoadObservable(@Nullable String accessToken,
        @NonNull String authCode) {
        if (!TextUtils.isEmpty(accessToken)) {
            return getLoadObservable(1);
        } else {
            return getLoadObservableWithAuth(authCode, 1);
        }
    }

    public abstract Observable<List<MODEL>> getLoadObservableWithAuth(String authCode, int page);

    @Override
    public void destroy() {
        // Do nothing
    }
}
