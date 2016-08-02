package com.konifar.annict.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

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

    public String statusText;

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

        statusText = status.name();

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

}
