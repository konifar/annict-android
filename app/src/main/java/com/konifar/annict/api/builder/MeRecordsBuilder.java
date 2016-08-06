package com.konifar.annict.api.builder;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Record;

import rx.Observable;

public class MeRecordsBuilder implements Builder<Record> {

    private final AnnictClient client;

    private final Long episodeId;

    private String comment;

    private Float rating;

    private boolean shareTwitter;

    private boolean shareFacebook;

    public MeRecordsBuilder(AnnictClient client, Long episodeId) {
        this.client = client;
        this.episodeId = episodeId;
    }

    public MeRecordsBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public MeRecordsBuilder rating(Float rating) {
        this.rating = rating;
        return this;
    }

    public MeRecordsBuilder shareTwitter(boolean shareTwitter) {
        this.shareTwitter = shareTwitter;
        return this;
    }

    public MeRecordsBuilder shareFacebook(boolean shareFacebook) {
        this.shareFacebook = shareFacebook;
        return this;
    }

    @Override
    public Observable<Record> build() {
        return client.service.postMeRecords(
            episodeId,
            comment,
            rating,
            shareTwitter,
            shareFacebook
        );
    }
}
