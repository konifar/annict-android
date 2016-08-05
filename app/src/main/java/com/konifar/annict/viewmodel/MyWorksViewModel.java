package com.konifar.annict.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.konifar.annict.model.Status;
import com.konifar.annict.model.Work;
import com.konifar.annict.repository.StatusRepository;
import com.konifar.annict.repository.WorkRepository;
import com.konifar.annict.util.PageNavigator;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class MyWorksViewModel implements ViewModel {

    private final Context context;
    private final WorkRepository workRepository;
    private final StatusRepository statusRepository;
    private final PageNavigator pageNavigator;

    private int currentPage = 1;
    private boolean isLoading;

    private Status status = Status.NO_SELECT;

    public ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
    public ObservableInt recyclerViewVisibility = new ObservableInt(View.GONE);
    public ObservableInt footerProgressBarVisibility = new ObservableInt(View.GONE);

    @Inject
    public MyWorksViewModel(Context context,
                            WorkRepository workRepository,
                            StatusRepository statusRepository,
                            PageNavigator pageNavigator) {
        this.context = context;
        this.workRepository = workRepository;
        this.statusRepository = statusRepository;
        this.pageNavigator = pageNavigator;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    public void incremantePage() {
        currentPage++;
    }

    public Observable<List<WorkItemViewModel>> showNextWorks() {
        if (isLoading) return Observable.empty();
        isLoading = true;

        int nextPage = currentPage + 1;

        return workRepository.getMineWhereStatus(status, nextPage)
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

    public Observable<List<WorkItemViewModel>> showWorks(@Nullable String accessToken,
                                                         @NonNull String authCode) {
        if (isLoading) return Observable.empty();
        isLoading = true;

        return getLoadObservable(accessToken, authCode)
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

    private List<WorkItemViewModel> createViewModel(List<Work> works) {
        return Stream.of(works)
                .map(work -> new WorkItemViewModel(context, work, status, pageNavigator, statusRepository))
                .collect(Collectors.toList());
    }

    private Observable<List<Work>> getLoadObservable(@Nullable String accessToken, @NonNull String authCode) {
        if (!TextUtils.isEmpty(accessToken)) {
            return workRepository.getMineWhereStatus(status, 1);
        } else {
            return workRepository.getMineWhereStatusWithAuth(authCode, status, 1);
        }
    }

    @Override
    public void destroy() {
        //
    }
}
