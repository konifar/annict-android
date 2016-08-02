package com.konifar.annict.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Status;
import com.konifar.annict.model.Work;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MyWorkItemViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = MyWorkItemViewModel.class.getSimpleName();

    public String thumbUrl;

    public String title;

    public String seasonNameText;

    public String mediaText;

    public String watchersCount;

    @Bindable
    public Status status;

    public final Work work;

    private final PageNavigator pageNavigator;

    private final AnnictClient client;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public MyWorkItemViewModel(@NonNull Work work, Status status,
                               PageNavigator pageNavigator, AnnictClient client) {
        title = work.title;
        if (work.twitterUserName != null) {
            thumbUrl = ViewHelper.getTwitterProfileImageUrl(work.twitterUserName);
        }
        seasonNameText = work.seasonNameText;
        mediaText = work.mediaText;
        watchersCount = String.valueOf(work.watchersCount);

        this.status = status;
        this.work = work;
        this.pageNavigator = pageNavigator;
        this.client = client;
    }

    @Override
    public void destroy() {
        compositeSubscription.unsubscribe();
    }

    public void onClickRoot(@SuppressWarnings("unused") View view) {
        pageNavigator.startWorkDetailActivity(work);
    }

    public void onClickImage(@SuppressWarnings("unused") View view) {
        pageNavigator.startWorkDetailActivity(work);
    }

    public void onClickStatusChange(@SuppressWarnings("unused") View view) {
        pageNavigator.showStatusSelectDialog(status, selectedStatus -> {
            this.status = selectedStatus;
            notifyPropertyChanged(BR.status);
            updateStatus();
        });
    }

    private void updateStatus() {
        Subscription sub = client.postMeStatuses(work.id, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeSubscription.add(sub);
    }

}
