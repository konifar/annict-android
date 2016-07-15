package com.konifar.annict.viewmodel;

import android.support.annotation.NonNull;

import com.konifar.annict.model.Program;
import com.konifar.annict.util.DateUtil;

public class MyProgramItemViewModel implements ViewModel {

    public String thumbUrl;

    public String workTitle;

    public String episodeTitle;

    public String displayDate;

    public String channel;

    public MyProgramItemViewModel(@NonNull Program program) {
        if (program.work != null) workTitle = program.work.title;
        if (program.episode != null) episodeTitle = program.episode.title;
        if (program.channel != null) channel = program.channel.name;
        displayDate = DateUtil.getLongFormatDate(program.startedAt);
    }

    @Override
    public void destroy() {
        // Do nothing
    }

}
