package com.konifar.annict.repository;

import android.content.Context;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.api.builder.MeWorksBuilder;
import com.konifar.annict.api.builder.WorksBuilder;
import com.konifar.annict.model.Sort;
import com.konifar.annict.model.Status;
import com.konifar.annict.model.Work;
import com.konifar.annict.pref.DefaultPrefs;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class WorkRepositoryImpl implements WorkRepository {

    private final AnnictClient client;

    private final Context context;

    @Inject
    public WorkRepositoryImpl(AnnictClient client, Context context) {
        this.client = client;
        this.context = context;
    }

    @Override
    public Observable<List<Work>> getMineWhereStatusWithAuth(String authCode, Status status, int page) {
        return client.postOauthToken(authCode)
                .flatMap(token -> {
                    DefaultPrefs.get(context).putAccessToken(token.accessToken);
                    return getMineWhereStatus(status, page);
                });
    }

    @Override
    public Observable<List<Work>> getMineWhereStatus(Status status, int page) {
        return new MeWorksBuilder(client, page)
                .filterStatus(status)
                .build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(works -> works.list);
    }

    @Override
    public Observable<List<Work>> getWhereSeason(String season, int page) {
        return new WorksBuilder(client, page)
                .filterSeason(season)
                .build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(works -> works.list);
    }

    @Override
    public Observable<List<Work>> getOrderWatchersCountDesc(int page) {
        return new WorksBuilder(client, page)
                .sortWatchersCount(Sort.DESC)
                .build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(works -> works.list);
    }
}
