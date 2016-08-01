package com.konifar.annict.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.konifar.annict.R;
import com.konifar.annict.model.Episode;
import com.konifar.annict.model.Program;
import com.konifar.annict.util.DateUtil;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;
import com.squareup.phrase.Phrase;

public class MyProgramItemViewModel implements ViewModel {

    public String thumbUrl;

    public String workTitle;

    public String episodeTitle;

    public String displayDate;

    public String channel;

    public final Program program;

    private final PageNavigator pageNavigator;

    public MyProgramItemViewModel(Context context, @NonNull Program program, PageNavigator pageNavigator) {
        if (program.work != null) {
            workTitle = program.work.title;
            if (program.work.twitterUserName != null) {
                thumbUrl = ViewHelper.getTwitterProfileImageUrl(program.work.twitterUserName);
            }
        }
        if (program.episode != null) {
            Episode episode = program.episode;
            if (TextUtils.isEmpty(episode.title)) {
                episodeTitle = episode.numberText;
            } else {
                episodeTitle = Phrase.from(context, R.string.episode_brackets)
                        .putOptional("episode_number", program.episode.numberText)
                        .put("episode_title", program.episode.title)
                        .format().toString();
            }
        }
        if (program.channel != null) channel = program.channel.name;
        displayDate = DateUtil.getProgramStartDate(program.startedAt);

        this.program = program;
        this.pageNavigator = pageNavigator;
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    public void onClickRoot(@SuppressWarnings("unused") View view) {
        pageNavigator.startEpisodeDetailActivity(program);
    }

    public void onClickImage(@SuppressWarnings("unused") View view) {
        pageNavigator.startWorkDetailActivity(program.work);
    }

    public void onClickRecordButton(@SuppressWarnings("unused") View view) {
        pageNavigator.showRecordCreateDialog(program);
    }

}
