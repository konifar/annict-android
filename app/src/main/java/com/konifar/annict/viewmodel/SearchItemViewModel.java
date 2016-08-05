package com.konifar.annict.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import com.konifar.annict.R;
import com.konifar.annict.model.Work;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

public class SearchItemViewModel extends BaseObservable implements ViewModel {

    public String thumbUrl;

    public String title;

    public String seasonNameText;

    public String mediaText;

    public String watchersCount;

    public String episodesCount;

    public final Work work;

    private final PageNavigator navigator;

    public SearchItemViewModel(Context context, @NonNull Work work, PageNavigator navigator) {
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

        this.work = work;
        this.navigator = navigator;
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    public void onClickRoot(@SuppressWarnings("unused") View view) {
        navigator.startWorkDetailActivity(work);
    }

    public void onClickImage(@SuppressWarnings("unused") View view) {
        navigator.startWorkDetailActivity(work);
    }
}
