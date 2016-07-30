package com.konifar.annict.viewmodel;

import android.support.annotation.NonNull;
import android.view.View;

import com.konifar.annict.model.Program;
import com.konifar.annict.util.DateUtil;
import com.konifar.annict.util.PageNavigator;

public class MyProgramItemViewModel implements ViewModel {

    public String thumbUrl;

    public String workTitle;

    public String episodeTitle;

    public String displayDate;

    public String channel;

    public final Program program;

    private final PageNavigator pageNavigator;

    public MyProgramItemViewModel(@NonNull Program program, PageNavigator pageNavigator) {
        if (program.work != null) {
            workTitle = program.work.title;
            if (program.work.twitterUserName != null) {
                // TODO this is temporary for now.
                // http://furyu.hatenablog.com/entry/20130730/1375178609
                thumbUrl = "http://furyu.nazo.cc/twicon/" + program.work.twitterUserName + "/bigger";
            }
        }
        if (program.episode != null) episodeTitle = program.episode.title;
        if (program.channel != null) channel = program.channel.name;
        displayDate = DateUtil.getLongFormatDate(program.startedAt);

        this.program = program;
        this.pageNavigator = pageNavigator;
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    public void onClickRoot(@SuppressWarnings("unused") View view) {
        pageNavigator.startProgramDetailActivity(program);
    }

}
