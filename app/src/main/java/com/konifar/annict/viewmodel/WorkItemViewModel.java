package com.konifar.annict.viewmodel;

import com.android.databinding.library.baseAdapters.BR;
import com.konifar.annict.R;
import com.konifar.annict.model.Status;
import com.konifar.annict.model.Work;
import com.konifar.annict.repository.StatusRepository;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class WorkItemViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = WorkItemViewModel.class.getSimpleName();

    public final Work work;

    private final PageNavigator pageNavigator;

    private final StatusRepository repository;

    public String thumbUrl;

    public String title;

    public String seasonNameText;

    public String mediaText;

    public String watchersCount;

    public String episodesCount;

    @Bindable
    public Status status;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public WorkItemViewModel(Context context, @NonNull Work work, Status status,
        PageNavigator pageNavigator, StatusRepository repository) {
        title = work.title;
        if (work.twitterUserName != null) {
            thumbUrl = ViewHelper.getTwitterProfileImageUrl(work.twitterUserName);
        }
        seasonNameText = work.seasonNameText;
        mediaText = work.mediaText;
        watchersCount = context.getResources()
            .getQuantityString(R.plurals.people_count, work.watchersCount, work.watchersCount);
        episodesCount = context.getResources()
            .getQuantityString(R.plurals.episodes_count, work.episodesCount, work.episodesCount);

        this.status = status;
        this.work = work;
        this.pageNavigator = pageNavigator;
        this.repository = repository;
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
        Subscription sub = repository.edit(work.id, status).subscribe();
        compositeSubscription.add(sub);
    }
}
