package com.konifar.annict.repository;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.api.builder.MeProgramsBuilder;
import com.konifar.annict.model.Program;
import com.konifar.annict.model.Sort;
import com.konifar.annict.pref.DefaultPrefs;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class ProgramRepositoryImpl implements ProgramRepository {

    private final AnnictClient client;

    private final Context context;

    @Inject
    public ProgramRepositoryImpl(AnnictClient client, Context context) {
        this.client = client;
        this.context = context;
    }

    @Override
    public Observable<List<Program>> getMineOrderByStartedAtDescWithAuth(String authCode, int page) {
        return withAuth(authCode, getMineOrderByStartedAtDesc(page));
    }

    private Observable<List<Program>> withAuth(String authCode,
        Observable<List<Program>> observable) {
        return client.postOauthToken(authCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(token -> {
                DefaultPrefs.get(context).putAccessToken(token.accessToken);
                return observable;
            });
    }

    @Override
    public Observable<List<Program>> getMineOrderByStartedAtDesc(int page) {
        return new MeProgramsBuilder(client, page).page(page)
            .sortStartedAt(Sort.DESC)
            .build()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(programs -> programs.list);
    }
}
