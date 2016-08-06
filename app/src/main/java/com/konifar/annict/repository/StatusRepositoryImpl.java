package com.konifar.annict.repository;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Status;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class StatusRepositoryImpl implements StatusRepository {

    private final AnnictClient client;

    @Inject
    public StatusRepositoryImpl(AnnictClient client) {
        this.client = client;
    }

    @Override
    public Observable<Void> edit(long workId, Status status) {
        return client.service.postMeStatuses(workId, status.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
