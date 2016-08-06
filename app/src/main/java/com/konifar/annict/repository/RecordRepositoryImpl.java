package com.konifar.annict.repository;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.api.builder.MeRecordsBuilder;
import com.konifar.annict.model.Record;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class RecordRepositoryImpl implements RecordRepository {

    private final AnnictClient client;

    @Inject
    public RecordRepositoryImpl(AnnictClient client) {
        this.client = client;
    }

    @Override
    public Observable<Record> edit(Long episodeId, String comment, Float rating,
        boolean shareTwitter, boolean shareFacebook) {
        return new MeRecordsBuilder(client, episodeId)
            .comment(comment)
            .rating(rating)
            .shareTwitter(shareTwitter)
            .shareFacebook(shareFacebook)
            .build();
    }
}
