package com.konifar.annict.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.konifar.annict.model.Status;
import com.konifar.annict.model.Work;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

public class MyWorkItemViewModel extends BaseObservable implements ViewModel {

    public String thumbUrl;

    public String title;

    public String seasonNameText;

    public String mediaText;

    public String watchersCount;

    @Bindable
    public Status status;

    public final Work work;

    private final PageNavigator pageNavigator;

    public MyWorkItemViewModel(@NonNull Work work, Status status, PageNavigator pageNavigator) {
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
    }

    @Override
    public void destroy() {
        // Do nothing
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
        });
    }

}
