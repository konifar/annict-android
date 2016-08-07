package com.konifar.annict.viewmodel;

import com.konifar.annict.model.Program;
import com.konifar.annict.util.DateUtil;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

public class MyProgramItemViewModel implements ViewModel {

    public final Program program;

    private final PageNavigator navigator;

    public String thumbUrl;

    public String workTitle;

    public String episodeTitle;

    public String displayDate;

    public String channel;

    public MyProgramItemViewModel(Context context, @NonNull Program program, PageNavigator navigator) {
        if (program.work != null) {
            workTitle = program.work.title;
            if (program.work.twitterUserName != null) {
                thumbUrl = ViewHelper.getTwitterProfileImageUrl(program.work.twitterUserName);
            }
        }
        episodeTitle = ViewHelper.getEpisodeTitle(program.episode, context);

        if (program.channel != null) {
            channel = program.channel.name;
        }

        displayDate = DateUtil.getProgramStartDate(program.startedAt);

        this.program = program;
        this.navigator = navigator;
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    public void onClickRoot(@SuppressWarnings("unused") View view) {
        navigator.startEpisodeDetailActivity(program);
    }

    public void onClickImage(@SuppressWarnings("unused") View view) {
        navigator.startWorkDetailActivity(program.work);
    }

    public void onClickRecordButton(@SuppressWarnings("unused") View view) {
        navigator.showRecordCreateDialog(program);
    }
}
