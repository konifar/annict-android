package com.konifar.annict.viewmodels;

import com.konifar.annict.model.Program;
import com.konifar.annict.util.DateUtil;

public class ProgramViewModel {

    public String thumbUrl;

    public String workTitle;

    public String episodeTitle;

    public String displayDate;

    public String channel;

    public ProgramViewModel(Program program) {
        this.workTitle = program.work.title;
        this.episodeTitle = program.episode.title;
        this.displayDate = DateUtil.getLongFormatDate(program.startedAt);
        this.channel = program.channel.name;
    }

}
