package com.konifar.annict.viewmodel;

import com.konifar.annict.model.Program;
import com.konifar.annict.util.DateUtil;

public class MyProgramItemViewModel implements ViewModel {

    public String thumbUrl;

    public String workTitle;

    public String episodeTitle;

    public String displayDate;

    public String channel;

    public MyProgramItemViewModel(Program program) {
        this.workTitle = program.work.title;
        this.episodeTitle = program.episode.title;
        this.displayDate = DateUtil.getLongFormatDate(program.startedAt);
        this.channel = program.channel.name;
    }

    @Override
    public void destroy() {
        // Do nothing
    }

}
