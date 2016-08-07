package com.konifar.annict.repository;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Record;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class RecordRepositoryImpl implements RecordRepository {

    private final AnnictClient client;

    @Inject
    public RecordRepositoryImpl(AnnictClient client) {
        this.client = client;
    }

    @Override
    public Observable<Record> create(Long episodeId, String comment, Float rating,
        boolean shareTwitter, boolean shareFacebook) {
        return client.service.postMeRecords(
            episodeId,
            comment,
            rating,
            shareTwitter,
            shareFacebook
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Record> update(Long recordId, String comment, Float rating,
        boolean shareTwitter, boolean shareFacebook) {
        return client.service.postMeRecords(
            recordId,
            comment,
            rating,
            shareTwitter,
            shareFacebook
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
